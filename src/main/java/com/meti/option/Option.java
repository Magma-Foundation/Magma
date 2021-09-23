package com.meti.option;

import com.meti.core.F1;
import com.meti.core.Supplier;

public interface Option<T> {
    <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E;

    T orElse(T other);

    Option<T> or(Option<T> other);

    <E extends Exception> T orElseThrow(Supplier<E> supplier);

    boolean isPresent();
}
