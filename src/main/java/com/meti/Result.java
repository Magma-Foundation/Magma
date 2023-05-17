package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public interface Result<T> {
    void match(Consumer<T> onOk, Consumer<IOException> onErr);

    Option<IOException> asErr();
}
