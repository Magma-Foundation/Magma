package magma.java;

import magma.api.Tuple;
import magma.api.collect.stream.AbstractStream;
import magma.api.collect.stream.Collector;
import magma.api.collect.stream.Stream;
import magma.api.collect.stream.Streams;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public record JavaList<T>(List<T> list) implements magma.api.collect.List<T> {
    public JavaList() {
        this(Collections.emptyList());
    }

    public static <T, R> magma.api.collect.List<R> fromNative(List<T> frames, Function<T, R> mapper) {
        return Streams.fromNativeList(frames)
                .map(mapper)
                .collect(toList());
    }

    public static <T> Collector<T, magma.api.collect.List<T>> toList() {
        return new Collector<>() {
            @Override
            public magma.api.collect.List<T> createInitial() {
                return new JavaList<>();
            }

            @Override
            public magma.api.collect.List<T> fold(magma.api.collect.List<T> current, T next) {
                return current.add(next);
            }
        };
    }

    @Override
    public magma.api.collect.List<T> add(T next) {
        var copy = new ArrayList<>(list);
        copy.add(next);
        return new JavaList<>(copy);
    }

    @Override
    public Option<Tuple<T, magma.api.collect.List<T>>> popFirst() {
        if (list.isEmpty()) return new None<>();
        var last = list.get(list.size() - 1);
        var slice = new JavaList<>(list.subList(0, list.size() - 1));
        return new Some<>(new Tuple<>(last, slice));
    }

    @Override
    public magma.api.collect.List<T> push(T element) {
        var copy = new ArrayList<>(list);
        copy.add(element);
        return new JavaList<>(copy);
    }

    @Override
    public Stream<T> stream() {
        return new AbstractStream<>(new NativeListHead<>(list));
    }

    @Override
    public boolean contains(T element) {
        return this.list.contains(element);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Option<T> last() {
        if (list.isEmpty()) {
            return new None<>();
        } else {
            return new Some<>(list.get(list.size() - 1));
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Option<magma.api.collect.List<T>> mapLast(Function<T, T> mapper) {
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
