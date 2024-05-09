package com.meti.util;

public interface Action<T, E extends Throwable> {
    T act() throws E;
}
