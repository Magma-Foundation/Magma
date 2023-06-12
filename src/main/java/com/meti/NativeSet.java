package com.meti;

import java.util.HashSet;
import java.util.Set;

public class NativeSet<T> {
    private final Set<T> values;

    public NativeSet() {
        this(new HashSet<>());
    }

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
        return new ArrayIterable<>(this.values.toArray());
    }

    public NativeSet<T> add(T value) {
        var copy = new HashSet<>(this.values);
        copy.add(value);
        return new NativeSet<>(copy);
    }

    public int size() {
        return this.values.size();
    }

    public Set<T> unwrap() {
        return this.values;
    }
}
