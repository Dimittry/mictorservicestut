package com.microservicestut.client.mood;

import java.util.Set;

import static com.microservicestut.client.mood.Mood.HAPPY;
import static com.microservicestut.client.mood.Mood.SAD;

public class TweetMood {
    private final Set<Mood> moods;

    public TweetMood(Set<Mood> moods) {
        this.moods = moods;
    }

    public boolean isHappy() {
        return moods.contains(HAPPY);
    }

    public boolean isSad() {
        return moods.contains(SAD);
    }

    public boolean isConfused() {
        return isHappy() && isSad();
    }
}
