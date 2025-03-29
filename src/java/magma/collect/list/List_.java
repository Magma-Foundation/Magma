package magma.collect.list;

import magma.collect.stream.Stream;

import java.util.List;

public interface List_<T> {
    Stream<T> stream();

    List_<T> add(T element);

    List_<T> addAll(List_<T> others);
}
