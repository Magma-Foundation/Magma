package com.meti.api;

public record Ok<T, E>(T value) implements Result<T, E> {
}
