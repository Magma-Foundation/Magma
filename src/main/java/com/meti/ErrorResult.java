package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class ErrorResult<T> implements Result<T> {
    private final IOException e;

    public ErrorResult(IOException e) {
        this.e = e;
    }

    @Override
    public void match(Consumer<T> onOk, Consumer<IOException> onErr) {
        onErr.accept(e);
    }

    @Override
    public Option<IOException> asErr() {
        return new Some(e);
    }
}
