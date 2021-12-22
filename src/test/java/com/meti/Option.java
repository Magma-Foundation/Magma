package com.meti;

public interface Option<T> {
    <E extends Exception> Option<T> filter(F1<T, Boolean, E> filter) throws E;

    boolean isPresent();
}
