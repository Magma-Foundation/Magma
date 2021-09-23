package com.meti.option;

import com.meti.core.F1;

public class None<T> implements Option<T> {
    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) {
        return new None<>();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public boolean isPresent() {
        return false;
    }
}
