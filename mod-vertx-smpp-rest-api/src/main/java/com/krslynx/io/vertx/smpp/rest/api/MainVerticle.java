package com.krslynx.io.vertx.smpp.rest.api;

import com.krslynx.integration.io.vertx.smpp.rest.api.routing.RouteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.platform.Verticle;

public class MainVerticle extends Verticle {

    /**
     * Logger
     */
    private static Logger log = LoggerFactory.getLogger(MainVerticle.class);

    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(new RouteManager()).listen(8080);
    }
}
