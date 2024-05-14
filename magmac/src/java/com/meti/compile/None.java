package com.meti.compile;

import com.meti.api.result.Tuple;

import java.util.function.Function;

public class None<T> implements Option<T> {
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(false, other);
    }

    @Override
    public boolean isPresent() {
        return false;
    }
}
