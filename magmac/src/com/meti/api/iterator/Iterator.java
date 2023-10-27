package com.meti.api.iterator;

import com.meti.api.option.Option;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Iterator<T> {
    <R> R collect(Collector<T, R> collector);

    <R> R foldRight(R initial, BiFunction<R, T, R> folder);

    <R> Iterator<R> map(Function<T, R> mapper);

    Option<T> head();

    <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

    Iterator<T> filter(Predicate<T> predicate);
}
