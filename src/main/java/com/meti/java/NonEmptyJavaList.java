package com.meti.java;

import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NonEmptyJavaList<T> extends JavaList<T> implements NonEmptyList<T> {
    public NonEmptyJavaList(List<T> values) {
        super(values);
    }

    public static <T> Option<NonEmptyList<T>> from(com.meti.java.List<T> list) {
        return list.isEmpty()
                ? new None<>()
                : new Some<>(new NonEmptyJavaList<>(list.unwrap()));
    }

    @SafeVarargs
    public static <T> NonEmptyJavaList<T> ofNonEmpty(T first, T... others) {
        var list = new ArrayList<T>();
        list.add(first);
        list.addAll(Arrays.asList(others));
        return new NonEmptyJavaList<T>(list);
    }

    @Override
    public Index lastIndex() {
        return new Index(this.values.size() - 1, this.values.size());
    }

    @Override
    public T first() {
        return values.get(0);
    }

    @Override
    public com.meti.java.List<T> sliceWithoutFirst() {
        return new JavaList<>(values.subList(1, values.size()));
    }
}
