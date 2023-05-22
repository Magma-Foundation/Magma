package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class ValueOk implements Result<Consumer<String>> {
    public ValueOk(String input) {
    }

    @Override
    public void match(Consumer<String> onOk, Consumer<IOException> onError) {

    }
}
