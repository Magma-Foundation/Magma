package com.meti.collect;

import com.meti.core.F1;
import com.meti.core.F2;

public interface Stream<T> {
    <R, E extends Exception> R foldRight(R identity, F2<R, T, R, E> folder) throws StreamException, E;

    T head() throws StreamException;

    <R> Stream<R> map(F1<T, R, ?> mapper) throws StreamException;
}
