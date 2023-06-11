package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Iterable<T> {
    <R> Iterable<R> map(Function<T, R> mapper);

    <R> R foldLeft(R initial, BiFunction<R, T, R> folder);

    <R> R collect(Collector<R, T> collector);

    interface Collector<R, T> {
        R initial();

        R fold(R current, T element);
    }
}
