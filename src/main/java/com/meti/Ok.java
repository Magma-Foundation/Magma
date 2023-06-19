package com.meti;

import java.util.function.Function;

public class Ok<T, E extends Throwable> implements Result<T, E> {
    private final T value;

    private Ok(T value) {
        this.value = value;
    }

    public static <T, E extends Throwable> Result<T, E> of(T value) {
        return new Ok<T, E>(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return of(mapper.apply(value));
    }

    @Override
    public <R> Result<R, E> mapValueToResult(Function<T, Result<R, E>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public T unwrap() throws E {
        return value;
    }
}
