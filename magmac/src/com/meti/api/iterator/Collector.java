package com.meti.api.iterator;

public interface Collector<T, R> {
    R initial();

    R fold(R current, T element);
}
