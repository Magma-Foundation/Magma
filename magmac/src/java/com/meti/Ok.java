package com.meti;

public record Ok<T, E extends Throwable>(T value) implements Result<T, E> {
    @Override
    public T $() {
        return value;
    }
}
