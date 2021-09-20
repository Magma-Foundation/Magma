package com.meti;

public interface Stream<T> {
    <R, E extends Exception> Stream<R> map(F1E1<T, R, E> mapper);

    <E extends Exception> void forEach(C1E1<T, E> consumer) throws E, StreamException;

    <R, E extends Exception> R foldRight(R initial, F2E1<R, T, R, E> folder) throws E, StreamException;
}
