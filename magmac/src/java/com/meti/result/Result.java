package com.meti.result;

import java.util.function.Function;

public interface Result<T, E extends Throwable> {
    T $() throws E;

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    default <R> R into(Function<Result<T, E>, R> mapper) {
        return mapper.apply(this);
    }

    <R> R match(Function<T, R> valueMapper, Function<E, R> errMapper);

    <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper);
}
