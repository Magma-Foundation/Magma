package com.meti;

public record Err<T, E extends Throwable>(E e) implements Result<T, E> {
    @Override
    public T $() throws E {
        throw e;
    }
}
