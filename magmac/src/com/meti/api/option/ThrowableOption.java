package com.meti.api.option;

import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ThrowableOption<T> implements Option<T> {
    private final Option<T> parent;

    public ThrowableOption(Option<T> parent) {
        this.parent = parent;
    }

    public <E> Result<T, E> unwrapOrThrow(E value) {
        return parent.map(Ok::<T, E>apply).unwrapOrElse(Err.apply(value));
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return parent.filter(predicate);
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return parent.map(mapper);
    }

    @Override
    public T unwrapOrElseGet(Supplier<T> other) {
        return parent.unwrapOrElseGet(other);
    }

    @Override
    public Option<T> orElseGet(Supplier<Option<T>> other) {
        return parent.orElseGet(other);
    }

    @Override
    public T $() throws IntentionalException {
        return parent.$();
    }

    @Override
    public <R> Option<R> replaceValue(R value) {
        return parent.replaceValue(value);
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return parent.flatMap(mapper);
    }

    @Override
    public T unwrapOrElse(T other) {
        return parent.unwrapOrElse(other);
    }

    @Override
    public <R> R into(Function<Option<T>, R> mapper) {
        return parent.into(mapper);
    }
}
