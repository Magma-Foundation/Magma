package com.meti.api.collect.java;

import com.meti.api.collect.stream.Stream;
import com.meti.api.core.F1;
import com.meti.api.option.Option;
import com.meti.api.option.Supplier;

import java.util.ArrayList;

public interface List<T> {
    @SafeVarargs
    static <R> List<R> apply(R... values) {
        return createList(new ArrayList<>(java.util.List.of(values)));
    }

    static <T> List<T> createList(java.util.List<T> value) {
        return new JavaList<>(value);
    }

    static <T> List<T> createList() {
        return new JavaList<>();
    }

    List<T> add(T node);

    List<T> addAll(List<T> others);

    T apply(int index);

    boolean contains(T element);

    int count(T element);

    <E extends Exception> List<T> ensure(Supplier<T, E> supplier) throws E;

    Option<T> first();

    List<T> insert(int index, T value);

    boolean isEmpty();

    Option<T> last();

    <E extends Exception> List<T> mapLast(F1<T, T, E> mapper) throws E;

    List<T> pop();

    int size();

    Stream<T> stream();
}
