package com.meti;

import java.util.function.BiFunction;

public interface Iterable<T> {
    <R> R foldLeft(R initial, BiFunction<R, T, R> folder);
}
