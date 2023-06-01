package com.meti;

import java.util.NoSuchElementException;

public interface Option<T> {
    static <T> Option<T> Some(T value) {
        return new Option<T>() {
            @Override
            public T unwrap() {
                return value;
            }
        };
    }

    static <T> Option<T> None() {
        return new Option<T>() {
            @Override
            public T unwrap() {
                throw new NoSuchElementException("No value present.");
            }
        };
    }

    T unwrap();
}
