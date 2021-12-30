package com.meti.api.option;

import com.meti.api.func.C1;
import com.meti.api.func.F1;

public class None<T> implements Option<T> {
    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) {
        return new None<>();
    }

    @Override
    public <E extends Exception> void ifPresent(C1<T, E> consumer) {

    }

    @Override
    public T orElse(T other) {
        return other;
    }
}
