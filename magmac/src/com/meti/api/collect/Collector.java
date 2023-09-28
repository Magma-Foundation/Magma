package com.meti.api.collect;

public interface Collector<T, R> {
    R initial();

    R fold(R accumulated, T element);
}
