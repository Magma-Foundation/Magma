package com.meti.collect.stream;

public interface Collector<T, C> {
    C initial();

    C fold(C current, T element);
}
