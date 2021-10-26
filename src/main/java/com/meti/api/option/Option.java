package com.meti.api.option;

import com.meti.api.F1;
import com.meti.api.Supplier;

public interface Option<T> {
    <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E;

    T orElse(T other);

    <E extends Exception> T orElseThrow(Supplier<E> supplier);
}
