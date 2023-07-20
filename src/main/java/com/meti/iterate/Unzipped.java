package com.meti.iterate;

import java.util.function.BiFunction;

public interface Unzipped<T> {
    Iterator<T> fold(BiFunction<Iterator<T>, Iterator<T>, Iterator<T>> folder);
}