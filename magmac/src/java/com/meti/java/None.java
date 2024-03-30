package com.meti.java;

import java.util.function.Consumer;

public class None<T> implements Option<T> {
    @Override
    public void ifPresent(Consumer<T> consumer) {
    }
}
