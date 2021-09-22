package com.meti;

public interface Option<T> {
    T get();

    boolean isPresent();
}
