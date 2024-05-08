package com.meti;

public record Some<T>(T t) implements Option<T> {
}
