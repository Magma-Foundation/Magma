package com.meti.util;

import java.util.function.Function;
import java.util.function.Supplier;

public record Some<T>(T value) implements Option<T> {
    @Override
    public T $() {
        return value;
    }

    @Override
    public <R> R match(Function<T, R> mapper, Supplier<R> supplier) {
        return mapper.apply(value);
    }

    @Override
    public T orElse(T other) {
        return value;
    }
}
