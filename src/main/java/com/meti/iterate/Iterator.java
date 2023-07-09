package com.meti.iterate;

import com.meti.collect.Collector;
import com.meti.core.Option;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Iterator<T> {
    <C> C foldLeft(C initial, BiFunction<C, T, C> folder);

    <R> Iterator<R> map(Function<T, R> mapper);

    <R> R into(Function<Iterator<T>, R> mapper);

    Option<T> head();

    <C> C collect(Collector<T, C> collector);
}
