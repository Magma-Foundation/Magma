package com.meti;

public class Err<T, E extends Throwable> implements Result<T, E> {
    private final E value;

    public Err(E value) {
        this.value = value;
    }

    @Override
    public T unwrap() throws E {
        throw value;
    }
}
