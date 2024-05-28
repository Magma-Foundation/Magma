package com.meti.api.result;

import java.util.Optional;
import java.util.function.Function;

public record Ok<T, E extends Throwable>(T value) implements Result<T, E> {
    @Override
    public T $() {
        return value;
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Ok<>(mapper.apply(value));
    }

    @Override
    public <R> R match(Function<T, R> valueMapper, Function<E, R> errMapper) {
        return valueMapper.apply(value);
    }

    @Override
    public <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper) {
        return new Ok<>(value);
    }

    @Override
    public Optional<T> findValue() {
        return Optional.of(value);
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public <R> Result<Tuple<T, R>, E> and(Result<R, E> other) {
        return other.mapValue(otherValue -> new Tuple<>(this.value, otherValue));
    }
}
