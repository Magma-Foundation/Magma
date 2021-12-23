package com.meti.option;

import com.meti.core.F1;
import com.meti.core.Supplier;

public interface Option<T> {
    <E extends Exception> Option<T> filter(F1<T, Boolean, E> filter) throws E;

    boolean isPresent();

    <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E;

    Option<T> or(Option<T> other);

    T orElse(T other);

    <E extends Exception> T orElseGet(Supplier<T, E> teSupplier) throws E;

    <E extends Exception> T orElseThrow(Supplier<E, E> supplier) throws E;
}
