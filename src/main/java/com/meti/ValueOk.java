package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class ValueOk implements Result<String> {
    private final String value;

    public ValueOk(String value) {
        this.value = value;
    }

    @Override
    public void match(Consumer<String> onOk, Consumer<IOException> onErr) {
        onOk.accept(value);
    }

    @Override
    public Option<IOException> asErr() {
        return new None();
    }
}
