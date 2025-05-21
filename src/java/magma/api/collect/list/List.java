package magma.api.collect.list;

import magma.api.option.Option;

public interface List<T> extends Iterable<T> {
    List<T> addLast(T element);

    int size();

    Option<List<T>> subList(int startInclusive, int endExclusive);

    Option<T> findLast();

    Option<T> findFirst();

    Option<T> find(int index);

    List<T> addAll(Iterable<T> others);

    boolean contains(T element);

    List<T> addFirst(T element);

    boolean isEmpty();

    List<T> removeValue(T element);

    Option<List<T>> removeLast();
}
