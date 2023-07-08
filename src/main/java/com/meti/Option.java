package com.meti;

interface Option<T> {
    T unwrapOrPanic();
}
