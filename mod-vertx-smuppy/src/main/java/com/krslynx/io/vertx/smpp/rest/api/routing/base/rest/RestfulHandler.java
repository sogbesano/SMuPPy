package com.krslynx.io.vertx.smpp.rest.api.routing.base.rest;

import com.krslynx.io.vertx.smpp.rest.api.common.model.ValidatedModel;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.DecodeException;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.json.impl.Json;

/**
 * <p>A handler for RESTFul requests, where <code>T</code> extends <code>ValidatedModel</code></p>
 *
 * @param <T> the model for JSON decoding
 * @author Christopher Burke
 */
public class RestfulHandler<T extends ValidatedModel> implements Handler<HttpServerRequest> {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(RestfulHandler.class);

    /**
     * The name of the suffix url parameter
     */
    private final String idSuffixName;

    /**
     * The name of the MongoDB collection to store the model in
     */
    private final String modelCollection;

    /**
     * The type of the model to store, for JSON decoding
     */
    private final Class<T> modelClazz;

    /**
     * Vertx for access to the EventBus
     */
    private final Vertx vertx;

    /**
     * <p>
     * Allows for basic RESTFul operations on a provided model <code>modelClazz</code> of type <code>T</code>.
     * </p>
     *
     * <ul>
     *     <li>POST: Attempts to validate and persist the model in the database <code>modelCollection</code></li>
     *     <li>GET: Attempts to list all entities in the database <code>modelCollection</code></li>
     *     <li>GET/cid: Attempts to show entities in the database <code>modelCollection</code>
     *     matching _id <code>idSuffixName</code></li>
     *     <li>PATCH: Attempts to validate and persist the model in the database <code>modelCollection</code>,
     *     patching existing entity matching _id <code>idSuffixName</code></li>
     *     <li>DELETE/cid: Attempts to delete all entities in the database <code>modelCollection</code>
     *     matching _id <code>idSuffixName</code></li>
     * </ul>
     *
     * @param vertx the vertx to run the verticle on to access eventbus
     * @param modelClazz the class of the model
     * @param modelCollection the name of the collection to store the model
     * @param idSuffixName the name of the suffix for path parameters
     */
    public RestfulHandler(Vertx vertx, Class<T> modelClazz, String modelCollection, String idSuffixName) {
        this.vertx = vertx;
        this.modelClazz = modelClazz;
        this.modelCollection = modelCollection;
        this.idSuffixName = idSuffixName;
    }

    /**
     * <p>
     * Inspects the requests HTTP Method, and subsequently routes the request.
     * </p>
     *
     * @param request the request to handle
     */
    @Override
    public void handle(HttpServerRequest request) {
        switch(request.method()) {
            case "POST":
                handlePost(request, modelClazz);
                break;
            case "GET":
                handleGet(request);
                break;
            case "PATCH":
                handlePatch(request, modelClazz, idSuffixName);
                break;
            case "DELETE":
                handleDelete(request);
                break;
        }
    }

