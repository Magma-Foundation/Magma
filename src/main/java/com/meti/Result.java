package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;

interface Result<T, E extends Exception> {
    <R> R match(Function<T, R> onOk, Function<E, R> onErr);

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    T unwrapOrPanic() throws E;

    <R, S> Result<S, E> merge(Result<R, E> other, BiFunction<T, R, S> merger);
}
