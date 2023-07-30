package com.meti.java;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.Collector;
import com.meti.iterate.Index;
import com.meti.iterate.IndexIterator;
import com.meti.iterate.Iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class JavaList<T> implements com.meti.java.List<T> {
    protected final List<T> values;

    public JavaList(List<T> values) {
        this.values = new ArrayList<>(values);
    }

    public static <T> Collector<T, com.meti.java.List<T>> intoList() {
        return new Collector<>() {
            @Override
            public com.meti.java.List<T> initial() {
                return new JavaList<>(new ArrayList<>());
            }

            @Override
            public com.meti.java.List<T> foldLeft(com.meti.java.List<T> accumulated, T element) {
                return accumulated.add(element);
            }
        };
    }

    @SafeVarargs
    public static <T> com.meti.java.List<T> of(T... values) {
        return new JavaList<>(new ArrayList<>(Arrays.asList(values)));
    }

    public static <T> com.meti.java.List<T> empty() {
        return new JavaList<>(new ArrayList<>());
    }

    @Override
    public com.meti.java.List<T> sort(Comparator<T> comparator) {
        var copy = new ArrayList<>(values);
        copy.sort(comparator);
        return new JavaList<>(copy);
    }

    @Override
    public com.meti.java.List<T> addAll(JavaList<T> others) {
        values.addAll(others.values);
        return this;
    }

    @Override
    public Iterator<T> iter() {
        return new IndexIterator<>() {
            @Override
            protected T apply(Index index) {
                return values.get(index.value());
            }

            @Override
            protected Index length() {
                return new Index(values.size(), values.size());
            }
        };
    }

    @Override
    public Option<Index> lastIndexOptionally() {
        if (values.isEmpty()) {
            return None.apply();
        } else {
            return Some.apply(new Index(values.size() - 1, values.size()));
        }
    }

    @Override
    public com.meti.java.List<T> sliceTo(Index extent) {
        return new JavaList<>(values.subList(0, extent.value()));
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public <R> R into(Function<com.meti.java.List<T>, R> mapper) {
        return mapper.apply(this);
    }

    @Override
    public List<T> unwrap() {
        return this.values;
    }

    @Override
    public Index size() {
        return new Index(this.values.size(), this.values.size());
    }

    @Override
    public com.meti.java.List<T> add(T element) {
        this.values.add(element);
        return this;
    }
}
