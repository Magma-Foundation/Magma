package com.meti.collect;

public interface Action<T, E extends Throwable> {
    T perform() throws E;
}
