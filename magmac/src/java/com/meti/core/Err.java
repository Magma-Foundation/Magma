package com.meti.core;

public class Err<T, E> implements Result<T, E> {
    private final E value;

    public Err(E value) {
        this.value = value;
    }
}
