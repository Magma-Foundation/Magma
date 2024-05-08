package com.meti;

import java.util.function.Function;
import java.util.function.Supplier;

public record Some<T>(T value) implements Option<T> {
    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return other.map(otherValue -> new Tuple<>(value, otherValue));
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public Option<T> orLazy(Supplier<Option<T>> other) {
        return this;
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T orElseNull() {
        return value;
    }
}
