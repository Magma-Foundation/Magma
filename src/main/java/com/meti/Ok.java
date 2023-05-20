package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class Ok implements Result<Consumer<String>> {
    private final String value;

    public Ok(String value) {
        this.value = value;
    }

    @Override
    public void match(Consumer<String> acceptValue, Consumer<IOException> acceptError) {
        acceptValue.accept(value);
    }
}
