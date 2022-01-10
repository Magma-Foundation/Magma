package com.meti.option;

import com.meti.core.C1;
import com.meti.core.F1;

public class None<T> implements Option<T> {
    @Override
    public <R, E extends Exception> Option<R> flatMap(F1<T, Option<R>, E> mapper) {
        return new None<>();
    }

    @Override
    public <E extends Exception> void ifPresent(C1<T, E> consumer) {
    }

    @Override
    public boolean isEmpty() {
        return true;
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
    public T orElse(T other) {
        return other;
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
