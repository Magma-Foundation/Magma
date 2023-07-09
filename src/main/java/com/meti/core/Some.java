package com.meti.core;

import java.util.function.Function;

public record Some<T>(T value) implements Option<T> {
    public static <T> Option<T> apply(T value) {
        return new Some<>(value);
    }

    @Override
    public T unwrapOrPanic() {
        return value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return apply(mapper.apply(value));
    }

    @Override
    public T unwrapOrElse(T other) {
        return value;
    }
}
