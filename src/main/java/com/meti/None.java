package com.meti;

public class None<T> implements Option<T> {
    @Override
    public T get() {
        return null;
    }

    @Override
    public boolean isPresent() {
        return false;
    }
}
