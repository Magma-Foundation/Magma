package magma.api.collect.list;

import magma.api.Tuple2;
import magma.api.collect.Query;
import magma.api.option.Option;

public interface List<T> {
    List<T> addLast(T element);

    Query<T> query();

    int size();

    Option<List<T>> subList(int startInclusive, int endExclusive);

    Option<T> findLast();

    Option<T> findFirst();

    Option<T> find(int index);

    Query<Tuple2<Integer,T>> queryWithIndices();

    List<T> addAll(List<T> others);

    boolean contains(T element);

    Query<T> queryReversed();

    List<T> addFirst(T element);

    boolean isEmpty();

    boolean equalsTo(List<T> other);

    List<T> removeValue(T element);

    Option<List<T>> removeLast();
}
