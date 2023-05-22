package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class Err<C> implements Result<C> {
    private final IOException e;

    public Err(IOException e) {
        this.e = e;
    }

    @Override
    public void match(C onOk, Consumer<IOException> onError) {
        onError.accept(e);
    }
}
