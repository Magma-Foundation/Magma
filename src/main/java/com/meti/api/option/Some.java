package com.meti.api.option;

import com.meti.api.core.F1;
import com.meti.api.core.Supplier;

public record Some<T>(T value) implements Option<T> {
    @Override
    public T orElse(T other) {
        return value;
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
    public boolean isPresent() {
        return true;
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
