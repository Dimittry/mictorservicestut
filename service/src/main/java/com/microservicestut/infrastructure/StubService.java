package com.microservicestut.infrastructure;

import java.util.function.Supplier;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class StubService implements Runnable{
    private final BroadcastingServerEndpoint<String> serverEndpoint = new BroadcastingServerEndpoint<>();
    private final WebSocketServer server;
    private final Supplier<String> messageGenerator;

    public StubService(String path, int port, Supplier<String> messageGenerator) {
        this.server = new WebSocketServer(path, port, serverEndpoint);
        this.messageGenerator = messageGenerator;
    }

    @Override
    public void run() {
        newSingleThreadExecutor(new DaemonThreadFactory()).submit(server);
    }
}
