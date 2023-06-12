package com.meti;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

class Results {
    static <T, E extends Exception> Result<T, E> Ok(T value) {
        return new Result<>() {
            @Override
            public <R> R match(Function<T, R> onOk, Function<E, R> onErr) {
                return onOk.apply(value);
            }

            @Override
            public void consume(Consumer<T> onOk, Consumer<E> onErr) {
                onOk.accept(value);
            }

            @Override
            public <R> Result<R, E> mapValue(Function<T, R> mapper) {
                return Ok(mapper.apply(value));
            }

            @Override
            public T unwrapOrPanic() {
                return value;
            }

            @Override
            public <R, S> Result<S, E> merge(Result<R, E> other, BiFunction<T, R, S> merger) {
                return other.mapValue(otherValue -> merger.apply(value, otherValue));
            }
        };
    }

    static <T, E extends Exception> Result<T, E> Err(E e) {
        return new Result<>() {
            @Override
            public <R> R match(Function<T, R> onOk, Function<E, R> onErr) {
                return onErr.apply(e);
            }

            @Override
            public void consume(Consumer<T> onOk, Consumer<E> onErr) {
                onErr.accept(e);
            }

            @Override
            public <R> Result<R, E> mapValue(Function<T, R> mapper) {
                return Err(e);
            }

            @Override
            public T unwrapOrPanic() throws E {
                throw e;
            }

            @Override
            public <R, S> Result<S, E> merge(Result<R, E> other, BiFunction<T, R, S> merger) {
                return Err(e);
            }
        };
    }
}