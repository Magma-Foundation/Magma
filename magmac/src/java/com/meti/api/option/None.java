package com.meti.api.option;

import com.meti.api.result.Tuple;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class None<T> implements Option<T> {
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(false, other);
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <U, R> Option<R> xnor(Option<U> contentEnd, BiFunction<T, U, R> mapper, Supplier<R> supplier) {
        return contentEnd.<Option<R>>map(inner -> new None<>()).orElseGet(() -> new Some<>(supplier.get()));
    }

    @Override
    public T orElseGet(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return new None<>();
    }
}
