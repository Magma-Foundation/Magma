package com.meti;

import java.util.function.Function;

public interface Option<T> {
    <R> Option<Tuple<T, R>> and(Option<R> other);

    <R> Option<R> map(Function<T, R> mapper);
}
