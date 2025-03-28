package magma.api.collect;

import magma.api.option.Option;

public interface List_<T> {
    Stream<T> stream();

    Option<List_<T>> subList(int fromInclusive, int toExclusive);

    List_<T> add(T element);
}
