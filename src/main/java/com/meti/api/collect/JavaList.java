package com.meti.api.collect;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JavaList<T> {
    private final List<T> value;


    public JavaList() {
        this(new ArrayList<>());
    }

    public JavaList(List<T> value) {
        this.value = value;
    }

    @SafeVarargs
    public static <R> JavaList<R> apply(R... values) {
        return new JavaList<>(new ArrayList<>(List.of(values)));
    }

    public JavaList<T> add(T node) {
        value.add(node);
        return this;
    }

    public JavaList<T> addAll(JavaList<T> others) {
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
        JavaList<?> javaList = (JavaList<?>) o;
        return Objects.equals(value, javaList.value);
    }

    @Override
    public String toString() {
        return value.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public JavaList<T> insert(int index, T value) {
        this.value.add(index, value);
        return new JavaList<>(this.value);
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
