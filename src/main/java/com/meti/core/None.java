package com.meti.core;

import java.util.function.Function;
import java.util.function.Supplier;

public class None<T> implements Option<T> {
    @Override
    public T $() throws IntentionalException {
        throw new IntentionalException();
    }

    @Override
    public T unwrap() {
        throw new RuntimeException("No unwrap present.");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Option<T> or(Option<T> other) {
        return other;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }

    @Override
    public T unwrapOrElse(T other) {
        return other;
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(false, other);
    }

    @Override
    public T unwrapOrElseGet(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return new None<>();
    }
}
