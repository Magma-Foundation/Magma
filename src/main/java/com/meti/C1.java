package com.meti;

public interface C1<A, B extends Exception> {
    void accept(A a) throws B;
}
