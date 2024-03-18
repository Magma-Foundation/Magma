package com.meti.collect;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;

import java.util.ArrayList;
import java.util.List;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class JavaList<T> {
    private final List<T> elements;

    public JavaList() {
        this(new ArrayList<>());
    }

    public JavaList(List<T> elements) {
        this.elements = elements;
    }

    @SafeVarargs
    public static <T> JavaList<T> from(T... elements) {
        return new JavaList<>(new ArrayList<>(List.of(elements)));
    }

    public static <T> JavaList<T> empty() {
        return new JavaList<>();
    }

    @Override
    public String toString() {
        return "JavaList{" +
               "list=" + elements +
               '}';
    }

    public JavaList<T> add(T element) {
        var copy = new ArrayList<>(elements);
        copy.add(element);
        return new JavaList<>(copy);
    }

    public Option<T> last() {
        return elements.isEmpty() ? None() : Some(elements.get(elements.size() - 1));
    }

    public Option<Tuple<T, JavaList<T>>> popLast() {
        if (elements.isEmpty()) return None();

        var slice = elements.subList(0, elements.size() - 1);
        var lastItem = elements.get(elements.size() - 1);
        return Some(new Tuple<>(lastItem, new JavaList<>(slice)));
    }

    public boolean contains(T value) {
        return this.elements.contains(value);
    }

    public Stream<T> stream() {
        return Streams.fromNativeList(elements);
    }

    public List<T> unwrap() {
        return elements;
    }

    public JavaList<T> addAll(JavaList<T> other) {
        var copy = new ArrayList<>(elements);
        copy.addAll(other.elements);
        return new JavaList<>(copy);
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
