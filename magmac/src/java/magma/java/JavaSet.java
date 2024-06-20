package magma.java;

import magma.api.option.Option;
import magma.api.stream.AbstractStream;
import magma.api.stream.Collector;
import magma.api.stream.Head;
import magma.api.stream.Stream;
import magma.api.stream.Streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;

public record JavaSet<T>(Set<T> set) {
    public JavaSet() {
        this(Collections.emptySet());
    }

    public static <T, R> JavaSet<R> fromNative(Set<T> frames, Function<T, R> mapper) {
        return Streams.fromNativeList(new ArrayList<>(frames))
                .map(mapper)
                .collect(toSet());
    }

    private static <R> Collector<R, JavaSet<R>> toSet() {
        return new Collector<R, JavaSet<R>>() {
            @Override
            public JavaSet<R> createInitial() {
                return new JavaSet<>();
            }

            @Override
            public JavaSet<R> fold(JavaSet<R> current, R next) {
                return current.add(next);
            }
        };
    }

    private JavaSet<T> add(T next) {
        var copy = new HashSet<>(set);
        copy.add(next);
        return new JavaSet<>(copy);
    }

    public Stream<T> stream() {
        return new AbstractStream<>(new NativeListStream<>(new ArrayList<>(set)));
    }
}
