package com.meti;

interface Result<T, E> {
    Option<T> value();

    Option<E> err();
}
