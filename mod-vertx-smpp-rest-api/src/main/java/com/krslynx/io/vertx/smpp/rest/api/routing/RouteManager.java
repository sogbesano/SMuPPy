package com.krslynx.io.vertx.smpp.rest.api.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;

public class RouteManager implements Handler<HttpServerRequest> {

    /**
     * Logger
     */
    private static Logger log = LoggerFactory.getLogger(RouteManager.class);

    /**
     * The base path for all requests to this... I want to say.. servlet. ;-P
     * (ps. I know it's not a servlet)
     */
    private final String BASE_PATH = "/smpp";

    /**
     * The route matcher to handle requests with
     */
    private RouteMatcher routeMatcher;

    /**
     * Application level routing for SMPP REST API
     *
     * @param routeMatcher the RouteMatcher to setup routes on
     */
    public RouteManager(RouteMatcher routeMatcher) {
        this.routeMatcher = routeMatcher;
        setupRouteMatcher(this.routeMatcher);
    }

    /**
     * All Application Level Routing for SMPP REST API
     */
    public RouteManager() {
        this(new RouteMatcher());
    }

    /**
     * Something has happened, so handle it.
     *
     * @param event the event to handle
     */
    @Override
    public void handle(HttpServerRequest event) {
        this.routeMatcher.handle(event);
    }

    /**
     * Entry point for grouping all route matching
     *
     * @param routeMatcher the RouteMatcher to setup routes on
     */
    private void setupRouteMatcher(RouteMatcher routeMatcher) {
        setupClientHttpManagement(routeMatcher);
        setupClientActions(routeMatcher);
        setupServerHttpManagement(routeMatcher);
        setupServerActions(routeMatcher);
    }

