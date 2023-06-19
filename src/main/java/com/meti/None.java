package com.meti;

import java.util.function.Function;

record None<T>() implements Option<T> {
    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public T unwrapOrPanic() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T unwrapOrElse(T other) {
        return other;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }
}
