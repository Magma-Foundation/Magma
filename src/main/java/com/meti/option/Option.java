package com.meti.option;

import com.meti.core.C1;
import com.meti.core.F1;

public interface Option<T> {
    <R, E extends Exception> Option<R> flatMap(F1<T, Option<R>, E> mapper) throws E;

    <E extends Exception> void ifPresent(C1<T, E> consumer) throws E;

    boolean isEmpty();

    boolean isPresent();

    <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E;

    T orElse(T other);

    <E extends Exception> T orElseThrow(Supplier<E, E> supplier) throws E;
}
