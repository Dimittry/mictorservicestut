package com.microservicestut.client.mood;

import java.util.Set;

import static java.util.stream.Collectors.joining;

public class MoodyMessage {
    private final Set<Mood> moods;

    public MoodyMessage(Set<Mood> moods) {
        this.moods = moods;
    }

    public boolean hasMood(Mood mood) {
        return moods.contains(mood);
    }

    @Override
    public String toString() {
        return moods.stream()
                .map(Enum::toString)
                .collect(joining(","));
    }
}
