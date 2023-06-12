package com.meti;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

interface Result<T, E extends Exception> {
    <R> R match(Function<T, R> onOk, Function<E, R> onErr);

    void consume(Consumer<T> onOk, Consumer<E> onErr);

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    T unwrapOrPanic() throws E;

    <R, S> Result<S, E> merge(Result<R, E> other, BiFunction<T, R, S> merger);
}
