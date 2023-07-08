package com.meti;

import java.util.function.Function;

interface Option<T> {
    T unwrapOrPanic();

    boolean isPresent();

    <R> Option<R> map(Function<T, R> mapper);

    T unwrapOrElse(T other);
}
