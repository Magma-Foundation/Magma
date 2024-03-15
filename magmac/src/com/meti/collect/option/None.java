package com.meti.collect.option;

import com.meti.collect.Tuple;

import java.util.function.Consumer;
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

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return new None<>();
    }

    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return new None<>();
    }

    @Override
    public T orElseGet(Supplier<T> other) {
        return other.get();
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(false, other);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T $() throws IntentionalException {
        throw new IntentionalException();
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
    }
}