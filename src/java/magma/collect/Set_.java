package magma.collect;

import magma.collect.stream.Stream;

public interface Set_<T> {
    Stream<T> stream();

    Set_<T> add(T element);
}
