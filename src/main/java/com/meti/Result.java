package com.meti;

import java.util.function.Function;

interface Result<T, E> {
    Option<T> value();

    Option<E> err();

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    <R> Result<R, E> mapValueToResult(Function<T, Result<R, E>> mapper);
}
