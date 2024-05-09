package com.meti.util;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    T $() throws OptionException;

    <R> R match(Function<T, R> mapper, Supplier<R> supplier);

    T orElse(T other);
}
