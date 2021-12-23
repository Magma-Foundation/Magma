package com.meti.option;

import com.meti.core.F1;
import com.meti.core.Supplier;

public class None<T> implements Option<T> {
    @Override
    public <E extends Exception> Option<T> filter(F1<T, Boolean, E> filter) {
        return new None<>();
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) {
        return new None<>();
    }

    @Override
    public Option<T> or(Option<T> other) {
        return other;
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public <E extends Exception> T orElseGet(Supplier<T, E> teSupplier) throws E {
        return teSupplier.apply();
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E, E> supplier) throws E {
        throw supplier.apply();
    }
}
