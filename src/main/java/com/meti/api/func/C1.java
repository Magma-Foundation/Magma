package com.meti.api.func;

public interface C1<A, B extends Exception> {
    void accept(A a) throws B;
}
