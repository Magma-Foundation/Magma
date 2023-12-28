package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;

public record Err<T, E extends Exception>(E value) implements Result<T, E> {
    public static <T, E extends Exception> Result<T, E> apply(E error) {
        return new Err<>(error);
    }

    @Override
    public T unwrap() throws E {
        throw value;
    }

    @Override
    public <R, O> Result<O, E> and(Result<R, E> other, BiFunction<T, R, O> folder) {
        return new Err<>(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Err<>(value);
    }
}
