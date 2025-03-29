package magma;

import magma.collect.Set_;
import magma.collect.stream.Stream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
}
