package com.meti.collect.stream;

import com.meti.collect.Pair;
import com.meti.collect.option.Option;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Stream<T> {
    <R> Stream<Pair<T, R>> cross(Supplier<Stream<R>> other);

    Option<T> next();

    <R> Stream<R> map(Function<T, R> mapper);

    Stream<T> filter(Predicate<T> predicate);

    <R> Stream<R> flatMap(Function<T, Stream<R>> mapper);

    <C> C collect(Collector<T, C> collector);

    default <R> R into(Function<Stream<T>, R> mapper) {
        return mapper.apply(this);
    }

    <C> C foldRightFromInitial(C initial, BiFunction<C, T, C> folder);

    <C> Option<C> foldRight(Function<T, C> mapper, BiFunction<C, T, C> folder);

    <C> Option<C> foldRightFromTwo(BiFunction<T, T, C> initial, BiFunction<C, T, C> folder);

    <R> Stream<Pair<T, R>> extend(Function<T, R> extender);

    Stream<T> concat(Stream<T> other);

    <R> Stream<R> two(BiFunction<T, T, R> mapper, Function<T, R> remaining);
}
