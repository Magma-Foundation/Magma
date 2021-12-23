package com.meti.option;

import com.meti.core.F1;
import com.meti.core.Supplier;

public record Some<T>(T value) implements Option<T> {
    @Override
    public <E extends Exception> Option<T> filter(F1<T, Boolean, E> filter) throws E {
        return filter.apply(value) ? this : new None<>();
    }

    @Override
    public <R, E extends Exception> Option<R> flatMap(F1<T, Option<R>, E> mapper) throws E {
        return mapper.apply(value);
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
    public Option<T> or(Option<T> other) {
        return this;
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public <E extends Exception> T orElseGet(Supplier<T, E> teSupplier) {
        return value;
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E, E> supplier) {
        return value;
    }
}
