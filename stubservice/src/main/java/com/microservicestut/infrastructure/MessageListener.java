package com.microservicestut.infrastructure;

@FunctionalInterface
public interface MessageListener<T> {
    void onMessage(T message);
}
