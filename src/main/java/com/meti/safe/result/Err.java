package com.meti.safe.result;

import java.util.function.Consumer;
import java.util.function.Function;

public record Err<T, E extends Throwable>(E e) implements Result<T, E> {
    public static <T, E extends Throwable> Err<T, E> apply(E e) {
        return new Err<T, E>(e);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return apply(e);
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return apply(e);
    }

    @Override
    public T unwrap() throws E {
        throw e;
    }

    @Override
    public boolean isErr() {
        return true;
    }

    @Override
    public void match(Consumer<T> onOk, Consumer<E> onErr) {
        onErr.accept(e);
    }

    @Override
    public <R> R matchWithValue(Function<T, R> onOk, Function<E, R> onErr) {
        return onErr.apply(e);
    }
}
