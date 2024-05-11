package com.meti.api;

public record Ok<T, E>(T value) implements Result<T, E> {
    @Override
    public Option<T> findValue() {
        return new Some<>(value);
    }
}
