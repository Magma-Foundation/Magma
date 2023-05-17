package com.meti;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class None<T> implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return new None<>();
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
    }

    @Override
    public T unwrapOrElse(T other) {
        return other;
    }
}
