package com.meti;

record Some<T>(T value) implements Option<T> {
    @Override
    public T unwrapOrPanic() {
        return value;
    }
}
