package com.meti.option;

import java.util.function.Function;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    T orElse(T value);

    default <R> R into(Function<Option<T>, R> mapper) {
        return mapper.apply(this);
    }
}
