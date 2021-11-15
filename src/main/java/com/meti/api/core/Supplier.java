package com.meti.api.core;

public interface Supplier<T, E extends Exception> {
    T get() throws E;
}
