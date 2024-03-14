package com.meti;

public class None<T> implements Option<T> {
    public static <T> Option<T> None() {
        return new None<>();
    }

    @Override
    public T orElseNull() {
        return null;
    }
}
