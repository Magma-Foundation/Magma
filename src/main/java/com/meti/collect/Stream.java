package com.meti.collect;

import com.meti.core.F1;
import com.meti.core.F2;
import com.meti.option.Option;

public interface Stream<T> {
    Stream<T> filter(F1<T, Boolean, ?> predicate);

    Option<T> first() throws StreamException;

    <R> Stream<R> flatMap(F1<T, Stream<R>, ?> mapper) throws StreamException;

    <R, E extends Exception> R foldRight(R identity, F2<R, T, R, E> folder) throws StreamException, E;

    T head() throws StreamException;

    <R> Stream<R> map(F1<T, R, ?> mapper) throws StreamException;
}
