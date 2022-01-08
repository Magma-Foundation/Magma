package com.meti.option;

public interface Supplier<T, E extends Exception> {
    T get() throws E;
}
