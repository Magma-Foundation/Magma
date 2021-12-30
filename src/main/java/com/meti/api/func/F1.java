package com.meti.api.func;

public interface F1<A, B, C extends Exception> {
    B apply(A a) throws C;
}
