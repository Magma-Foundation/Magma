package com.meti;

import java.util.function.Function;
import java.util.function.Supplier;

public class None<T> implements Option<T> {
    public static <T> Option<T> None() {
        return new None<>();
    }

    @Override
    public T orElseNull() {
        return null;
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public Option<T> or(Supplier<Option<T>> supplier) {
        return supplier.get();
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }
}
