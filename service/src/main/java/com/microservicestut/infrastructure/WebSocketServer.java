package com.microservicestut.infrastructure;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerEndpointConfig;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

public class WebSocketServer implements Runnable {
    private final int port;
    private final String path;
    private final Endpoint endpoint;
    private final Server server;

    public WebSocketServer(String path, int port, Endpoint endpoint) {
        this.path = path;
        this.port = port;
        this.endpoint = endpoint;
        server = new Server();
    }

    public void run() {
        ServletContextHandler context = initialiseJettyServer(port);
        try {
            SingletonEndpointConfigurator serverEndpointConfigurator
                    = new SingletonEndpointConfigurator(endpoint);
            ServerEndpointConfig config = ServerEndpointConfig.Builder
                    .create(endpoint.getClass(), path)
                    .configurator(serverEndpointConfigurator)
                    .build();
            WebSocketServerContainerInitializer.configureContext(context).addEndpoint(config);

            server.start();
            server.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

    private ServletContextHandler initialiseJettyServer(int port) {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        return context;
    }
}
