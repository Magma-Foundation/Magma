package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class EmptyOk implements Result<Runnable> {
    @Override
    public void match(Runnable acceptValue, Consumer<IOException> acceptError) {
        acceptValue.run();
    }
}
