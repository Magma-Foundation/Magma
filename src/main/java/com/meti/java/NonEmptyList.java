package com.meti.java;

import com.meti.iterate.Index;

public interface NonEmptyList<T> extends List<T> {
    Index lastIndex();

    T first();

    List<T> sliceWithoutFirst();
}
