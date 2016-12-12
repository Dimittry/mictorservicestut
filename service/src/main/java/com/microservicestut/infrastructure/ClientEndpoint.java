package com.microservicestut.infrastructure;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.jar.Pack200;
import java.util.logging.Logger;

@javax.websocket.ClientEndpoint
public class ClientEndpoint<T> {
    private static final Logger LOGGER = Logger.getLogger(ClientEndpoint.class.getName());

    private  final List<MessageListener<T>> listeners = new ArrayList<>();
    private final URI serverEndpoint;
    private final MessageHandler<T> messageHandler;
    private Session session;

    public ClientEndpoint(String serverEndpoint, MessageHandler<T> messageHandler) {
        this.serverEndpoint = URI.create(serverEndpoint);
        this.messageHandler = messageHandler;
    }

    @OnMessage
    public void onWebSocketText(String fullTweet) throws IOException {
        T message = messageHandler.processMessage(fullTweet);
        listeners.stream()
                .forEach(messageListener -> messageListener.onMessage(message));
    }

    @OnError
    public void onError(Throwable error) {
        LOGGER.warning("Error received: " + error.getMessage());
        LOGGER.warning(error.toString());
        close();
        naiveReconnectRetry();
    }

    @OnClose
    public void onClose() {
        LOGGER.warning(String.format("Session to % closed, retrying...", serverEndpoint));
        naiveReconnectRetry();
    }

    public void addListener(MessageListener<T> listener) {
        listeners.add(listener);
    }

    public void connect() {
        WebSocketContainer container
                = ContainerProvider.getWebSocketContainer();
        try {
            session = container.connectToServer(this, serverEndpoint);
            LOGGER.info("Connected to: " + serverEndpoint);
        } catch (DeploymentException | IOException e) {
            LOGGER.warning(String.format("Error connecting to %s: %s",
                    serverEndpoint, e.getMessage()));
        }
    }

    public void close() {
        if(session != null) {
            try {
                session.close();
            } catch (IOException e) {
                LOGGER.warning(String.format("Error closing session %s", e.getMessage()));
            }
        }
    }

    public static ClientEndpoint<String> createPassthroughEndpoint(String serverEndpoint) {
        return new ClientEndpoint<>(serverEndpoint, originalText -> originalText);
    }

    private void naiveReconnectRetry() {
        try {
            TimeUnit.SECONDS.sleep(5);
            connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
