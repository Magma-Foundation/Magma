package com.meti.safe.result;

import java.util.function.Consumer;
import java.util.function.Function;

public class Ok<T, E extends Throwable> implements Result<T, E> {
    private final T value;

    private Ok(T value) {
        this.value = value;
    }

    public static <T, E extends Throwable> Result<T, E> apply(T value) {
        return new Ok<T, E>(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return apply(mapper.apply(value));
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public T unwrap() throws E {
        return value;
    }

    @Override
    public boolean isErr() {
        return false;
    }

    @Override
    public void match(Consumer<T> onOk, Consumer<E> onErr) {
        onOk.accept(value);
    }

    @Override
    public <R> R matchWithValue(Function<T, R> onOk, Function<E, R> onErr) {
        return onOk.apply(value);
    }
}
