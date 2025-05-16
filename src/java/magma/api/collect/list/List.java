package magma.api.collect.list;

import magma.api.Tuple2;
import magma.api.collect.Query;
import magma.api.option.Option;

import java.util.function.BiFunction;

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

    boolean contains(T element, BiFunction<T, T, Boolean> equator);

    Query<T> queryReversed();

    List<T> addFirst(T element);

    boolean isEmpty();

    boolean equalsTo(List<T> other, BiFunction<T, T, Boolean> equator);

    List<T> removeValue(T element, BiFunction<T, T, Boolean> equator);

    Option<List<T>> removeLast();
}
