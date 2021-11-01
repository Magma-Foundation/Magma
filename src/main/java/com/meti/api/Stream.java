package com.meti.api;

import com.meti.api.option.Option;

public interface Stream<T> {
    <R> Stream<R> map(F1<T, R, ?> mapper);

    Option<T> first() throws StreamException;

    <R> Stream<R> flatMap(F1<T, Stream<R>, ?> mapper);

    Stream<T> filter(F1<T, Boolean, ?> predicate);

    <R, E extends Exception> R foldRight(R identity, F2<R, T, R, E> folder) throws E, StreamException;

    <E extends Exception> Option<T> foldRight(F2<T, T, T, E> folder) throws E, StreamException;

    T head() throws StreamException;
}
