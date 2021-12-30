package com.meti.app;

import com.meti.api.func.F1;

public interface Stream<T> {
    <R, E extends Exception> Stream<R> map(F1<T, R, E> compile) throws E;
}
