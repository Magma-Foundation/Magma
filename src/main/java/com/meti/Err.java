package com.meti;

import java.io.IOException;
import java.util.function.Consumer;

public class Err<T> implements Result<T> {
    private final IOException e;

    private Err(IOException e) {
        this.e = e;
    }

    public static Err<Runnable> forEmpty(IOException e) {
        return new Err<>(e);
    }

    public static <T> Err<Consumer<T>> forValued(IOException e) {
        return new Err<>(e);
    }

    @Override
    public void match(T acceptValue, Consumer<IOException> acceptError) {
        acceptError.accept(e);
    }
}
