package com.meti.api.option;

import com.meti.compile.Tuple;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    Tuple<Boolean, T> unwrapToTuple(T other);

    <R> Option<Tuple<T, R>> and(Option<R> other);

    void ifPresent(Consumer<T> consumer);

    T unwrapOrElse(T other);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    Option<T> or(Option<T> other);

    boolean isPresent();

    default <R> R into(Function<Option<T>, R> mapper) {
        return mapper.apply(this);
    }
}
