package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class EmptyOk implements Result<Void> {
    @Override
    public void match(Consumer<Void> onOk, Consumer<IOException> onErr) {
        onOk.accept(null);
    }

    @Override
    public Option<IOException> asErr() {
        return new None();
    }
}
