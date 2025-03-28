package magma.api.collect;

import magma.api.option.Option;
import magma.api.result.Tuple;

public interface List_<T> {
    Stream<T> stream();

    Option<List_<T>> subList(int fromInclusive, int toExclusive);

    List_<T> add(T element);

    int size();

    boolean isEmpty();

    Option<Tuple<T, List_<T>>> popFirst();

    List_<T> addAll(List_<T> other);
}
