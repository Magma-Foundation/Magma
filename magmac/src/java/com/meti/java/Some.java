package com.meti.java;

import java.util.function.Consumer;

public class Some<T> implements Option<T> {
    private final T value;

    public Some(T value) {
        this.value = value;
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        consumer.accept(value);
    }
}
