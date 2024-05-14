package com.meti.compile;

import com.meti.api.result.Tuple;

import java.util.function.Function;

public record Some<T>(T value) implements Option<T> {
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(true, value);
    }

    @Override
    public boolean isPresent() {
        return true;
    }
}
