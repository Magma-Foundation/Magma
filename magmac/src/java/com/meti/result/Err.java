package com.meti.result;

public record Err<T, E extends Throwable>(E value) implements Result<T, E> {
    @Override
    public T $() throws E {
        throw value;
    }
}
