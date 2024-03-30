package com.meti;

public class Err<T, E> implements Result<T, E> {
    private final E value;

    public Err(E value) {
        this.value = value;
    }
}
