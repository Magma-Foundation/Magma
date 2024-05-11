package com.meti.api.result;

import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record Ok<T, E>(T value) implements Result<T, E> {
    @Override
    public Option<T> findValue() {
        return new Some<>(value);
    }
}
