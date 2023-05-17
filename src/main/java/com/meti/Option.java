package com.meti;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    void ifPresent(Consumer<T> consumer);

    T unwrapOrElse(T other);
}
