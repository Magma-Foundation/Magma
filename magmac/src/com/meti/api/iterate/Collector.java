package com.meti.api.iterate;

public interface Collector<T, R> {
    R initial();

    R fold(R accumulated, T value);
}
