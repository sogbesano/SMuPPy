package com.krslynx.io.vertx.smpp.rest.api.routing.client.configuration;

import com.krslynx.io.vertx.smpp.rest.api.common.model.client.ClientConfiguration;
import com.krslynx.io.vertx.smpp.rest.api.routing.base.rest.RestfulHandler;
import org.vertx.java.core.Vertx;

/**
 * <p>Handler for Client Configurations</p>
 *
 * @author Christopher Burke
 */
public class ClientConfigurationHandler extends RestfulHandler<ClientConfiguration> {

    /**
     * @see com.krslynx.io.vertx.smpp.rest.api.routing.base.rest.RestfulHandler
     */
    public ClientConfigurationHandler(Vertx vertx) {
        super(vertx, ClientConfiguration.class, "client_configuration", "cid");
    }
}
