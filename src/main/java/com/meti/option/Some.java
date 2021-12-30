package com.meti.option;

import com.meti.func.C1;
import com.meti.func.F1;

public record Some<T>(T value) implements Option<T> {
    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public <E extends Exception> void ifPresent(C1<T, E> consumer) throws E {
        consumer.accept(value);
    }

    @Override
    public T orElse(T other) {
        return value;
    }
}
