package com.meti;

public class None<T> implements Option<T> {
    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return new None<>();
    }
}
