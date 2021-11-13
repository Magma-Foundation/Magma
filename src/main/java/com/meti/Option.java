package com.meti;

public interface Option<T> {
    T orElse(T other);

    boolean isPresent();
}
