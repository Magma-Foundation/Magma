package com.meti.core;

public interface F1<A, B, C extends Exception> {
    B apply(A a) throws C;
}
