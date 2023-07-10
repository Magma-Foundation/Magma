package com.meti.core;

public interface Generator<T, E extends Exception> {
    T generate() throws E;
}