    /**
     * <p>Handles HTTP POST</p>
     *
     * @param request the request to handle
     * @param clazz the class of the model
     */
    private void handlePost(final HttpServerRequest request, final Class<?> clazz) {

        request.bodyHandler(new Handler<Buffer>() {
            @Override
            public void handle(Buffer buf) {
                final String body = buf.toString();

                try {
                    // attempt to construct model from body using provided clazz
                    final T model = Json.decodeValue(body, clazz);

                    // ensure model is valid
                    if(!model.isValid()) {
                        // HTTP/1.1 400 BAD REQUEST
                        // Body: model as JSON
                        request.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code()).end(
                                new JsonObject(body)
                                        .putString("validationErrors", model.getErrors().toString())
                                        .putString("validationWarnings", model.getWarnings().toString())
                                        .encodePrettily());
                        return;
                    }

                    // Vertx Query
                    JsonObject json = new JsonObject()
                            .putString("collection", modelCollection)
                            .putString("action", "save")
                            .putObject("document", new JsonObject(Json.encode(model)));

                    // transmit query to mongodb-persistor
                    vertx.eventBus().send("mongodb-persistor", json, new Handler<Message<JsonObject>>() {
                        @Override
                        public void handle(Message<JsonObject> event) {
                            if(event.body().getString("status").equalsIgnoreCase("ok")) {
                                model._id = event.body().getString("_id");
                                // HTTP/1.1 202 ACCEPTED
                                // Body: model as JSON
                                request.response().setStatusCode(HttpResponseStatus.ACCEPTED.code())
                                        .end(Json.encodePrettily(model));
                            } else {
                                // HTTP/1.1 500 INTERNAL SERVER ERROR
                                // Body: MongoDB Error
                                request.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                                        .end(event.body().encodePrettily());
                            }
                        }
                    });

                } catch (DecodeException de) {
                    request.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code())
                            .end(new JsonObject()
                                    .putString("Failure", "An exception was raised when attempting to " +
                                            "process the provided request body.")
                                    .putString("body", body)
                                    .putString("exceptionMessage", de.getMessage()).encodePrettily());
                }
            }
        });
    }

    /**
     * <p>Handles HTTP GET</p>
     *
     * @param request the request to handle
     */
    private void handleGet(final HttpServerRequest request) {

        // Determine if configuration id suffix is contained in the request parameters (path parameter)
        final String cid = request.params().get(idSuffixName);
        if(cid != null) {
            handleGetId(request, cid);
        }

        // vertx query
        JsonObject json = new JsonObject()
                .putString("collection", modelCollection)
                .putString("action", "find");

        // transmit query to mongodb-persistor
        vertx.eventBus().send("mongodb-persistor", json, new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> event) {
                if(event.body().getString("status").equalsIgnoreCase("ok")) {
                    // HTTP/1.1 200 OK
                    // Body: N results, contained in array indices
                    request.response().setStatusCode(HttpResponseStatus.OK.code())
                            .end(event.body().getArray("results").encodePrettily());
                } else {
                    // HTTP/1.1 500 INTERNAL SERVER ERROR
                    // Body: MongoDB Error
                    request.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                            .end(event.body().encodePrettily());
                }
            }
        });
    }

    /**
     * <p>Handles HTTP GET with an id path parameter</p>
     * @param request the request to handle
     * @param idSuffix the suffix for path parameter names
     */
    private void handleGetId(final HttpServerRequest request, String idSuffix) {

        // vertx query
        JsonObject json = new JsonObject()
                .putString("collection", modelCollection)
                .putString("action", "findone")
                .putObject("matcher", new JsonObject().putString("_id", idSuffix));

        // transmit query to mongodb-persistor
        vertx.eventBus().send("mongodb-persistor", json, new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> event) {
                if(event.body().getString("status").equalsIgnoreCase("ok")) {
                    // HTTP/1.1 200 OK
                    // Body: Single result, contained in array indices
                    request.response().setStatusCode(HttpResponseStatus.OK.code())
                            .end(event.body().getArray("results").encodePrettily());
                } else {
                    // HTTP/1.1 500 INTERNAL SERVER ERROR
                    // Body: MongoDB Error
                    request.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                            .end(event.body().encodePrettily());
                }
            }
        });
    }

    /**
     * <p>Handles HTTP PATCH</p>
     *
     * @param request the request to handle
     * @param clazz the class of the model
     * @param idSuffix the suffix for path parameter names
     */
    private void handlePatch(final HttpServerRequest request, final Class<?> clazz, final String idSuffix) {

        // handle request body
        request.bodyHandler(new Handler<Buffer>() {

            @Override
            public void handle(Buffer buf) {

                final String body = buf.toString();

                try {

                    // attempt to construct model from body using provided clazz
                    final T model = Json.decodeValue(body, clazz);

                    // ensure model is valid
                    if(!model.isValid()) {
                        // HTTP/1.1 400 BAD REQUEST
                        // Body: model as JSON
                        request.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code()).end(Json.encodePrettily(model));
                        return;
                    }

                    // Vertx Query
                    JsonObject json = new JsonObject()
                            .putString("collection", modelCollection)
                            .putString("action", "update")
                            .putObject("criteria", new JsonObject().putString("_id", idSuffix)
                            .putObject("objNew", new JsonObject(Json.encode(model)))
                            .putBoolean("upsert", true)
                            .putBoolean("multi", false));

                    // transmit query to mongodb-persistor
                    vertx.eventBus().send("mongodb-persistor", json, new Handler<Message<JsonObject>>() {
                        @Override
                        public void handle(Message<JsonObject> event) {
                            if(event.body().getString("status").equalsIgnoreCase("ok")) {
                                model._id = event.body().getString("_id");
                                // HTTP/1.1 202 ACCEPTED
                                // Body: model as JSON
                                request.response().setStatusCode(HttpResponseStatus.ACCEPTED.code())
                                        .end(Json.encodePrettily(model));
                            } else {
                                // HTTP/1.1 500 INTERNAL SERVER ERROR
                                // Body: MongoDB Error
                                request.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                                        .end(event.body().encodePrettily());
                            }
                        }
                    });

                } catch (DecodeException de) {
                    request.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code())
                            .end(new JsonObject()
                                    .putString("Failure", "An exception was raised when attempting to " +
                                            "process the provided request body.")
                                    .putString("body", body)
                                    .putString("exceptionMessage", de.getMessage()).encodePrettily());
                }
            }
        });
    }

    /**
     * <p>Handles HTTP DELETE</p>
     * @param request the request to handle
     */
    private void handleDelete(final HttpServerRequest request) {

        // vertx query
        JsonObject json = new JsonObject()
                .putString("collection", modelCollection)
                .putString("action", "delete")
                .putObject("matcher", new JsonObject().putString("_id", request.params().get(idSuffixName)));

        // transmit query to mongodb-persistor
        vertx.eventBus().send("mongodb-persistor", json, new Handler<Message<JsonObject>>() {

            @Override
            public void handle(Message<JsonObject> event) {

                if(event.body().getString("status").equalsIgnoreCase("ok")) {
                    // HTTP/1.1 200 OK
                    request.response().setStatusCode(HttpResponseStatus.OK.code()).end();
                } else {
                    // HTTP/1.1 500 INTERNAL SERVER ERROR
                    // Body: MongoDB Error
                    request.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                            .end(event.body().encodePrettily());
                }
            }
        });
    }
}
