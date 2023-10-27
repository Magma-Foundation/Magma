package com.meti.api.collect;

import com.meti.api.iterator.Iterator;
import com.meti.api.option.Option;

public interface List<T> {
    Option<T> head();

    boolean isEmpty();

    Iterator<T> iter();

    List<T> add(T element);

    int size();

    Option<T> get(int index);
}
