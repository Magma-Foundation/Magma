package com.meti.safe.iter;

import com.meti.safe.Tuple2;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Iterator<T> {
    Option<T> head();

    <R> R foldLeft(R state, BiFunction<R, T, R> folder);

    <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

    <R, E extends Throwable> Result<R, E> foldLeftResult(R state, BiFunction<R, T, Result<R, E>> folder);

    <R> Iterator<Tuple2<T, R>> zip(Iterator<R> other);

    <R> Iterator<R> map(Function<T, R> mapper);

    Iterator<T> filter(Predicate<T> predicate);

    boolean allMatch(Predicate<T> predicate);

    <R> R collect(Collector<R, T> collector);
}
