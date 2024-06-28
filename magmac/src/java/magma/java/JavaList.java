package magma.java;

import magma.api.collect.stream.Collector;
import magma.api.collect.stream.HeadedStream;
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
                .collect(collecting());
    }

    public static <T> Collector<T, magma.api.collect.List<T>> collecting() {
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

    public static <T> magma.api.collect.List<T> fromNative(List<T> list) {
        return new JavaList<>(list);
    }

    public static <T> List<T> toNative(magma.api.collect.List<T> values) {
        return values.stream().foldLeft(new ArrayList<>(), (ts, t) -> {
            ts.add(t);
            return ts;
        });
    }

    public static <T> magma.api.collect.List<T> empty() {
        return new JavaList<>();
    }

    @SafeVarargs
    public static <T> magma.api.collect.List<T> of(T... values) {
        return new JavaList<>(List.of(values));
    }

    @Override
    public magma.api.collect.List<T> add(T next) {
        var copy = new ArrayList<>(list);
        copy.add(next);
        return new JavaList<>(copy);
    }

    @Override
    public Stream<T> stream() {
        return new HeadedStream<>(new NativeListHead<>(list));
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

}
