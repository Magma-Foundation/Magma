package com.meti.core;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    T unwrap();

    boolean isPresent();

    <R> Option<R> map(Function<T, R> mapper);

    T unwrapOrElse(T other);

    Tuple<Boolean, T> toTuple(T other);

    T unwrapOrElseGet(Supplier<T> supplier);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);
}
