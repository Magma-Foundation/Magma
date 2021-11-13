package com.meti.api.option;

import com.meti.api.core.Supplier;

public interface Option<T> {
    boolean isPresent();

    T orElse(T other);

    <E extends Exception> T orElseThrow(Supplier<E> supplier) throws E;
}
