package com.meti.api.result;

import com.meti.api.option.None;
import com.meti.api.option.Option;

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
    public void consume(Consumer<T> okConsumer, Consumer<E> errConsumer) {
        okConsumer.accept(value);
    }

    @Override
    public <R> R match(Function<T, R> okMapper, Function<E, R> errMapper) {
        return okMapper.apply(value);
    }

    @Override
    public Option<E> err() {
        return None.apply();
    }

    @Override
    public boolean isOk() {
        return true;
    }
}
