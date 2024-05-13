package com.meti.result;

public interface Action<T, E extends Throwable> {
    T run() throws E;
}
