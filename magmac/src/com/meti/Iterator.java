package com.meti;

import java.util.function.Function;

public interface Iterator<T> {
    <R> Iterator<R> map(Function<T, R> mapper);

    Option<T> head();
}
