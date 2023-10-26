package com.meti.compile.iterator;

public interface Collector<T, R> {
    R initial();

    R fold(R current, T element);
}
