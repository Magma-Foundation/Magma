package com.meti.api;

public interface Action<T, E extends Throwable> {
    T act() throws E;
}
