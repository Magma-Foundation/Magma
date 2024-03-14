package com.meti.collect.option;

public interface Action<T, E extends Throwable> {
    T perform() throws E;
}
