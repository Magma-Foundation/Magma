package com.meti.api.option;

import com.meti.api.tuple.Tuple;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class None<T> implements Option<T> {
    @Override
    public <R> Option<R> replaceValue(R value) {
        return new None<>();
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return new None<>();
    }

    @Override
    public T unwrapOrElse(T other) {
        return other;
    }

    @Override
    public <R> R into(Function<Option<T>, R> mapper) {
        return mapper.apply(this);
    }

    @Override
    public Tuple<Boolean, T> unwrapToTuple(T other) {
        return new Tuple<>(false, other);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    public static <T> Option<T> apply() {
        return new None<>();
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return this;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }

    @Override
    public T unwrapOrElseGet(Supplier<T> other) {
        return other.get();
    }

    @Override
    public Option<T> orElseGet(Supplier<Option<T>> other) {
        return other.get();
    }

    @Override
    public T $() throws IntentionalException {
        throw new IntentionalException();
    }
}
