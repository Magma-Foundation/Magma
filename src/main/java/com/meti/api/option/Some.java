package com.meti.api.option;

import com.meti.api.F1;
import com.meti.api.Supplier;

public record Some<T>(T value) implements Option<T> {
    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E> supplier) throws E {
        return value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
