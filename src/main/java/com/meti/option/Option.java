package com.meti.option;

import com.meti.func.C1;
import com.meti.func.F1;

public interface Option<T> {
    boolean isPresent();

    <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E;

    <E extends Exception> void ifPresent(C1<T, E> consumer) throws E;

    T orElse(T other);
}
