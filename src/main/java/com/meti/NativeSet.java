package com.meti;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class NativeSet<T> {
    private final Set<T> values;

    public NativeSet(Set<T> values) {
        this.values = values;
    }

    public static <T> NativeSet<T> empty() {
        return new NativeSet<>(new HashSet<>());
    }

    public Option<T> any() {
        return values.stream()
                .findAny()
                .map(Options::Some)
                .orElse(Options.None());
    }

    public Iterable<T> iter() {
        return new Iterable<>() {
            @Override
            public <R> R foldLeft(R initial, BiFunction<R, T, R> folder) {
                var current = initial;
                for (T value : values) {
                    current = folder.apply(current, value);
                }

                return current;
            }
        };
    }

    public NativeSet<T> add(T value) {
        var copy = new HashSet<>(this.values);
        copy.add(value);
        return new NativeSet<>(copy);
    }
}
