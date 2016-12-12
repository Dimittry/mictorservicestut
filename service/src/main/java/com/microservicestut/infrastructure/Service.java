package com.microservicestut.infrastructure;

import javax.websocket.DeploymentException;
import java.io.IOException;

public class Service<T> implements Runnable {
    private final String endpointToConnect;
    private final String serviceEndpointPath;
    private final int servicePort;

    private WebSocketServer webSocketServer;
    private ClientEndpoint<T> clientEndpoint;
    private MessageHandler<T> messageHandler;

    public Service(String endpointToConnect, String serviceEndpointPath,
                   int servicePort, MessageHandler<T> messageHandler) {
        this.endpointToConnect = endpointToConnect;
        this.messageHandler = messageHandler;
        this.serviceEndpointPath = serviceEndpointPath;
        this.servicePort = servicePort;
    }

    public static void main(String[] args) throws IOException, DeploymentException {
        new Service<>("ws://localhost:8081/tweets/", "/testing", 8090,
                originalText -> originalText).run();
    }

    @Override
    public void run() {
        try {
            BroadcastingServerEndpoint<T> broadcastingServerEndpoint
                    = new BroadcastingServerEndpoint<>();
            clientEndpoint = new ClientEndpoint<>(endpointToConnect, messageHandler);
            clientEndpoint.addListener(broadcastingServerEndpoint);
            clientEndpoint.connect();

            webSocketServer = new WebSocketServer(serviceEndpointPath, servicePort, broadcastingServerEndpoint);
            webSocketServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        clientEndpoint.close();
        webSocketServer.stop();
    }

}
