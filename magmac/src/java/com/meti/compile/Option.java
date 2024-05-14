package com.meti.compile;

import com.meti.api.result.Tuple;

import java.util.function.Function;

public interface Option<T> {
    boolean isEmpty();

    <R> Option<R> map(Function<T, R> mapper);

    T orElse(T other);

    Tuple<Boolean, T> toTuple(T other);

    boolean isPresent();
}
