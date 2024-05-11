package com.meti.api.result;

import com.meti.api.option.None;
import com.meti.api.option.Option;

public record Err<T, E>(E err) implements Result<T, E> {
    @Override
    public Option<T> findValue() {
        return new None<>();
    }
}
