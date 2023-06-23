package com.meti;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

record Some<T>(T value) implements Option<T> {
    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return predicate.test(value) ? this : new None<>();
    }

    @Override
    public T unwrapOrPanic() {
        return value;
    }

    @Override
    public T unwrapOrElse(T other) {
        return value;
    }

    @Override
    public <R> Option<Tuple2<T, R>> and(Option<R> other) {
        return other.map(otherValue -> new Tuple2<>(value, otherValue));
    }

    @Override
    public <E extends Throwable> Result<T, E> unwrapOrThrow(Supplier<E> supplier) {
        return Ok.apply(value);
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public <R> R match(Function<T, R> ifPresent, Supplier<R> ifEmpty) {
        return ifPresent.apply(value);
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }
}
