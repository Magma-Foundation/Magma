package com.meti;

import java.util.function.Function;
import java.util.function.Supplier;

interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    T unwrapOrElse(T other);

    boolean isPresent();

    T unwrapOrPanic();

    <R> R match(Function<T, R> onSome, Supplier<R> onNone);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    boolean isEmpty();
}
