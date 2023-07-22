package com.meti.core;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    //This should eventually be an extension method
    T $();

    @Deprecated
    T unwrap();

    boolean isPresent();

    <R> Option<R> map(Function<T, R> mapper);

    T unwrapOrElse(T other);

    Tuple<Boolean, T> toTuple(T other);

    T unwrapOrElseGet(Supplier<T> supplier);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    boolean isEmpty();

    Option<T> or(Option<T> other);

    void consumeOrElse(Consumer<T> onPresent, Runnable onEmpty);
}
