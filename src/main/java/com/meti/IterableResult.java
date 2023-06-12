package com.meti;

import java.util.function.BiFunction;

public interface IterableResult<T, E extends Exception> extends Iterable<Result<T, E>> {
    <R> Result<R, E> foldLeftByValue(R initial, BiFunction<R, T, R> merger);
}
