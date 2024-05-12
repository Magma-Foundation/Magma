package com.meti;

public interface Result<T, E extends Throwable> {
    T $() throws E;
}
