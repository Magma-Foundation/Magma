package com.meti;

public interface Stream<T> {
    <E extends Exception> void forEach(C1E1<T, E> consumer) throws E;
}
