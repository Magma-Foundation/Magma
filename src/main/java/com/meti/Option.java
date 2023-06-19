package com.meti;

import java.util.function.Function;

interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    boolean isPresent();

    T unwrapOrPanic();

    T unwrapOrElse(T other);
}
