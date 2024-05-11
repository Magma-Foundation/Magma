package com.meti.api;

public record Err<T, E>(E err) implements Result<T, E> {
    @Override
    public Option<T> findValue() {
        return new None<>();
    }
}
