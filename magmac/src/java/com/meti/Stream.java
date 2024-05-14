package com.meti;

import com.meti.compile.CompileException;
import com.meti.compile.Option;
import com.meti.result.Result;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<T> {
    Option<T> head();

    Stream<T> filter(Predicate<T> predicate);

    <R> Stream<R> flatMap(Function<T, Stream<R>> mapper);

    <R> Stream<R> map(Function<T, R> mapper);

    default <R> R into(Function<Stream<T>, R> mapper) {
        return mapper.apply(this);
    }

    <C> C collect(Collector<T, C> collector);
}
