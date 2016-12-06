package com.microservicestut.infrastructure;

@FunctionalInterface
public interface MessageHandler<T> {
    T processMessage(String message);
}
