package com.microservicestut.client.twitter;

import com.microservicestut.infrastructure.BroadcastingServerEndpoint;
import com.microservicestut.infrastructure.DaemonThreadFactory;
import com.microservicestut.infrastructure.WebSocketServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CannedTweetsService implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(CannedTweetsService.class.getName());

    private final ExecutorService executorService = Executors.newSingleThreadExecutor(new DaemonThreadFactory());
    private final BroadcastingServerEndpoint<String> tweetsEndpoint
            = new BroadcastingServerEndpoint<>();
    private final WebSocketServer server = new WebSocketServer("/tweets/", 8081, tweetsEndpoint);
    private final Path filePath;

    public CannedTweetsService(Path filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) throws URISyntaxException{
        new CannedTweetsService(Paths.get(ClassLoader.getSystemResource("tweets.csv").toURI())).run();
    }

    @Override
    public void run() {
        executorService.submit(server);
        try(Stream<String> lines = Files.lines(filePath)) {
            lines.filter(s -> s != "OK")
                    .peek(s1->{
                        this.addArtificialDelay();
                        LOGGER.log(Level.INFO, s1);
                    })
                    .forEach(tweetsEndpoint::onMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        server.stop();
        executorService.shutdownNow();
    }

    private void addArtificialDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch(InterruptedException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

}
