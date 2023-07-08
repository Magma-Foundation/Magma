package com.meti;

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
        return new Some<>(inner);
    }
}
