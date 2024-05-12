package com.meti.result;

import java.util.function.Function;

public record Err<T, E extends Throwable>(E value) implements Result<T, E> {
    @Override
    public T $() throws E {
        throw value;
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Err<>(value);
    }

    @Override
    public <R> R match(Function<T, R> valueMapper, Function<E, R> errMapper) {
        return errMapper.apply(value);
    }

    @Override
    public <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper) {
        return new Err<>(mapper.apply(value));
    }
}
