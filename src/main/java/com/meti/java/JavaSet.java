package com.meti.java;

import com.meti.collect.Collector;
import com.meti.collect.Index;
import com.meti.iterate.IndexIterator;
import com.meti.iterate.Iterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public record JavaSet<T>(Set<T> value) {
    public static <T> Collector<T, JavaSet<T>> asSet() {
        return new Collector<>() {
            @Override
            public JavaSet<T> initial() {
                return JavaSet.empty();
            }

            @Override
            public JavaSet<T> foldLeft(JavaSet<T> accumulated, T element) {
                return accumulated.add(element);
            }
        };
    }

    public static <T> JavaSet<T> empty() {
        return new JavaSet<>(new HashSet<>());
    }

    @SafeVarargs
    public static <T> JavaSet<T> of(T... elements) {
        return new JavaSet<>(new HashSet<>(Set.of(elements)));
    }

    private JavaSet<T> add(T element) {
        this.value.add(element);
        return this;
    }

    public Iterator<T> iter() {
        var list = new ArrayList<>(value);
        return new IndexIterator<>() {
            @Override
            protected T apply(Index index) {
                return list.get(index.value());
            }

            @Override
            protected Index length() {
                return new Index(list.size(), list.size());
            }
        };
    }

    public Index size() {
        return new Index(this.value.size(), this.value.size());
    }
}
