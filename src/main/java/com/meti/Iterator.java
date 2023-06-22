package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Iterator<T> {
    Option<T> head();

    <R> R foldLeft(R state, BiFunction<R, T, R> folder);

    <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

    <R, E extends Throwable> Result<R, E> foldLeftResult(R state, BiFunction<R, T, Result<R, E>> folder);

    <R> Iterator<Tuple2<T, R>> zip(Iterator<R> other);

    <R> Iterator<R> map(Function<T, R> mapper);
}
