package com.meti;

import java.util.function.BiFunction;

public interface Iterator<T> {
    Option<T> head();

    <R> R foldLeft(R state, BiFunction<R, T, R> folder);

    <R, E extends Throwable> Result<R, E> foldLeftResult(R state, BiFunction<R, T, Result<R, E>> folder);
}
