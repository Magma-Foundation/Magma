package com.meti.result;

public interface Result<T, E extends Throwable> {
    T $() throws E;
}
