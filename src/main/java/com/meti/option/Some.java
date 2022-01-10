package com.meti.option;

import com.meti.core.C1;
import com.meti.core.F1;

public record Some<T>(T value) implements Option<T> {
    @Override
    public <E extends Exception> void ifPresent(C1<T, E> consumer) throws E {
        consumer.consume(value);
    }

    @Override
    public boolean isEmpty() {
        return false;
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
    public T orElse(T other) {
        return value;
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E, E> supplier) {
        return value;
    }
}
