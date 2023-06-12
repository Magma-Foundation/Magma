package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Iterable<T> {
    Option<T> head();

    <R> Iterable<R> map(Function<T, R> mapper);

    <R> R foldLeft(R initial, BiFunction<R, T, R> folder);

    <R> R collect(Collector<R, T> collector);

    Iterable<T> filter(Predicate<T> filter);

    <R> Iterable<R> flatMap(Function<T, Iterable<R>> mapper);

    <R> Iterable<Tuple<T, R>> zip(Iterable<R> iter);

    boolean allMatch(Predicate<T> predicate);

    Option<Iterable<T>> take(int count);

    interface Collector<R, T> {
        R initial();

        R fold(R current, T element);
    }
}
