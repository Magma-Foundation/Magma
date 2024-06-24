package magma.java;

import magma.api.collect.stream.HeadedStream;
import magma.api.collect.stream.Collector;
import magma.api.collect.stream.Stream;
import magma.api.collect.stream.Streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public record JavaSet<T>(Set<T> set) implements magma.java.Set<T> {
    public JavaSet() {
        this(Collections.emptySet());
    }

    public static <T, R> magma.java.Set<R> fromNative(Set<T> frames, Function<T, R> mapper) {
        return Streams.fromNativeList(new ArrayList<>(frames))
                .map(mapper)
                .collect(toSet());
    }

    private static <R> Collector<R, magma.java.Set<R>> toSet() {
        return new Collector<R, magma.java.Set<R>>() {
            @Override
            public magma.java.Set<R> createInitial() {
                return new JavaSet<>();
            }

            @Override
            public magma.java.Set<R> fold(magma.java.Set<R> current, R next) {
                return current.add(next);
            }
        };
    }

    public static <T> Collector<T, magma.java.Set<T>> collecting() {
        return new Collector<>() {
            @Override
            public magma.java.Set<T> createInitial() {
                return new JavaSet<>();
            }

            @Override
            public magma.java.Set<T> fold(magma.java.Set<T> current, T next) {
                return current.add(next);
            }
        };
    }

    @Override
    public Stream<T> stream() {
        return new HeadedStream<>(new NativeListHead<>(new ArrayList<>(set)));
    }

    @Override
    public magma.java.Set<T> add(T next) {
        var copy = new HashSet<>(set);
        copy.add(next);
        return new JavaSet<>(copy);
    }
}
