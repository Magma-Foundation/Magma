package com.meti.api.collect;

import com.meti.api.iterator.AbstractIterator;
import com.meti.api.iterator.Collector;
import com.meti.api.iterator.Iterator;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class JavaList<T> implements List<T> {
    private final java.util.List<T> inner;

    public JavaList(java.util.List<T> inner) {
        this.inner = inner;
    }

    public static <T> Collector<T, List<T>> collect() {
        return new Collector<>() {
            @Override
            public List<T> initial() {
                return new JavaList<>(new ArrayList<>());
            }

            @Override
            public List<T> fold(List<T> current, T element) {
                return current.add(element);
            }
        };
    }

    @SafeVarargs
    public static <T> List<T> of(T... elements) {
        return new JavaList<>(Arrays.asList(elements));
    }

    public static <T> List<T> empty() {
        return new JavaList<>(Collections.emptyList());
    }

    @Override
    public List<T> add(T element) {
        var copy = new ArrayList<>(this.inner);
        copy.add(element);
        return new JavaList<>(copy);
    }

    @Override
    public int size() {
        return this.inner.size();
    }

    @Override
    public Option<T> get(int index) {
        if (index < this.inner.size()) {
            return Some.apply(this.inner.get(index));
        } else {
            return None.apply();
        }
    }

    @Override
    public Option<T> head() {
        return inner.isEmpty() ? None.apply() : Some.apply(inner.get(0));
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public Iterator<T> iter() {
        return new AbstractIterator<T>() {
            private int counter = 0;

            @Override
            public Option<T> head() {
                if (counter < inner.size()) {
                    var temp = inner.get(counter);
                    counter++;
                    return Some.apply(temp);
                } else {
                    return None.apply();
                }
            }
        };
    }
}
