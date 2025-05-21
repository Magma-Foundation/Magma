package magma.api.collect.list;

import magma.api.Tuple2;
import magma.api.collect.Iter;
import magma.api.option.Option;

public interface List<T> {
    List<T> addLast(T element);

    Iter<T> query();

    int size();

    Option<List<T>> subList(int startInclusive, int endExclusive);

    Option<T> findLast();

    Option<T> findFirst();

    Option<T> find(int index);

    Iter<Tuple2<Integer,T>> queryWithIndices();

    List<T> addAll(List<T> others);

    boolean contains(T element);

    Iter<T> queryReversed();

    List<T> addFirst(T element);

    boolean isEmpty();

    List<T> removeValue(T element);

    Option<List<T>> removeLast();
}
