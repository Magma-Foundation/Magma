package com.meti.api.option;

import com.meti.api.core.Supplier;

public class None<T> implements Option<T> {
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
}
