package com.meti;

public interface Option<T> {
    boolean isPresent();

    T orElse(T other);

    <E extends Exception> T orElseThrow(Supplier<E> supplier) throws E;
}
