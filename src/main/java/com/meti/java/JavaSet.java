package com.meti.java;

import com.meti.collect.Collector;
import com.meti.collect.Index;
import com.meti.iterate.IndexIterator;
import com.meti.iterate.Iterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public record JavaSet<T>(Set<T> value) implements com.meti.java.Set<T> {
    public static <T> Collector<T, com.meti.java.Set<T>> asSet() {
        return new Collector<>() {
            @Override
            public com.meti.java.Set<T> initial() {
                return JavaSet.empty();
            }

            @Override
            public com.meti.java.Set<T> foldLeft(com.meti.java.Set<T> accumulated, T element) {
                return accumulated.add(element);
            }
        };
    }

    public static <T> com.meti.java.Set<T> empty() {
        return new JavaSet<>(new HashSet<>());
    }

    @SafeVarargs
    public static <T> com.meti.java.Set<T> of(T... elements) {
        return new JavaSet<>(new HashSet<>(Set.of(elements)));
    }

    @Override
    public com.meti.java.Set<T> add(T element) {
        this.value.add(element);
        return this;
    }

    @Override
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

    @Override
    public Index size() {
        return new Index(this.value.size(), this.value.size());
    }

    @Override
    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    @Override
    public Set<T> unwrap() {
        return value;
    }
}
