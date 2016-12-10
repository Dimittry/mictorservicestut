package com.microservicestut.client.mood;

import com.microservicestut.infrastructure.MessageHandler;
import com.microservicestut.infrastructure.Service;

public class MoodService implements Runnable {
    private final Service<MoodService> service;

    public MoodService() {
        service = new Service<>("ws://localhost:8081/tweets/", "/moods/",
                8082,
                MoodAnalyser::analyseMood);
    }

    @Override
    public void run() {
        service.run();
    }

    public void stop() throws Exception {
        service.stop();
    }

}
