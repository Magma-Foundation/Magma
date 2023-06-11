package com.meti;

import java.util.function.Function;

interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    T unwrap();

    T unwrapOrElse(T other);

    boolean isPresent();

    T unwrapOrPanic();
}
