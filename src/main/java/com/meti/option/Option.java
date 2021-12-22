package com.meti.option;

import com.meti.core.F1;

public interface Option<T> {
    <E extends Exception> Option<T> filter(F1<T, Boolean, E> filter) throws E;

    boolean isPresent();

    <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E;

    T orElse(T other);
}
