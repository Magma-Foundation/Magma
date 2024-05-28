package com.meti.api.result;

import java.util.Optional;
import java.util.function.Function;

public interface Result<T, E extends Throwable> {
    T $() throws E;

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    default <R> R into(Function<Result<T, E>, R> mapper) {
        return mapper.apply(this);
    }

    <R> R match(Function<T, R> valueMapper, Function<E, R> errMapper);

    <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper);

    Optional<T> findValue();

    <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper);

    <R> Result<Tuple<T, R>, E> and(Result<R, E> other);
}
