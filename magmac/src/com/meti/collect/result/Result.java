package com.meti.collect.result;

import com.meti.collect.Tuple;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Result<T, E extends Throwable> {
    <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper);

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    T $() throws E;

    <R> Result<Tuple<T, R>, E> and(Result<R, E> other);

    <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper);

    void consume(Consumer<T> valueConsumer, Consumer<E> errorConsumer);
}
