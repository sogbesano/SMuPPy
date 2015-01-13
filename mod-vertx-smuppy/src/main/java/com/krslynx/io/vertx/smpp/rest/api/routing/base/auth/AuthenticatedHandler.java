package com.krslynx.io.vertx.smpp.rest.api.routing.base.auth;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Handler for completing Access/Session Key based authentication on <code>HttpServerRequest</code>s.
 * </p>
 *
 * @author Christopher Burke
 * @see org.vertx.java.core.Handler
 */
public class AuthenticatedHandler implements Handler<HttpServerRequest> {

    /**
     * Reference to Vertx for retrieving the EventBus
     */
    public final Vertx vertx;

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(AuthenticatedHandler.class);

    /**
     * The length of the session time-out, if zero, sessions do not time out
     */
    private static int VARIABLE_SESSION_TIMEOUT_SECONDS = 3660;

    /**
     * Collection which tokens are stored
     */
    private static String VARIABLE_AUTHENTICATION_TOKENS_COLLECTION = "tokens";

    /**
     * Key for Access Tokens
     */
    private static String VARIABLE_ACCESS_KEY_MATCHER = "access_key";

    /**
     * Access Key header name
     */
    private static final String VARIABLE_ACCESS_KEY = "AccessKey";

    /**
     * Session Key header name
     */
    private static final String VARIABLE_SESSION_KEY = "SessionKey";

    /**
     * Session Key Expiry header name
     */
    private static final String VARIABLE_SESSION_KEY_DATE_EXPIRES = "SessionKeyExpiry";

    /**
     * SecureRandom object for generating session keys
     */
    private static SecureRandom random = new SecureRandom();

    /**
     * In-memory response cache, as login calls are made in every subsequent request after a login attempt
     */
    private static final Map<String, Date> AUTH_CACHE = new HashMap<>();

    /**
     * handler to call on success of authentication, failure cases are handled internally here and subsequent
     * vertices are not called
     */
    private final Handler<HttpServerRequest> successHandler;

    /**
     * <p>
     * The AuthenticationHandler is used to authenticate requests before passing them to the <code>successHandler</code>
     * </p>
     *
     * @param vertx the verticle to check the eventbus for MongoDB
     * @param successHandler the handler to call on authentication success
     */
    public AuthenticatedHandler(Vertx vertx, Handler<HttpServerRequest> successHandler) {
        this.vertx = vertx;
        this.successHandler = successHandler;
    }

