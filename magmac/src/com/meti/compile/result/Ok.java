package com.meti.compile.result;

import java.util.function.Function;

public class Ok<T, E extends Throwable> implements Result<T, E> {
    private final T value;

    private Ok(T value) {
        this.value = value;
    }

    public static <T, E extends Throwable> Result<T, E> apply(T value) {
        return new Ok<T, E>(value);
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
    public <R> Result<R, E> mapValueExceptionally(Function<T, Result<R, E>> mapper) {
        return mapper.apply(value);
    }
}
