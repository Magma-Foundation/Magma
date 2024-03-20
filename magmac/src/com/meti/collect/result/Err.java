package com.meti.collect.result;

import com.meti.collect.Pair;

import java.util.function.Consumer;
import java.util.function.Function;

public class Err<T, E extends Throwable> implements Result<T, E> {
    private final E value;

    public Err(E value) {
        this.value = value;
    }

    public static <T, E extends Throwable> Result<T, E> Err(E value) {
        return new Err<>(value);
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return new Err<>(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Err<>(value);
    }

    @Override
    public <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper) {
        try {
            var apply = mapper.apply(value);
            try {
                return new Err<>(apply);
            } catch (Exception e) {
                throw new RuntimeException(apply);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(value);
        }
    }

    @Override
    public void consume(Consumer<T> valueConsumer, Consumer<E> errorConsumer) {
        errorConsumer.accept(value);
    }

    @Override
    public T $() throws E {
        throw value;
    }

    @Override
    public <R> Result<Pair<T, R>, E> and(Result<R, E> other) {
        return new Err<>(value);
    }
}
