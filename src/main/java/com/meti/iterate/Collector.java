package com.meti.iterate;

public interface Collector<T, C> {
    C initial();

    C foldLeft(C accumulated, T element);
}
