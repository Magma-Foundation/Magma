package com.meti.option;

import java.util.function.Function;
import java.util.function.Supplier;

public class None<T> implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return new None<>();
    }

    @Override
    public T orElse(T value) {
        return value;
    }

    @Override
    public T orElseGet(Supplier<T> supplier) {
        return supplier.get();
    }
}
