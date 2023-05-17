package com.meti;

import java.util.function.Consumer;

public class ValueOk<T, E> implements Result<T, E> {
    private final T value;

    public ValueOk(T value) {
        this.value = value;
    }

    @Override
    public void match(Consumer<T> onOk, Consumer<E> onErr) {
        onOk.accept(value);
    }

    @Override
    public Option<E> asErr() {
        return new None<E>();
    }

    @Override
    public Option<T> asOk() {
        return new Some<>(value);
    }
}
