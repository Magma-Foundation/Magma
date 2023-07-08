package com.meti;

import java.util.function.Function;

class None<T> implements Option<T> {
    @Override
    public T unwrapOrPanic() {
        throw new RuntimeException("No unwrap present.");
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }

    @Override
    public T unwrapOrElse(T other) {
        return other;
    }
}
