package com.meti.collect.result;

import com.meti.collect.Tuple;
import com.meti.collect.option.IntentionalException;
import com.meti.collect.option.Option;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ThrowableOption<T> implements Option<T> {
    private final Option<T> parent;

    public ThrowableOption(Option<T> parent) {
        this.parent = parent;
    }

    @Override
    public Option<T> orLazy(Supplier<Option<T>> other) {
        return parent.orLazy(other);
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        parent.ifPresent(consumer);
    }

    public <E extends Throwable> Result<T, E> orElseThrow(Supplier<E> supplier) {
        return parent.map(Ok::<T, E>Ok).orElseGet(() -> Err.Err(supplier.get()));
    }

    @Override
    public T orElseNull() {
        return parent.orElseNull();
    }

    @Override
    public T orElse(T other) {
        return parent.orElse(other);
    }

    @Override
    public Option<T> or(Supplier<Option<T>> supplier) {
        return parent.or(supplier);
    }

    @Override
    public boolean isPresent() {
        return parent.isPresent();
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return parent.map(mapper);
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return parent.flatMap(mapper);
    }

    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return parent.and(other);
    }

    @Override
    public T orElseGet(Supplier<T> other) {
        return parent.orElseGet(other);
    }

    @Override
    public Tuple<Boolean, T> toResolvedTuple(T other) {
        return parent.toResolvedTuple(other);
    }

    @Override
    public boolean isEmpty() {
        return parent.isEmpty();
    }

    @Override
    public T $() throws IntentionalException {
        return parent.$();
    }
}
