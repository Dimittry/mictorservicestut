package com.microservicestut.client.user;

import com.microservicestut.client.twitter.TweetParser;
import com.microservicestut.infrastructure.MessageHandler;
import com.microservicestut.infrastructure.Service;

public class UserService implements Runnable{
    private final Service<TwitterUser> service;

    public UserService() {
        service = new Service<>("ws://localhost:8081/tweets/", "/users/", 8083, new MessageHandler<TwitterUser>(){
            @Override
            public TwitterUser processMessage(String message) {
                return new TwitterUser(TweetParser.getTwitterHandleFrom(message));
            }
        });
    }

    @Override
    public void run() {
        service.run();
    }

    public void stop() throws Exception {
        service.stop();
    }

    public static void main(String[] args) {
        new UserService().run();
    }
}
