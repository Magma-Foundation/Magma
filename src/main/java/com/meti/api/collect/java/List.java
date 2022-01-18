package com.meti.api.collect.java;

import com.meti.api.collect.stream.Stream;
import com.meti.api.option.Option;

import java.util.ArrayList;

public interface List<T> {
    @SafeVarargs
    static <R> List<R> apply(R... values) {
        return createList(new ArrayList<>(java.util.List.of(values)));
    }

    static <T> List<T> createList() {
        return new JavaList<T>();
    }

    static <T> List<T> createList(java.util.List<T> value) {
        return new JavaList<>(value);
    }

    List<T> add(T node);

    List<T> addAll(List<T> others);

    T apply(int index);

    boolean contains(T element);

    Option<T> first();

    List<T> insert(int index, T value);

    boolean isEmpty();

    Option<T> last();

    int size();

    Stream<T> stream();
}
