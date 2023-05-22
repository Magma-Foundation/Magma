package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class ValueOk implements Result<Consumer<String>> {
    private final String input;

    public ValueOk(String input) {
        this.input = input;
    }

    @Override
    public void match(Consumer<String> onOk, Consumer<IOException> onError) {
        onOk.accept(input);
    }
}
