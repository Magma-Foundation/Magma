package com.meti.option;

import com.meti.api.core.C1;
import com.meti.api.core.F1;

public interface Option<T> {
    <E extends Exception> Option<T> filter(F1<T, Boolean, E> predicate) throws E;

    <R, E extends Exception> Option<R> flatMap(F1<T, Option<R>, E> mapper) throws E;

    <E extends Exception> void ifPresent(C1<T, E> consumer) throws E;

    boolean isEmpty();

    boolean isPresent();

    <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E;

    Option<T> or(Option<T> option);

    <E extends Exception> Option<T> or(Supplier<Option<T>, E> supplier) throws E;

    T orElse(T other);

    <E extends Exception> T orElseGet(Supplier<T, E> supplier) throws E;

    <E extends Exception> T orElseThrow(Supplier<E, E> supplier) throws E;
}
