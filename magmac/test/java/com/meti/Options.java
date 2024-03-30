package com.meti;

import com.meti.java.IOException;
import com.meti.java.Option;

public class Options {
    public static <T extends Throwable> void panic(Option<T> option) {
        option.ifPresent(value -> {
            throw new RuntimeException(value);
        });
    }
}
