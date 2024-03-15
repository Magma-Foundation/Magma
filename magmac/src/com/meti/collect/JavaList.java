package com.meti.collect;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;

import java.util.ArrayList;
import java.util.List;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class JavaList<T> {
    private final List<T> list;

    public JavaList() {
        this(new ArrayList<>());
    }

    public JavaList(List<T> list) {
        this.list = list;
    }

    @SafeVarargs
    public static <T> JavaList<T> from(T... elements) {
        return new JavaList<>(new ArrayList<>(List.of(elements)));
    }

    public JavaList<T> add(T element) {
        var copy = new ArrayList<>(list);
        copy.add(element);
        return new JavaList<>(copy);
    }

    public Option<T> last() {
        return list.isEmpty() ? None() : Some(list.get(list.size() - 1));
    }

    public Option<Tuple<T, JavaList<T>>> popLast() {
        if (list.isEmpty()) return None();

        var slice = list.subList(0, list.size() - 1);
        var lastItem = list.get(list.size() - 1);
        return Some(new Tuple<>(lastItem, new JavaList<>(slice)));
    }

    public boolean contains(T value) {
        return this.list.contains(value);
    }

    public Stream<T> stream() {
        return Streams.fromList(list);
    }
}
