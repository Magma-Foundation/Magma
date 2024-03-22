package com.meti.collect.option;

import com.meti.collect.Pair;

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

    <R> Option<Pair<T, R>> and(Option<R> other);

    T orElseGet(Supplier<T> other);

    Pair<Boolean, T> toResolvedTuple(T other);

    boolean isEmpty();

    T $() throws IntentionalException;

    default <R> R into(Function<Option<T>, R> mapper) {
        return mapper.apply(this);
    }

    void ifPresent(Consumer<T> consumer);

    Option<T> orLazy(Supplier<Option<T>> other);

    <R> Option<Pair<T, R>> andLazy(Supplier<Option<R>> other);
}
