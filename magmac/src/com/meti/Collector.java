package com.meti;

public interface Collector<T, C> {
    C initial();

    C fold(C current, T element);
}
