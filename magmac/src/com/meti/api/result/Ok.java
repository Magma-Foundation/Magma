package com.meti.api.result;

public record Ok<T, E>(T value) implements Result<T, E> {
    public static <T, E> Result<T, E> apply(T value) {
        return new Ok<>(value);
    }
}
