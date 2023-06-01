package com.meti;

import java.util.NoSuchElementException;

public interface Option<T> {
    static <T> Option<T> Some(T value) {
        return new Some<>(value);
    }

    static <T> Option<T> None() {
        return new None<>();
    }

    T unwrap();

    T unwrapOrElse(T other);

    class Some<T> implements Option<T> {
        private final T value;

        public Some(T value) {
            this.value = value;
        }

        @Override
        public T unwrap() {
            return value;
        }

        @Override
        public T unwrapOrElse(T other) {
            return value;
        }
    }

    class None<T> implements Option<T> {
        @Override
        public T unwrap() {
            throw new NoSuchElementException("No value present.");
        }

        @Override
        public T unwrapOrElse(T other) {
            return other;
        }
    }
}
