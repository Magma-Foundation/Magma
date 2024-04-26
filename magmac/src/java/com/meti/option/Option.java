package com.meti.option;

import com.meti.collect.Tuple;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    T orElse(T value);

    default <R> R into(Function<Option<T>, R> mapper) {
        return mapper.apply(this);
    }

    T orElseGet(Supplier<T> supplier);

    <R> Option<Tuple<T, R>> and(Option<R> option);
}
