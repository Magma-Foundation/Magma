package magma.java;

import magma.api.Tuple;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.stream.AbstractStream;
import magma.api.stream.Collector;
import magma.api.stream.Stream;
import magma.api.stream.Streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public record JavaList<T>(List<T> list) {
    public JavaList() {
        this(Collections.emptyList());
    }

    public static <T, R> JavaList<R> fromNative(List<T> frames, Function<T, R> mapper) {
        return Streams.fromNativeList(frames)
                .map(mapper)
                .collect(toList());
    }

    public static <T> Collector<T, JavaList<T>> toList() {
        return new Collector<>() {
            @Override
            public JavaList<T> createInitial() {
                return new JavaList<>();
            }

            @Override
            public JavaList<T> fold(JavaList<T> current, T next) {
                return current.add(next);
            }
        };
    }

    public JavaList<T> add(T next) {
        var copy = new ArrayList<>(list);
        copy.add(next);
        return new JavaList<>(copy);
    }

    public Option<Tuple<T, JavaList<T>>> pop() {
        if (list.isEmpty()) return new None<>();
        var last = list.get(list.size() - 1);
        var slice = new JavaList<>(list.subList(0, list.size() - 1));
        return new Some<>(new Tuple<>(last, slice));
    }

    public JavaList<T> push(T element) {
        var copy = new ArrayList<>(list);
        copy.add(element);
        return new JavaList<>(copy);
    }

    public Stream<T> stream() {
        return new AbstractStream<>(new NativeListStream<>(list));
    }

    public boolean contains(T element) {
        return this.list.contains(element);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Option<T> last() {
        if (list.isEmpty()) {
            return new None<>();
        } else {
            return new Some<>(list.get(list.size() - 1));
        }
    }

    public int size() {
        return list.size();
    }

    public Option<JavaList<T>> mapLast(Function<T, T> mapper) {
        if (list.isEmpty()) {
            return new None<>();
        }

        var copy = new ArrayList<>(list);
        var lastIndex = copy.size() - 1;
        var last = copy.get(lastIndex);
        var newLast = mapper.apply(last);
        copy.set(lastIndex, newLast);
        return new Some<>(new JavaList<>(copy));
    }
}
