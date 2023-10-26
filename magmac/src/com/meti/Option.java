package com.meti;

import java.util.function.Function;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    Tuple<Boolean, T> unwrapToTuple(T other);
}
