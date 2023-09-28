package com.meti.api.collect;

import com.meti.api.option.Option;

public interface List<T> {
    Iterator<T> iter();

    List<T> addLast(T element);
}

