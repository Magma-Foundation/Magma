package com.meti.java;

import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;

import java.util.List;

public class NonEmptyJavaList<T> extends JavaList<T> {
    public NonEmptyJavaList(List<T> values) {
        super(values);
    }

    public static <T> Option<NonEmptyJavaList<T>> from(JavaList<T> list) {
        return list.isEmpty()
                ? new None<>()
                : new Some<>(new NonEmptyJavaList<>(list.unwrap()));
    }

    public Index lastIndex() {
        return new Index(this.values.size() - 1);
    }

    public T first() {
        return values.get(0);
    }
}
