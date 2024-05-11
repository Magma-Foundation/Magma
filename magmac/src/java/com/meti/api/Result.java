package com.meti.api;

public interface Result<T, R> {
    Option<T> value();
}
