package com.meti.api.option;

public interface TrySupplier<T, E extends Throwable> {
    T get() throws E;
}
