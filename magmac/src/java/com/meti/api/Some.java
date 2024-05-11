package com.meti.api;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public record Some<T>(T value) implements Option<T> {
    @Override
    public T $() {
        return value;
    }

    @Override
    public T orElseGet(Supplier<T> other) {
        return value;
    }

    @Override
    public <R> R match(Function<T, R> mapper, Supplier<R> supplier) {
        return mapper.apply(value);
    }

    @Override
    public T orElse(T other) {
        return value;
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
}
