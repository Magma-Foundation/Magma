package com.meti;

import java.util.function.Function;
import java.util.function.Supplier;

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

            @Override
            public <R> R match(Function<T, R> onSome, Supplier<R> onNone) {
                return onSome.apply(value);
            }

            @Override
            public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
                return mapper.apply(value);
            }

            @Override
            public boolean isEmpty() {
                return false;
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

            @Override
            public <R> R match(Function<T, R> onSome, Supplier<R> onNone) {
                return onNone.get();
            }

            @Override
            public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
                return None();
            }

            @Override
            public boolean isEmpty() {
                return true;
            }
        };
    }
}
