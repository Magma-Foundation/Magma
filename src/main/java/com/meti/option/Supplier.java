package com.meti.option;

import com.meti.LexException;

public interface Supplier<T, E extends Exception> {
    T get() throws E;
}
