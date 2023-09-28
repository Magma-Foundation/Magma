package com.meti.api.collect;

import com.meti.api.option.Option;

import java.util.function.Function;

public interface Iterator<T> {
    Option<T> head();

    <R> Iterator<R> map(Function<T, R> mapper);

    <R> R collect(Collector<T, R> collector);
}
