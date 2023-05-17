package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class Some implements Option<IOException> {
    private final IOException value;

    public Some(IOException value) {
        this.value = value;
    }

    @Override
    public void ifPresent(Consumer<IOException> consumer) {
        consumer.accept(value);
    }
}
