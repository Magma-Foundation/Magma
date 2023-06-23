package com.meti;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    boolean isPresent();

    T unwrapOrPanic();

    T unwrapOrElse(T other);

    <R> Option<Tuple2<T, R>> and(Option<R> other);

    <E extends Throwable> Result<T, E> unwrapOrThrow(Supplier<E> supplier);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    <R> R match(Function<T, R> ifPresent, Supplier<R> ifEmpty);

    boolean isEmpty();

    Option<T> filter(Predicate<T> predicate);

    T unwrapOrElseGet(Supplier<T> other);
}
