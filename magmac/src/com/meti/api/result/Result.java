package com.meti.api.result;

import java.util.function.Function;

public interface Result<T, E extends Throwable> {
    <R> Result<R, E> mapValue(Function<T, R> mapper);

    T $() throws E;

    <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper);
}
