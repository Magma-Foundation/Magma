package com.meti;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    <R> Option<Tuple<T, R>> and(Option<R> other);

    <R> Option<R> map(Function<T, R> mapper);

    Option<T> orLazy(Supplier<Option<T>> other);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    T orElse(T other);

    boolean isEmpty();

    T orElseNull();
}
