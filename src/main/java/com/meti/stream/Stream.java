package com.meti.stream;

import com.meti.C1E1;
import com.meti.F1E1;
import com.meti.F2E1;

public interface Stream<T> {
    <E extends Exception> Stream<T> filter(F1E1<T, Boolean, E> filter);

    <R, E extends Exception> Stream<R> map(F1E1<T, R, E> mapper);

    <E extends Exception> void forEach(C1E1<T, E> consumer) throws E, StreamException;

    <R, E extends Exception> R foldRight(R initial, F2E1<R, T, R, E> folder) throws E, StreamException;
}
