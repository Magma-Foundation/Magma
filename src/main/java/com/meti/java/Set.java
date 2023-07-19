package com.meti.java;

import com.meti.collect.Index;
import com.meti.iterate.Iterator;

public interface Set<T> {
    Set<T> add(T element);

    Iterator<T> iter();

    Index size();

    boolean isEmpty();

    java.util.Set<T> unwrap();
}
