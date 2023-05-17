package com.meti;

import java.util.function.Consumer;

public interface Result<T, E> {
    void match(Consumer<T> onOk, Consumer<E> onErr);

    Option<E> asErr();

    Option<T> asOk();
}
