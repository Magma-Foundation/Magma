package com.meti.core;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Result<T, E extends Throwable> {
    Option<T> value();

    Option<E> err();

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    <R> Result<R, E> mapValueToResult(Function<T, Result<R, E>> mapper);

    void consume(Consumer<T> valueConsumer, Consumer<E> errorConsumer);

    <R> R into(Function<Result<T, E>, R> mapper);

    <R> R match(Function<T, R> onOk, Function<E, R> onErr);

    <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper);

    /**
     * Peeks at this Result's type.
     */
    Result<T, E> peekValue(Consumer<T> consumer);

    T $() throws E;
}
