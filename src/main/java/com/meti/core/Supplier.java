package com.meti.core;

public interface Supplier<A, B extends Exception> {
    A apply() throws B;
}
