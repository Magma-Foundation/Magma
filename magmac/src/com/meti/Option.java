package com.meti;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    T orElseNull();

    T orElse(T other);

    Option<T> or(Supplier<Option<T>> supplier);

    boolean isPresent();

    <R> Option<R> map(Function<T, R> mapper);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);
}
