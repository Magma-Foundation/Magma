package com.meti.api.option;

public interface Supplier<T, E extends Exception> {
    T get() throws E;
}
