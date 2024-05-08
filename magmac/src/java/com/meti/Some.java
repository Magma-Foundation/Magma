package com.meti;

import java.util.function.Function;

public record Some<T>(T t) implements Option<T> {
    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return other.map(value -> new Tuple<>(t, value));
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(t));
    }
}
