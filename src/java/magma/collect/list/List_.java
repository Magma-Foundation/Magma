package magma.collect.list;

import magma.collect.stream.Stream;

public interface List_<T> {
    Stream<T> stream();

    List_<T> add(T element);
}
