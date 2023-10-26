package com.meti.compile.option;

import com.meti.compile.Tuple;

import java.util.function.Consumer;
import java.util.function.Function;

public class Some<T> implements Option<T> {
    private final T value;

    public Some(T value) {
        this.value = value;
    }

    public static <T> Option<T> apply(T value) {
        return new Some<>(value);
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public Tuple<Boolean, T> unwrapToTuple(T other) {
        return new Tuple<>(true, value);
    }

    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return other.map(otherValue -> new Tuple<>(this.value, otherValue));
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        consumer.accept(value);
    }

    @Override
    public T unwrapOrElse(T other) {
        return value;
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return mapper.apply(value);
    }
}
