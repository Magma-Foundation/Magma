package com.meti.core;

import java.util.function.Function;

public interface Option<T> {
    T unwrapOrPanic();

    boolean isPresent();

    <R> Option<R> map(Function<T, R> mapper);

    T unwrapOrElse(T other);
}
