package com.meti;

import java.util.function.Function;

public class None<T> implements Option<T> {
    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return new None<>();
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }
}
