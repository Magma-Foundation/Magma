package com.meti.api.option;

import com.meti.api.core.F1;
import com.meti.api.core.Supplier;

public class None<T> implements Option<T> {
    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E {
        return new None<>();
    }

    @Override
    public Option<T> or(Option<T> other) {
        return other;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <E extends Exception> T orElseGet(Supplier<T, E> supplier) throws E {
        return supplier.get();
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E, E> supplier) throws E {
        throw supplier.get();
    }
}
