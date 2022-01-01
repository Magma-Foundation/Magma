package com.meti.app;

import com.meti.api.func.F1;
import com.meti.api.func.F2;

public interface Stream<T> {
    <R, E extends Exception> R foldRight(R identity, F2<R, T, R, E> folder) throws E;

    <R, E extends Exception> Stream<R> map(F1<T, R, E> compile) throws E;
}
