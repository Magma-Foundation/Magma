package com.meti;

import java.util.function.Consumer;

public class ErrorResult<T, E> implements Result<T, E> {
    private final E e;

    public ErrorResult(E e) {
        this.e = e;
    }

    @Override
    public void match(Consumer<T> onOk, Consumer<E> onErr) {
        onErr.accept(e);
    }

    @Override
    public Option<E> asErr() {
        return new Some<>(e);
    }

    @Override
    public Option<T> asOk() {
        return new None<>();
    }
}
