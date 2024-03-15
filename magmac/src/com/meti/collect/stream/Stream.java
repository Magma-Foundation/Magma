package com.meti.collect.stream;

import com.meti.collect.Tuple;
import com.meti.collect.option.Option;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<T> {
    Option<T> next();

    <R> Stream<R> map(Function<T, R> mapper);

    Stream<T> filter(Predicate<T> predicate);

    <R> Stream<R> flatMap(Function<T, Stream<R>> mapper);

    <C> C collect(Collector<T, C> collector);

    default <R> R into(Function<Stream<T>, R> mapper) {
        return mapper.apply(this);
    }

    <C> C foldRight(C initial, BiFunction<C, T, C> folder);

    <R> Stream<Tuple<T, R>> extend(Function<T, R> extender);
}