    /**
     * Setup all Routes for /client/configuration requests.
     *
     * @param routeMatcher the RouteMatcher to setup routes on
     */
    private void setupClientHttpManagement(RouteMatcher routeMatcher) {

        String clientPath = getClientPath();
        String configPath = getConfigPath();
        String confSuffix = getConfSuffix();

        String pathSpec = BASE_PATH + clientPath + configPath;

        // see: http://krslynx.com/smpp-rest-api/docs/
        log.info("Beginning registry of Client / SMPP Management Actions...");

        log.info("Registering POST - " + pathSpec);
        routeMatcher.post(pathSpec, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered POST - " + pathSpec);

        log.info("Registering GET - " + pathSpec);
        routeMatcher.get(pathSpec, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec);

        log.info("Registering GET - " + pathSpec + confSuffix);
        routeMatcher.get(pathSpec + confSuffix, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix);

        log.info("Registering PATCH - " + pathSpec + confSuffix);
        routeMatcher.patch(pathSpec + confSuffix, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered PATCH - " + pathSpec + confSuffix);

        log.info("Registering DELETE - " + pathSpec + confSuffix);
        routeMatcher.delete(pathSpec + confSuffix, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered DELETE - " + pathSpec + confSuffix);

        log.info("Registering GET - " + pathSpec + confSuffix + "/clone");
        routeMatcher.get(pathSpec + confSuffix + "/clone", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix + "/clone");

        // done
        log.info("Registry of Client / SMPP Management Actions Completed!");
    }



    /**
     * Setup all routes for /client/configuration/cid/* requests
     *
     * @param routeMatcher the RouteMatcher to setup routes on
     */
    private void setupClientActions(RouteMatcher routeMatcher) {

        String clientPath = getClientPath();
        String configPath = getConfigPath();
        String confSuffix = getConfSuffix();
        String bindSuffix = getBindSuffix();

        String pathSpec = BASE_PATH + clientPath + configPath;

        // see: http://krslynx.com/smpp-rest-api/docs/
        log.info("Beginning registry of Client / Actions...");

        log.info("Registering GET - " + pathSpec + confSuffix + "/bind");
        routeMatcher.get(pathSpec + confSuffix + "/bind", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix + "/bind");

        log.info("Registering POST - " + pathSpec + confSuffix + "/bind" + bindSuffix + "/submit");
        routeMatcher.post(pathSpec + confSuffix + "/bind" + bindSuffix + "/submit", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered POST - " + pathSpec + confSuffix + "/bind" + bindSuffix + "/submit");

        log.info("Registering GET - " + pathSpec + confSuffix + "/bind" + bindSuffix + "/destroy");
        routeMatcher.get(pathSpec + confSuffix + "/bind" + bindSuffix + "/destroy", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix + "/bind" + bindSuffix + "/destroy");

        log.info("Registering GET - " + pathSpec + confSuffix + "/bind" + bindSuffix + "/log");
        routeMatcher.get(pathSpec + confSuffix + "/bind" + bindSuffix + "/log", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix + "/bind" + bindSuffix + "/log");

        log.info("Registering DELETE - " + pathSpec + confSuffix + "/bind" + bindSuffix);
        routeMatcher.delete(pathSpec + confSuffix + "/bind" + bindSuffix, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered DELETE - " + pathSpec + confSuffix + "/bind" + bindSuffix);

        // done
        log.info("Registry of Client / Actions Completed!");
    }

    /**
     * Setup routes for /server/configuration requests
     *
     * @param routeMatcher the RouteMatcher to setup routes on
     */
    private void setupServerHttpManagement(RouteMatcher routeMatcher) {

        String serverPath = getServerPath();
        String configPath = getConfigPath();
        String confSuffix = getConfSuffix();

        String pathSpec = BASE_PATH + serverPath + configPath;

        // see: http://krslynx.com/smpp-rest-api/docs/
        log.info("Beginning registry of Server / SMPP Management Actions...");

        log.info("Registering POST - " + pathSpec);
        routeMatcher.post(pathSpec, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered POST - " + pathSpec);

        log.info("Registering GET - " + pathSpec);
        routeMatcher.get(pathSpec, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec);

        log.info("Registering GET - " + pathSpec + confSuffix);
        routeMatcher.get(pathSpec + confSuffix, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix);

        log.info("Registering PATCH - " + pathSpec + confSuffix);
        routeMatcher.patch(pathSpec + confSuffix, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered PATCH - " + pathSpec + confSuffix);

        log.info("Registering DELETE - " + pathSpec + confSuffix);
        routeMatcher.delete(pathSpec + confSuffix, new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered DELETE - " + pathSpec + confSuffix);

        log.info("Registering GET - " + pathSpec + confSuffix + "/clone");
        routeMatcher.get(pathSpec + confSuffix + "/clone", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix + "/clone");

        // done
        log.info("Registry of Server / SMPP Management Actions Completed!");
    }

    /**
     * Setup requests for /server/configuration/* requests
     *
     * @param routeMatcher the RouteMatcher to setup routes on
     */
    private void setupServerActions(RouteMatcher routeMatcher) {

        String serverPath = getServerPath();
        String configPath = getConfigPath();
        String confSuffix = getConfSuffix();

        String pathSpec = BASE_PATH + serverPath + configPath;

        // see: http://krslynx.com/smpp-rest-api/docs/
        log.info("Beginning registry of Server / Actions...");

        log.info("Registering GET - " + pathSpec + confSuffix + "/start");
        routeMatcher.get(pathSpec + confSuffix + "/start", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix + "/start");

        log.info("Registering GET - " + pathSpec + confSuffix + "/stop");
        routeMatcher.get(pathSpec + confSuffix + "/stop", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix + "/stop");

        log.info("Registering GET - " + pathSpec + confSuffix + "/status");
        routeMatcher.get(pathSpec + confSuffix + "/status", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix + "/status");

        log.info("Registering GET - " + pathSpec + confSuffix + "/log");
        routeMatcher.get(pathSpec + confSuffix + "/log", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(event.method() + " " + event.path() + " not yet implemented.");
            }
        });
        log.info("Registered GET - " + pathSpec + confSuffix + "/log");

        // done
        log.info("Registry of Server / Actions Completed!");
    }

    /**
     * @return the client id suffix
     */
    private String getConfSuffix() {
        final String clidSuffix = System.getProperty("com.krslynx.smpp.client.config.id.suffix");
        if(clidSuffix != null && !clidSuffix.isEmpty()) {
            return clidSuffix;
        } else {
            return "/:cid";
        }
    }

    /**
     * @return the bind id suffix
     */
    private String getBindSuffix() {
        final String bindSuffix = System.getProperty("com.krslynx.smpp.client.config.bind.id.suffix");
        if(bindSuffix != null && !bindSuffix.isEmpty()) {
            return bindSuffix;
        } else {
            return "/:bid";
        }
    }

    /**
     * @return the path for configurations
     */
    private String getConfigPath() {
        final String configPath = System.getProperty("com.krslynx.smpp.client.config.path");
        if(configPath != null && !configPath.isEmpty()) {
            return configPath;
        } else {
            return "/configuration";
        }
    }

    /**
     * @return the path for client requests
     */
    private String getClientPath() {
        final String clientPath = System.getProperty("com.krslynx.smpp.client.path");
        if(clientPath != null && !clientPath.isEmpty()) {
            return clientPath;
        } else {
            return "/client";
        }
    }

    /**
     * @return the path for client requests
     */
    private String getServerPath() {
        final String serverPath = System.getProperty("com.krslynx.smpp.server.path");
        if(serverPath != null && !serverPath.isEmpty()) {
            return serverPath;
        } else {
            return "/server";
        }
    }
}
