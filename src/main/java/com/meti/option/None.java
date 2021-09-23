package com.meti.option;

import com.meti.core.F1;
import com.meti.core.Supplier;

public class None<T> implements Option<T> {
    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) {
        return new None<>();
    }

    @Override
    public Option<T> or(Option<T> other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E> supplier) {
        throw new UnsupportedOperationException();
    }
}
