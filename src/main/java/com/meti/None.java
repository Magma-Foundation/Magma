package com.meti;

class None<T> implements Option<T> {
    @Override
    public T unwrapOrPanic() {
        throw new RuntimeException("No value present.");
    }
}
