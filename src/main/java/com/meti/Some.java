package com.meti;

import java.util.function.Function;

record Some<T>(T value) implements Option<T> {
    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public T unwrapOrPanic() {
        return value;
    }

    @Override
    public T unwrapOrElse(T other) {
        return value;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }
}
