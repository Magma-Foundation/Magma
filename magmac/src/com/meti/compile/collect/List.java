package com.meti.compile.collect;

import com.meti.compile.iterator.Iterator;
import com.meti.compile.option.Option;

import java.util.Map;

public interface List<T> {
    Option<T> head();

    boolean isEmpty();

    Iterator<T> iter();

    List<T> add(T element);

    int size();

    Option<T> get(int index);
}
