package com.krslynx.io.vertx.smpp.rest.api;

import com.krslynx.io.vertx.smpp.rest.api.routing.RouteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

/**
 * <p>Main entry point to the application.</p>
 *
 * @author Christopher Burke
 */
public class MainVerticle extends Verticle {

    /**
     * Logger
     */
    private static Logger log = LoggerFactory.getLogger(MainVerticle.class);

    /**
     * <p>Code executed when application is started</p>
     */
    @Override
    public void start() {

        final JsonObject appConfig = container.config();
        log.info("Deploying MongoDB Persistor with config: " + appConfig.encodePrettily());
        container.deployModule("io.vertx~mod-mongo-persistor~2.1.1", appConfig.getObject("mongo-persistor"));

        vertx.createHttpServer().requestHandler(new RouteManager(vertx)).listen(8080);
    }
}
