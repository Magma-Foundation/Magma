package com.meti.util;

import java.util.function.Function;
import java.util.function.Supplier;

public class None<T> implements Option<T> {
    @Override
    public T $() throws OptionException {
        throw new OptionException();
    }

    @Override
    public <R> R match(Function<T, R> mapper, Supplier<R> supplier) {
        return supplier.get();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return new None<>();
    }
}