    /**
     * <p>
     * Authenticates a <code>HttpServerRequest</code> by checking an in-memory cache, or MongoDB for a provided
     * AccessKey or SessionKey. AccessKey's are intended to be long-standing tokens which should be secure,
     * while SessionKey's are intended to be short-lived (i.e. expire) tokens which can be provided for demos
     * or for clients to temporally connect.
     *
     * If authentication is successful, the provided <code>successHandler</code> will be called to process the request.
     * In the case of GET requests with an AccessKey, the SessionKey, and if configured, SessionKey expiry will be
     * provided in the response headers.
     *
     * Due to the nature of Vertx operating on an eventloop, all other request methods will require requests to
     * have a SessionKey. This is due to the eventloop being blocked by a requests bodyreader, which also
     * is asynchronous and therefore causes a deadlock.
     *
     * Failed authentication attempts will result in HTTP/1.1 401 (Unauthorized) where additional diagnostic
     * information may be found in the response body.
     * </p>
     *
     * @param request the request to authenticate
     */
    @Override
    public void handle(final HttpServerRequest request) {

        // if session key is present in headers, we're authenticating with in-memory hash map
        final String sessionKeyAuth = request.headers().get(VARIABLE_SESSION_KEY);

        // if access key is present in headers, we're authenticating with mongodb
        final String accessKeyAuth = request.headers().get(VARIABLE_ACCESS_KEY);

        if(sessionKeyAuth != null) {

            log.debug("Authenticating using Session Key.");

            // check authentication cache
            if(AUTH_CACHE.containsKey(sessionKeyAuth)) {

                // if session timeout == 0, we're not checking dates
                if(VARIABLE_SESSION_TIMEOUT_SECONDS == 0) {
                    log.debug("Authentication was successful.");
                    this.successHandler.handle(request);
                }

                // retrieve when session key expires
                final Date sessionKeyExpiry = AUTH_CACHE.get(sessionKeyAuth);
                if(sessionKeyExpiry.after(Calendar.getInstance().getTime())) {
                    // session key expires after current date, so authenticated
                    log.debug("Authentication was successful.");
                    this.successHandler.handle(request);
                }
            } else {
                log.debug("Authentication failed. Re-authentication is required.");
                request.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code())
                        .end(new JsonObject()
                                .putString(
                                        "error",
                                        "Please re-authenticate by providing an AccessKey in the request headers. " +
                                                "Upon successful authentication, please use SessionKey in subsequent requests.")
                                .encodePrettily());
            }
        } else if(accessKeyAuth != null) {
            // let mongo auth asynchronously
            // query
            JsonObject json = new JsonObject()
                    .putString("collection", VARIABLE_AUTHENTICATION_TOKENS_COLLECTION)
                    .putString("action", "find")
                    .putObject("matcher", new JsonObject().putString(VARIABLE_ACCESS_KEY_MATCHER, accessKeyAuth));

            // send query to bus
            vertx.eventBus().send("mongodb-persistor", json, new Handler<Message<JsonObject>>() {

                @Override
                public void handle(Message<JsonObject> event) {

                    if (event.body() != null) {
                        final JsonArray results = event.body().getArray("results");
                        if (results != null && results.size() > 0) {

                            // the query should return a result array of length=1
                            for (int i = 0; i < results.size(); i++) {
                                final JsonObject result = results.get(i);

                                // validate token
                                if (result.containsField(VARIABLE_ACCESS_KEY_MATCHER) &&
                                        result.getString(VARIABLE_ACCESS_KEY_MATCHER).equals(accessKeyAuth)) {
                                    // construct a random session key
                                    final String sessionKey = new BigInteger(130, random).toString(32);

                                    // set the session timeout
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(new Date());
                                    cal.add(Calendar.SECOND, VARIABLE_SESSION_TIMEOUT_SECONDS);

                                    // place the session key and time out into the cache
                                    AUTH_CACHE.put(sessionKey, cal.getTime());

                                    if(VARIABLE_SESSION_TIMEOUT_SECONDS > 0) {
                                        request.response().headers().add(VARIABLE_SESSION_KEY_DATE_EXPIRES, cal.getTime().toString());
                                    }

                                    request.response().headers().add(VARIABLE_SESSION_KEY, sessionKey);

                                    if(request.method().equals("GET")) {
                                        successHandler.handle(request);
                                    } else {
                                        request.response().end(new JsonObject()
                                                .putString(
                                                        "message",
                                                        "Please try this request again with a SessionKey. " +
                                                                "AccessKeys cannot be authenticated with the provided HTTP Method.")
                                                .encodePrettily());
                                    }
                                } else {
                                    request.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code())
                                            .end(new JsonObject()
                                                    .putString(
                                                            "error",
                                                            "AccessKey not found.")
                                                    .encodePrettily());
                                }
                            }
                        }
                    } else {
                        log.error("Failure to authenticate AccessKey in database.");
                        request.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code())
                                .end(new JsonObject()
                                        .putString(
                                                "error",
                                                "Failure to authenticate AccessKey in database.")
                                        .encodePrettily());
                    }
                }
            });
        } else {
            request.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code())
                    .end(new JsonObject()
                            .putString(
                                    "error",
                                    "Please authenticate by providing an SessionKey in the request headers.")
                            .encodePrettily());
        }
    }
}
