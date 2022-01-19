package com.meti.api.collect.stream;

import com.meti.api.core.F1;
import com.meti.api.core.F2;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public interface Stream<T> {
    default <E extends Exception> boolean anyMatch(F1<T, Boolean, E> predicate) throws StreamException, E {
        return foldRight(false, (current, t) -> current || predicate.apply(t));
    }

    <R, E extends Exception> R foldRight(R identity, F2<R, T, R, E> folder) throws StreamException, E;

    default int count() throws StreamException {
        return foldRight(0, (current, value) -> current + 1);
    }

    Stream<T> filter(F1<T, Boolean, ?> predicate);

    Option<T> first() throws StreamException;

    <R> Stream<R> flatMap(F1<T, Stream<R>, ?> mapper) throws StreamException;

    <E extends Exception> Option<T> foldRight(F2<T, T, T, E> folder) throws StreamException, E;

    default Option<T> headOptionally() throws StreamException {
        try {
            return new Some<>(head());
        } catch (EndOfStreamException e) {
            return new None<>();
        }
    }

    T head() throws StreamException;

    <R> Stream<R> map(F1<T, R, ?> mapper) throws StreamException;
}
