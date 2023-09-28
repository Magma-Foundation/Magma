package com.meti.api.result;

import java.util.function.Consumer;
import java.util.function.Function;

public record Ok<T, E extends Throwable>(T value) implements Result<T, E> {
    public static <T, E extends Throwable> Result<T, E> apply(T value) {
        return new Ok<>(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return Ok.apply(mapper.apply(value));
    }

    @Override
    public T $() throws E {
        return value;
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public void match(Consumer<T> okConsumer, Consumer<E> errConsumer) {
        okConsumer.accept(value);
    }
}
