package com.meti.api.collect;

public interface List<T> {
    Iterator<T> iter();

    List<T> addLast(T element);
}

