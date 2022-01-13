package com.meti.collect;

import java.util.ArrayList;
import java.util.List;

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

    public JavaList<T> insert(int index, T value) {
        getValue().add(index, value);
        return new JavaList<>(getValue());
    }

    public List<T> getValue() {
        return value;
    }

    public int size() {
        return getValue().size();
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
