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

    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public T orElse(T other) {
        return value;
    }
}
