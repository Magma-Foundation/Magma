package com.meti;

public class Ok<T, E extends Throwable> implements Result<T, E> {
    private final T value;

    public Ok(T value) {
        this.value = value;
    }

    @Override
    public T unwrap() throws E {
        return value;
    }
}
