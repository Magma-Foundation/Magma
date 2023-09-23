package com.meti.api.option;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Option<T> {
    Option<T> filter(Predicate<T> predicate);

    <R> Option<R> map(Function<T, R> mapper);

    T unwrapOrElseGet(Supplier<T> other);

    Option<T> orElseGet(Supplier<Option<T>> other);

    T $() throws IntentionalException;

    <R> Option<R> replaceValue(R value);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    T unwrapOrElse(T other);
}
