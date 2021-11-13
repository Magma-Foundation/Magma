package com.meti;

public record Some<T>(T value) implements Option<T> {
    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E> supplier) {
        return value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }
}
