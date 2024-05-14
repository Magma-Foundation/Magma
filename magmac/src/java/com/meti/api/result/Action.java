package com.meti.api.result;

public interface Action<T, E extends Throwable> {
    T run() throws E;
}
