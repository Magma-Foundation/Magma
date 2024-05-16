package com.meti.api.option;

import com.meti.api.result.Result;
import com.meti.api.result.Tuple;
import com.meti.compile.JavaString;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    boolean isEmpty();

    <R> Option<R> map(Function<T, R> mapper);

    T orElse(T other);

    Tuple<Boolean, T> toTuple(T other);

    boolean isPresent();

    <U, R> Option<R> xnor(Option<U> contentEnd, BiFunction<T, U, R> mapper, Supplier<R> supplier);

    T orElseGet(Supplier<T> supplier);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    <R> R into(Function<Option<T>, R> mapper);
}
