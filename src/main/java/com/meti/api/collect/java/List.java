package com.meti.api.collect.java;

import com.meti.api.collect.stream.AbstractStream;
import com.meti.api.collect.stream.EndOfStreamException;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class List<T> {
    private final java.util.List<T> value;

    private List() {
        this(new ArrayList<>());
    }

    private List(java.util.List<T> value) {
        this.value = value;
    }

    @SafeVarargs
    public static <R> List<R> apply(R... values) {
        return createList(new ArrayList<>(java.util.List.of(values)));
    }

    public static <T> List<T> createList() {
        return new List<T>();
    }

    public static <T> List<T> createList(java.util.List<T> value) {
        return new List<T>(value);
    }

    public List<T> add(T node) {
        value.add(node);
        return this;
    }

    public List<T> addAll(List<T> others) {
        value.addAll(others.value);
        return this;
    }

    public T apply(int index) {
        return value.get(index);
    }

    public boolean contains(T element) {
        return value.contains(element);
    }

    public Option<T> first() {
        return value.isEmpty() ? new None<>() : new Some<>(value.get(0));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        List<?> list = (List<?>) o;
        return Objects.equals(value, list.value);
    }

    @Override
    public String toString() {
        return value.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public List<T> insert(int index, T value) {
        this.value.add(index, value);
        return createList(this.value);
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public Option<T> last() {
        if (value.isEmpty()) return new None<>();
        else return new Some<>(value.get(value.size() - 1));
    }

    public int size() {
        return value.size();
    }

    public Stream<T> stream() {
        return new JavaListStream();
    }

    private class JavaListStream extends AbstractStream<T> {
        private int counter = 0;

        @Override
        public T head() throws StreamException {
            if (counter < size()) {
                return value.get(counter++);
            } else {
                throw new EndOfStreamException("No more elements left in list.");
            }
        }
    }
}
