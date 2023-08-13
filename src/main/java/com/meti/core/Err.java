package com.meti.core;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents an error type.
 */
public record Err<T, E extends Throwable>(E inner) implements Result<T, E> {
    public static <T, E extends Throwable> Result<T, E> apply(E inner) {
        return new Err<>(inner);
    }

    @Override
    public Option<T> value() {
        return None.apply();
    }

    @Override
    public Option<E> err() {
        return Some.apply(inner);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return apply(inner);
    }

    @Override
    public <R> Result<R, E> mapValueToResult(Function<T, Result<R, E>> mapper) {
        return apply(inner);
    }

    @Override
    public void consume(Consumer<T> valueConsumer, Consumer<E> errorConsumer) {
        try {
            errorConsumer.accept(inner);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <R> R into(Function<Result<T, E>, R> mapper) {
        return mapper.apply(this);
    }

    @Override
    public <R> R match(Function<T, R> onOk, Function<E, R> onErr) {
        return onErr.apply(inner);
    }

    @Override
    public <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper) {
        return new Err<>(mapper.apply(inner));
    }

    @Override
    public Result<T, E> peekValue(Consumer<T> consumer) {
        return this;
    }

    @Override
    public T $() throws E {
        throw inner;
    }
}
