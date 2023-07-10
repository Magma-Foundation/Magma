package com.meti.core;

import java.util.function.Function;
import java.util.function.Supplier;

public record Some<T>(T value) implements Option<T> {
    public static <T> Option<T> apply(T value) {
        return new Some<>(value);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(true, value);
    }

    @Override
    public T unwrapOrElseGet(Supplier<T> supplier) {
        return value;
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public T unwrap() {
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
