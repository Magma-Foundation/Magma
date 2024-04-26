package com.meti.option;

import com.meti.collect.Tuple;

import java.util.function.Function;
import java.util.function.Supplier;

public record Some<T>(T value) implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public T orElse(T value) {
        return this.value;
    }

    @Override
    public Option<T> or(Option<T> other) {
        return this;
    }

    @Override
    public T orElseGet(Supplier<T> supplier) {
        return value;
    }

    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> option) {
        return option.map(otherValue -> new Tuple<>(value, otherValue));
    }
}
