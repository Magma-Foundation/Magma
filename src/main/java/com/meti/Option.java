package com.meti;

public interface Option<T> {
    boolean isPresent();

    <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E;

    <E extends Exception> void ifPresent(C1<T, E> consumer) throws E;

    T orElse(T other);
}
