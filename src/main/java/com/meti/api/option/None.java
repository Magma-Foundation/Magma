package com.meti.api.option;

import com.meti.api.F1;

public class None<T> implements Option<T> {
    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) {
        return new None<>();
    }

    @Override
    public T orElse(T other) {
        return other;
    }
}
