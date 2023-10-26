package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Iterator<T> {
    <R> R collect(Collector<T, R> collector);

    <R> R foldRight(R initial, BiFunction<R, T, R> folder);

    <R> Iterator<R> map(Function<T, R> mapper);

    Option<T> head();
}
