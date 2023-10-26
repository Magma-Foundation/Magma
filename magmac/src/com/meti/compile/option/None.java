package com.meti.compile.option;

import com.meti.compile.Tuple;

import java.util.function.Consumer;
import java.util.function.Function;

public class None<T> implements Option<T> {
    public static <T> Option<T> apply() {
        return new None<>();
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }

    @Override
    public Tuple<Boolean, T> unwrapToTuple(T other) {
        return new Tuple<>(false, other);
    }

    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return new None<>();
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
    }

    @Override
    public T unwrapOrElse(T other) {
        return other;
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return new None<>();
    }
}
