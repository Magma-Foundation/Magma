package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class None implements Option<IOException> {
    @Override
    public void ifPresent(Consumer<IOException> consumer) {
    }
}
