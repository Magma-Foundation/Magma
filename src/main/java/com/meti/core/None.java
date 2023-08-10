package com.meti.core;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class None<T> implements Option<T> {
    private None() {
    }

    public static <T> Option<T> apply() {
        return new None<>();
    }

    @Override
    public <R> Option<R> replace(R other) {
        return None.apply();
    }

    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return None.apply();
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return None.apply();
    }

    @Override
    public Option<T> peek(Consumer<T> consumer) {
        return None.apply();
    }

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
        return apply();
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
        return apply();
    }
}
