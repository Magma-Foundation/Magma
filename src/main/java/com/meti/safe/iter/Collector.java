package com.meti.safe.iter;

public interface Collector<R, T> {
    R initial();

    R folder(R previous, T next);
}
