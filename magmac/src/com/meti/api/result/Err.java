package com.meti.api.result;

public record Err<T, E>(E value) implements Result<T, E> {

    public static <T, E> Result<T, E> apply(E value) {
        return new Err<>(value);
    }
}
