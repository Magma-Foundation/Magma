package com.meti.core;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents a non-error type.
 *
 * @param inner The type of this result.
 * @param <T>   The type value of this result.
 * @param <E>   The error value of this result.
 */
public record Ok<T, E extends Throwable>(T inner) implements Result<T, E> {
    public static <T, E extends Throwable> Result<T, E> apply(T inner) {
        return new Ok<>(inner);
    }

    @Override
    public Option<T> value() {
        return Some.apply(inner);
    }

    @Override
    public Option<E> err() {
        return None.apply();
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return apply(mapper.apply(inner));
    }

    @Override
    public <R> Result<R, E> mapValueToResult(Function<T, Result<R, E>> mapper) {
        return mapper.apply(inner);
    }

    @Override
    public void consume(Consumer<T> valueConsumer, Consumer<E> errorConsumer) {
        valueConsumer.accept(inner);
    }

    @Override
    public <R> R into(Function<Result<T, E>, R> mapper) {
        return mapper.apply(this);
    }

    @Override
    public <R> R match(Function<T, R> onOk, Function<E, R> onErr) {
        return onOk.apply(inner);
    }

    @Override
    public <R extends Throwable> Result<T, R> mapErr(Function<E, R> mapper) {
        return new Ok<>(inner);
    }

    @Override
    public T $() throws E {
        return this.inner;
    }

    @Override
    public Result<T, E> peekValue(Consumer<T> consumer) {
        consumer.accept(inner);
        return this;
    }
}
