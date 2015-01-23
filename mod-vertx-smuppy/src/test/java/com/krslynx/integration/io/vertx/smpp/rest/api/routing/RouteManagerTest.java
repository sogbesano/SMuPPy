package com.krslynx.integration.io.vertx.smpp.rest.api.routing;

import com.krslynx.io.vertx.smpp.rest.api.routing.RouteManager;
import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.http.HttpServer;
import org.vertx.testtools.TestVerticle;

import static org.vertx.testtools.VertxAssert.assertEquals;
import static org.vertx.testtools.VertxAssert.testComplete;

public class RouteManagerTest extends TestVerticle {

    private final int TEST_PORT = 8082;

    @Test
    public void testHttpPostSmppClientConfiguration() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/client/configuration", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppClientConfiguration() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/client/configuration", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppClientConfigurationConfSuffix() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/client/configuration/1", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpPatchSmppClientConfigurationConfSuffix() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).patch("/smpp/client/configuration/1", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpDeleteSmppClientConfigurationConfSuffix() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).delete("/smpp/client/configuration/1", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppClientConfigurationConfSuffixClone() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/client/configuration/1/clone", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppClientConfigurationConfSuffixBind() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/client/configuration/1/bind", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpPostSmppClientConfigurationConfSuffixBindBindSuffixSubmit() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).post("/smpp/client/configuration/1/bind/1/submit", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppClientConfigurationConfSuffixBindBindSuffixDestroy() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/client/configuration/1/bind/1/destroy", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppClientConfigurationConfSuffixLogBindBindSuffixLog() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/client/configuration/1/bind/1/log", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpDeleteSmppClientConfigurationConfSuffixBindBindSuffix() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).delete("/smpp/client/configuration/1/bind/1", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpPostSmppServerConfiguration() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/server/configuration", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppServerConfiguration() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/server/configuration", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppServerConfigurationConfSuffix() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/server/configuration/1", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpPatchSmppServerConfigurationConfSuffix() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).patch("/smpp/server/configuration/1", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpDeleteSmppServerConfigurationConfSuffix() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).delete("/smpp/server/configuration/1", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppServerConfigurationConfSuffixClone() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/server/configuration/1/clone", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppServerConfigurationConfSuffixStart() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/server/configuration/1/start", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppServerConfigurationConfSuffixStop() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/server/configuration/1/stop", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppServerConfigurationConfSuffixStatus() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/server/configuration/1/status", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }

    @Test
    public void testHttpGetSmppServerConfigurationConfSuffixLog() {
        vertx.createHttpServer().requestHandler(new RouteManager(this.vertx)).listen(TEST_PORT, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> event) {
                vertx.createHttpClient().setPort(TEST_PORT).get("/smpp/server/configuration/1/log", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse event) {
                        assertEquals(200, event.statusCode());
                        testComplete();
                    }
                }).end();
            }
        });
    }
}