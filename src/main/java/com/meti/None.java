package com.meti;

public class None<T> implements Option<T> {
    @Override
    public <E extends Exception> Option<T> filter(F1<T, Boolean, E> filter) {
        return new None<>();
    }

    @Override
    public boolean isPresent() {
        return false;
    }
}
