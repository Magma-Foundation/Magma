package com.meti;

import java.util.function.Function;

public interface Result<T, E extends Throwable> {
    T unwrap() throws E;

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    <R> Result<R, E> mapValueExceptionally(Function<T, Result<R, E>> mapper);
}
