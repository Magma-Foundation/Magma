package jvm.collect.set;

import jvm.collect.stream.Streams;
import magma.collect.set.Set_;
import magma.collect.stream.Stream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public record JavaSet<T>(Set<T> set) implements Set_<T> {
    public JavaSet() {
        this(new HashSet<>());
    }

    @Override
    public Stream<T> stream() {
        return Streams.fromNativeList(new ArrayList<>(set));
    }

    @Override
    public Set_<T> add(T element) {
        set.add(element);
        return this;
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        set.forEach(consumer);
    }
}
