package com.meti.option;

import com.meti.core.F1;

public record Some<T>(T value) implements Option<T> {
    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public Option<T> or(Option<T> other) {
        return this;
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public <E extends Exception> T orElseGet(Supplier<T, E> supplier) {
        return value;
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E, E> supplier) {
        return value;
    }
}
