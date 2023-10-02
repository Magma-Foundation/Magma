package com.meti.api.collect;

import com.meti.api.option.Option;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Iterator<T> {
    Option<T> head();

    <R> Iterator<R> map(Function<T, R> mapper);

    <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

    <R> R collect(Collector<T, R> collector);

    Iterator<T> filter(Predicate<T> predicate);
}
