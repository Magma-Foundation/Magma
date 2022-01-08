package com.meti.option;

import com.meti.core.F1;

public interface Option<T> {
    boolean isPresent();

    <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E;

    Option<T> or(Option<T> other);

    T orElse(T other);

    <E extends Exception> T orElseGet(Supplier<T, E> supplier) throws E;

    <E extends Exception> T orElseThrow(Supplier<E, E> supplier) throws E;
}
