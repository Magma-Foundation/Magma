package com.meti.option;

import com.meti.core.F1;

public interface Option<T> {
    <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E;

    T orElse(T other);

    boolean isPresent();
}
