package com.meti;

import java.util.function.Function;

record Some<T>(T value) implements Option<T> {
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
        return new Some<>(mapper.apply(value));
    }

    @Override
    public T unwrapOrElse(T other) {
        return value;
    }
}
