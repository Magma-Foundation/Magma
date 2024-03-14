package com.meti;

import java.util.function.Function;
import java.util.function.Supplier;

public class Some<T> implements Option<T> {
    private final T value;

    public Some(T value) {
        this.value = value;
    }

    public static <T> Option<T> Some(T value) {
        return new Some<T>(value);
    }

    @Override
    public T orElseNull() {
        return value;
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public Option<T> or(Supplier<Option<T>> supplier) {
        return this;
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
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return other.map(otherValue -> new Tuple<>(value, otherValue));
    }
}
