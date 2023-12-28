package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;

public record Ok<T, E extends Exception>(T value) implements Result<T, E> {
    public static <T, E extends Exception> Result<T, E> apply(T value) {
        return new Ok<>(value);
    }

    @Override
    public T unwrap() {
        return value;
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Ok<>(mapper.apply(value));
    }

    @Override
    public <R, O> Result<O, E> and(Result<R, E> other, BiFunction<T, R, O> folder) {
        return other.mapValue(otherValue -> folder.apply(value, otherValue));
    }
}
