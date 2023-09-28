package com.meti.api.option;

import com.meti.api.tuple.Tuple;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record Some<T>(T value) implements Option<T> {
    public static <T> Option<T> apply(T value) {
        return new Some<>(value);
    }

    @Override
    public <R> Option<R> replaceValue(R value) {
        return new Some<>(value);
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public T unwrapOrElse(T other) {
        return value;
    }

    @Override
    public <R> R into(Function<Option<T>, R> mapper) {
        return mapper.apply(this);
    }

    @Override
    public Tuple<Boolean, T> unwrapToTuple(T other) {
        return new Tuple<>(true, value);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return predicate.test(value) ? this : None.apply();
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return Some.apply(mapper.apply(value));
    }

    @Override
    public T unwrapOrElseGet(Supplier<T> other) {
        return value;
    }

    @Override
    public Option<T> orElseGet(Supplier<Option<T>> other) {
        return this;
    }

    @Override
    public T $() {
        return value;
    }
}
