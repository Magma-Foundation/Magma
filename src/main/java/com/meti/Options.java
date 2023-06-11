package com.meti;

import java.util.function.Function;

class Options {
    public static <T> Option<T> Some(T value) {
        return new Option<>() {
            @Override
            public <R> Option<R> map(Function<T, R> mapper) {
                return Some(mapper.apply(value));
            }

            @Override
            public T unwrap() {
                return value;
            }

            @Override
            public T unwrapOrElse(T other) {
                return value;
            }
        };
    }

    public static <T> Option<T> None() {
        return new Option<>() {
            @Override
            public <R> Option<R> map(Function<T, R> mapper) {
                return None();
            }

            @Override
            public T unwrap() {
                throw new UnsupportedOperationException("No values present.");
            }

            @Override
            public T unwrapOrElse(T other) {
                return other;
            }
        };
    }
}
