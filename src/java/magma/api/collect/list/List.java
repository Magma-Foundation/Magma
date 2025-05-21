package magma.api.collect.list;

import magma.api.option.Option;

public interface List<T> extends Sequence<T> {
    List<T> addLast(T element);

    Option<List<T>> subList(int startInclusive, int endExclusive);

    List<T> addAll(Iterable<T> others);

    List<T> addFirst(T element);

    List<T> removeValue(T element);

    Option<List<T>> removeLast();
}
