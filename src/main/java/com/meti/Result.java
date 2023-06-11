package com.meti;

import java.util.function.Function;

interface Result<T, E extends Exception> {

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    T unwrap() throws E;
}
