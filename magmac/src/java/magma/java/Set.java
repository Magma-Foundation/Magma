package magma.java;

import magma.api.collect.stream.Stream;

public interface Set<T> {
    Set<T> add(T next);

    Stream<T> stream();
}
