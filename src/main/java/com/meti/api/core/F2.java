package com.meti.api.core;

public interface F2<A, B, C, D extends Exception> {
    C apply(A a, B b) throws D;
}
