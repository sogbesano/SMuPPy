package com.krslynx.io.vertx.smpp.rest.api.routing.server.configuration;

import com.krslynx.io.vertx.smpp.rest.api.common.model.server.ServerConfiguration;
import com.krslynx.io.vertx.smpp.rest.api.routing.base.rest.RestfulHandler;
import org.vertx.java.core.Vertx;

/**
 * <p>Handler for Server Configurations</p>
 *
 * @author Christopher Burke
 */
public class ServerConfigurationHandler extends RestfulHandler<ServerConfiguration> {

    /**
     * @see com.krslynx.io.vertx.smpp.rest.api.routing.base.rest.RestfulHandler
     */
    public ServerConfigurationHandler(Vertx vertx) {
        super(vertx, ServerConfiguration.class, "server_configuration", "cid");
    }
}
