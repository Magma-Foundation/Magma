package com.meti.core;

public class Options {
    public static <T extends Throwable> void panic(Option<T> option) {
        option.ifPresent(value -> {
            throw new RuntimeException(value);
        });
    }
}
