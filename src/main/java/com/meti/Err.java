package com.meti;

import java.util.function.Function;

record Err<T, E>(E inner) implements Result<T, E> {
    static <T, E> Result<T, E> from(E inner) {
        return new Err<>(inner);
    }

    @Override
    public Option<T> value() {
        return new None<>();
    }

    @Override
    public Option<E> err() {
        return Some.apply(inner);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Err<>(inner);
    }
}
