package com.meti.api.option;

public interface Action<T, E extends Throwable> {
    T act() throws E;
}
