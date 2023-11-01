package com.meti.api.result;

import java.util.function.Function;

public class Err<T, E extends Throwable> implements Result<T, E> {
    private final E value;

    public Err(E value) {
        this.value = value;
    }

    public static <T, E extends Throwable> Result<T, E> apply(E value) {
        return new Err<>(value);
    }

    @Override
    public T unwrap() throws E {
        throw value;
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Err<>(value);
    }

    @Override
    public <R> Result<R, E> mapValueExceptionally(Function<T, Result<R, E>> mapper) {
        return new Err<>(value);
    }

    @Override
    public T $() throws E {
        throw value;
    }
}
