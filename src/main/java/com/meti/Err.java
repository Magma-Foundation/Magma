package com.meti;

import java.util.function.Consumer;
import java.util.function.Function;

public record Err<T, E extends Throwable>(E e) implements Result<T, E> {
    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Err<>(e);
    }

    @Override
    public <R> Result<R, E> mapValueToResult(Function<T, Result<R, E>> mapper) {
        return new Err<>(e);
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
}
