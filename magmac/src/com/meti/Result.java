package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Result<T, E extends Exception> {
    T unwrap() throws E;

    <R, O> Result<O, E> and(Result<R, E> other, BiFunction<T, R, O> folder);

    <R> Result<R, E> mapValue(Function<T, R> mapper);
}
