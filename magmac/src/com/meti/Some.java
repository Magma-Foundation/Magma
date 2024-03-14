package com.meti;

public class Some<T> implements Option<T> {
    private final T value;

    public Some(T value) {
        this.value = value;
    }

    public static <T> Option<T> Some(T value) {
        return new Some<T>(value);
    }

    @Override
    public T orElseNull() {
        return value;
    }
}
