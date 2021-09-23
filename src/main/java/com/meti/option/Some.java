package com.meti.option;

import com.meti.core.F1;
import com.meti.core.Supplier;

public class Some<T> implements Option<T> {
    private final T value;

    public Some(T value) {
        this.value = value;
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
    public boolean isPresent() {
        return true;
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E> supplier) {
        return value;
    }
}
