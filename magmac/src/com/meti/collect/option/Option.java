package com.meti.collect.option;

import com.meti.collect.Tuple;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    T orElseNull();

    T orElse(T other);

    Option<T> or(Supplier<Option<T>> supplier);

    boolean isPresent();

    <R> Option<R> map(Function<T, R> mapper);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    <R> Option<Tuple<T, R>> and(Option<R> other);

    T orElseGet(Supplier<T> other);

    Tuple<Boolean, T> toTuple(T other);

    boolean isEmpty();

    T $() throws IntentionalException;

    default <R> R into(Function<Option<T>, R> mapper) {
        return mapper.apply(this);
    }

    void ifPresent(Consumer<T> consumer);
}
