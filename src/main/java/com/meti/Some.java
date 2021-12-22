package com.meti;

public record Some<T>(T value) implements Option<T> {
    @Override
    public <E extends Exception> Option<T> filter(F1<T, Boolean, E> filter) throws E {
        return filter.apply(value) ? this : new None<>();
    }

    @Override
    public boolean isPresent() {
        return true;
    }
}
