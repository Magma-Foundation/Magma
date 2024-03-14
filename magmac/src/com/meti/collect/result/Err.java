package com.meti.collect.result;

import com.meti.collect.Tuple;

import java.util.function.Function;

public class Err<T, E extends Throwable> implements Result<T, E> {
    private final E value;

    public Err(E value) {
        this.value = value;
    }

    public static <T, E extends Throwable> Result<T, E> Err(E value) {
        return new Err<>(value);
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return new Err<>(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Err<>(value);
    }

    @Override
    public T $() throws E {
        throw value;
    }

    @Override
    public <R> Result<Tuple<T, R>, E> and(Result<R, E> other) {
        return new Err<>(value);
    }
}
