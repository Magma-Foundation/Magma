package com.meti.api.option;

import com.meti.api.F1;
import com.meti.api.Supplier;

public class None<T> implements Option<T> {
    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) {
        return new None<>();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E> supplier) throws E {
        throw supplier.get();
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
