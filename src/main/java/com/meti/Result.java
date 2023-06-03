package com.meti;

import java.io.IOException;
import java.util.function.Function;

public interface Result<T> {
    static <T> Result<T> ok(T values) {
        return new Ok<>(values);
    }

    static <T> Result<T> err(IOException e) {
        return new Err<>(e);
    }

    T unwrapValue();

    <R> Result<R> mapValue(Function<T, R> mapper);

    class Ok<T> implements Result<T> {
        private final T value;

        public Ok(T value) {
            this.value = value;
        }

        @Override
        public T unwrapValue() {
            return value;
        }

        @Override
        public <R> Result<R> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(value));
        }
    }

    record Err<T>(IOException e) implements Result<T> {
        @Override
        public T unwrapValue() {
            throw new RuntimeException(e);
        }

        @Override
        public <R> Result<R> mapValue(Function<T, R> mapper) {
            return new Err<>(e);
        }
    }
}
