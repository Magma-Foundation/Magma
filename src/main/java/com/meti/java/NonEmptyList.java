package com.meti.java;

import com.meti.collect.Index;

public interface NonEmptyList<T> extends List<T> {
    Index lastIndex();

    T first();
}
