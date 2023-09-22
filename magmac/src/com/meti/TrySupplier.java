package com.meti;

public interface TrySupplier<T> {
    T get() throws IntentionalException;
}
