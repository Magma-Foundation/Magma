package com.meti;

import java.util.function.Function;

public interface Result<T, E extends Throwable> {
    <R> Result<R, E> mapValue(Function<T, R> mapper);
    <R> Result<R, E> mapValueToResult(Function<T, Result<R, E>> mapper);

    T unwrap() throws E;
}
