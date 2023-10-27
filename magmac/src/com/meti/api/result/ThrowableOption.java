package com.meti.api.result;

import com.meti.api.option.Option;
import com.meti.compile.Tuple;

import java.util.function.Consumer;
import java.util.function.Function;

public class ThrowableOption<T> implements Option<T> {
    private final Option<T> inner;

    public ThrowableOption(Option<T> inner) {
        this.inner = inner;
    }

    public <E extends Throwable> Result<T, E> unwrapOrThrow(E value) {
        return inner.map(Ok::<T, E>apply).unwrapOrElse(Err.apply(value));
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return inner.map(mapper);
    }

    @Override
    public Tuple<Boolean, T> unwrapToTuple(T other) {
        return inner.unwrapToTuple(other);
    }

    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return inner.and(other);
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        inner.ifPresent(consumer);
    }

    @Override
    public T unwrapOrElse(T other) {
        return inner.unwrapOrElse(other);
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return inner.flatMap(mapper);
    }

    @Override
    public Option<T> or(Option<T> other) {
        return inner.or(other);
    }

    @Override
    public boolean isPresent() {
        return inner.isPresent();
    }
}
