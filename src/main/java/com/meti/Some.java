package com.meti;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class Some<T> implements Option<T> {
    private final T value;

    public Some(T value) {
        this.value = value;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        consumer.accept(value);
    }

    @Override
    public T unwrapOrElse(T other) {
        return value;
    }
}
