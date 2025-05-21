package magma.api.collect.list;

import magma.api.option.Option;

public interface Sequence<T> extends Iterable<T> {
    int size();

    Option<T> findLast();

    Option<T> findFirst();

    Option<T> find(int index);

    boolean isEmpty();

    boolean contains(T element);
}
