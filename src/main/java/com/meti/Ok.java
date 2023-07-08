package com.meti;

record Ok<T, E>(T inner) implements Result<T, E> {
    @Override
    public Option<T> value() {
        return new Some<>(inner);
    }

    @Override
    public Option<E> err() {
        return new None<>();
    }
}
