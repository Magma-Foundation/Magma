package com.meti.java;

import com.meti.collect.Collector;
import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.IndexIterator;
import com.meti.iterate.Iterator;

import java.util.ArrayList;
import java.util.List;

public class JavaList<T> {
    private final List<T> values;

    public JavaList(List<T> values) {
        this.values = values;
    }

    public static <T> Collector<T, JavaList<T>> asList() {
        return new Collector<>() {
            @Override
            public JavaList<T> initial() {
                return new JavaList<>(new ArrayList<>());
            }

            @Override
            public JavaList<T> foldLeft(JavaList<T> accumulated, T element) {
                return accumulated.add(element);
            }
        };
    }

    private JavaList<T> add(T element) {
        this.values.add(element);
        return this;
    }

    public Iterator<T> iter() {
        return new IndexIterator<>() {
            @Override
            protected T apply(Index index) {
                return values.get(index.value());
            }

            @Override
            protected Index length() {
                return new Index(values.size());
            }
        };
    }

    public Option<Index> lastIndex() {
        if (values.isEmpty()) {
            return new None<>();
        } else {
            return Some.apply(new Index(values.size() - 1));
        }
    }

    public JavaList<T> sliceTo(Index extent) {
        return new JavaList<>(values.subList(0, extent.value()));
    }
}
