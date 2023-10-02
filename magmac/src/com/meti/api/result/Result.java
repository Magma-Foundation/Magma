package com.meti.api.result;

import com.meti.api.option.Option;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Result<T, E extends Throwable> {
    <R> Result<R, E> mapValue(Function<T, R> mapper);

    T $() throws E;

    <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper);

    void consume(Consumer<T> okConsumer, Consumer<E> errConsumer);

    <R> R match(Function<T, R> okMapper, Function<E, R> errMapper);

    Option<E> err();
}
