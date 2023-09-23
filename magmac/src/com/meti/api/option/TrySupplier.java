package com.meti.api.option;

public interface TrySupplier<T> {
    T get() throws IntentionalException;
}
