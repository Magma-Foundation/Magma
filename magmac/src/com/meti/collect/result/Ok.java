package com.meti.collect.result;

import com.meti.collect.Pair;

import java.util.function.Consumer;
import java.util.function.Function;

public class Ok<T, E extends Throwable> implements Result<T, E> {
    private final T value;

    public Ok(T value) {
        this.value = value;
    }

    public static <T, E extends Throwable> Result<T, E> Ok(T value) {
        return new Ok<>(value);
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Ok<>(mapper.apply(value));
    }

    @Override
    public T $() {
        return value;
    }

    @Override
    public <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper) {
        return new Ok<>(value);
    }

    @Override
    public void consume(Consumer<T> valueConsumer, Consumer<E> errorConsumer) {
        valueConsumer.accept(value);
    }

    @Override
    public <R> R match(Function<T, R> valueConsumer, Function<E, R> errorConsumer) {
        return valueConsumer.apply(value);
    }

    @Override
    public <R> Result<Pair<T, R>, E> and(Result<R, E> other) {
        return other.mapValue(otherValue -> new Pair<>(value, otherValue));
    }
}
