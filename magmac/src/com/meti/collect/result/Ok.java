package com.meti.collect.result;

import com.meti.collect.Tuple;

import java.util.function.Function;

public class Ok<T, E extends Throwable> implements Result<T, E> {
    private final T value;

    public Ok(T value) {
        this.value = value;
    }

    public static <T, E extends Throwable> Result<T, E> Ok(T value) {
        return new Ok<>(value);
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Ok<>(mapper.apply(value));
    }

    @Override
    public T $() {
        return value;
    }

    @Override
    public <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper) {
        return new Ok<>(value);
    }

    @Override
    public <R> Result<Tuple<T, R>, E> and(Result<R, E> other) {
        return other.mapValue(otherValue -> new Tuple<>(value, otherValue));
    }
}
