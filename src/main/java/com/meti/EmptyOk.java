package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class EmptyOk implements Result<Runnable> {

    @Override
    public void match(Runnable onOk, Consumer<IOException> onError) {
        onOk.run();
    }
}
