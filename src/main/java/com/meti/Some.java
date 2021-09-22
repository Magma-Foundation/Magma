package com.meti;

public class Some<T> implements Option<T> {
    private final T value;

    public Some(T value) {
        this.value = value;
    }

    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }
}
