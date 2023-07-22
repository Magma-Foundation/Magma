package com.meti.core;

public class ThrowableResult<T, E extends Exception> {
    private final Result<T, E> parent;

    public ThrowableResult(Result<T, E> parent) {
        this.parent = parent;
    }

    public T $() throws E {
        var value = parent.value();
        var err = parent.err();

        if (value.isPresent()) return value.unwrap();
        if (err.isPresent()) throw err.unwrap();
        throw new RuntimeException("No name1 or error present?");
    }
}
