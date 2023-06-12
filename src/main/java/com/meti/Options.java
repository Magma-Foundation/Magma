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
            public T unwrapOrElse(T other) {
                return value;
            }

            @Override
            public boolean isPresent() {
                return true;
            }

            @Override
            public T unwrapOrPanic() {
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
            public T unwrapOrElse(T other) {
                return other;
            }

            @Override
            public boolean isPresent() {
                return false;
            }

            @Override
            public T unwrapOrPanic() {
                throw new UnsupportedOperationException("No values present.");
            }
        };
    }
}
