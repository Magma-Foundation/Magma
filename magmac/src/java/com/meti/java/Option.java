package com.meti.java;

import java.util.function.Consumer;

public interface Option<T> {
    default void consume(Consumer<Option<T>> consumer) {
        consumer.accept(this);
    }

    void ifPresent(Consumer<T> consumer);
}
