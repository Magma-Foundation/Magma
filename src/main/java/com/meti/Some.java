package com.meti;

public class Some<T> implements Option<T> {
    private final T value;

    public Some(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }
}
