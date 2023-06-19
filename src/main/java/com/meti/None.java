package com.meti;

import java.util.function.Function;
import java.util.function.Supplier;

record None<T>() implements Option<T> {
    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public T unwrapOrPanic() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T unwrapOrElse(T other) {
        return other;
    }

    @Override
    public <R> Option<Tuple2<T, R>> and(Option<R> other) {
        return new None<>();
    }

    @Override
    public <E extends Throwable> Result<T, E> unwrapOrThrow(Supplier<E> supplier) {
        return new Err<>(supplier.get());
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return new None<>();
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }
}
