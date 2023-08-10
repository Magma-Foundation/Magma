package com.meti.java;

import java.util.function.Function;

public interface Key<T> {
    <R> R peek(Function<T, R> mapper);

    T unwrap();
}
