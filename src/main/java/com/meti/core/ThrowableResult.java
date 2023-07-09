package com.meti.core;

public class ThrowableResult<T, E extends Exception> {
    private final Result<T, E> parent;

    public ThrowableResult(Result<T, E> parent) {
        this.parent = parent;
    }

    public T $() throws E {
        var value = parent.value();
        var err = parent.err();

        if (value.isPresent()) return value.unwrapOrPanic();
        if (err.isPresent()) throw err.unwrapOrPanic();
        throw new RuntimeException("No value or error present?");
    }
}
