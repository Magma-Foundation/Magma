package com.meti;

public interface Collector<T, R> {
    R initial();

    R fold(R current, T element);
}
