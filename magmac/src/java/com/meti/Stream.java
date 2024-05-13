package com.meti;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<T> {
    Stream<T> filter(Predicate<T> predicate);

    <R> Stream<R> flatMap(Function<T, Stream<R>> mapper);
}
