package com.meti.api.result;

import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.function.Consumer;
import java.util.function.Function;

public record Err<T, E extends Throwable>(E value) implements Result<T, E> {

    public static <T, E extends Throwable> Result<T, E> apply(E value) {
        return new Err<>(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return Err.apply(value);
    }

    @Override
    public T $() throws E {
        throw value;
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return Err.apply(value);
    }

    @Override
    public void consume(Consumer<T> okConsumer, Consumer<E> errConsumer) {
        errConsumer.accept(value);
    }

    @Override
    public <R> R match(Function<T, R> okMapper, Function<E, R> errMapper) {
        return errMapper.apply(value);
    }

    @Override
    public Option<E> err() {
        return Some.apply(value);
    }
}
