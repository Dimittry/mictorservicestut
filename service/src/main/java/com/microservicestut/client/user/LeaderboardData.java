package com.microservicestut.client.user;


import com.microservicestut.infrastructure.MessageListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static javafx.application.Platform.runLater;

public class LeaderboardData implements MessageListener<String> {
    private static final int NUMBER_OF_LEADERS = 18;
    private final Map<String, TwitterUser> allTwitterUsers = new HashMap<>();
    private ObservableList<TwitterUser> items = FXCollections.observableArrayList();

    @Override
    public void onMessage(String twitterHandle) {
        System.err.println(twitterHandle);
        TwitterUser twitterUser
                 = allTwitterUsers.computeIfAbsent(twitterHandle, TwitterUser::new);
//                = allTwitterUsers.computeIfAbsent(twitterHandle,
//                    new Function<String, TwitterUser>(){
//                        @Override
//                        public TwitterUser apply(String message) {
//                            return new TwitterUser(message);
//                        }
//
//                    }
//                );
        twitterUser.incrementCount();

        List<TwitterUser> topTweeters =
                allTwitterUsers.values().stream()
                    .sorted(Comparator.comparing(TwitterUser::getTweets).reversed())
//                        .sorted(new Comparator<TwitterUser>(){
//                            @Override
//                            public int compare(TwitterUser o1, TwitterUser o2) {
//                                return o2.getTweets() - o1.getTweets();
//                            }
//
//                        })
                    .limit(NUMBER_OF_LEADERS)
                    .collect(Collectors.toList());
        runLater(() -> items.setAll(topTweeters));

    }

    public ObservableList<TwitterUser> getItems() {
        return items;
    }

}
