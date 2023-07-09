package com.meti.core;

import java.util.function.Function;

public record Ok<T, E>(T inner) implements Result<T, E> {
    @Override
    public Option<T> value() {
        return Some.apply(inner);
    }

    @Override
    public Option<E> err() {
        return new None<>();
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Ok<>(mapper.apply(inner));
    }

    @Override
    public <R> Result<R, E> mapValueToResult(Function<T, Result<R, E>> mapper) {
        return mapper.apply(inner);
    }
}
