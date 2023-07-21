package com.meti.java;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.Index;

import java.util.List;

public class NonEmptyJavaList<T> extends JavaList<T> implements NonEmptyList<T> {
    public NonEmptyJavaList(List<T> values) {
        super(values);
    }

    public static <T> Option<NonEmptyList<T>> from(com.meti.java.List<T> list) {
        return list.isEmpty()
                ? None.apply()
                : new Some<>(new NonEmptyJavaList<>(list.unwrap()));
    }

    @Override
    public Index lastIndex() {
        return new Index(this.values.size() - 1, this.values.size());
    }

    @Override
    public T last() {
        return values.get(values.size() - 1);
    }

    @Override
    public T first() {
        return values.get(0);
    }
}
