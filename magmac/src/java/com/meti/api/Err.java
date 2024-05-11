package com.meti.api;

public record Err<T, E>(E err) implements Result<T, E> {
}
