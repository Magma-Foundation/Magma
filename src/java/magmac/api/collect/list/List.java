package magmac.api.collect.list;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.iter.Iter;

import java.util.function.BiFunction;

public interface List<T> {
    List<T> add(T element);

    Iter<T> iter();

    List<T> addAll(List<T> others);

    List<T> removeAll(List<T> others);

    T get(int index);

    List<T> sort(BiFunction<T, T, Integer> sorter);

    T getLast();

    boolean contains(T element);

    int size();

    Option<Tuple2<List<T>, T>> popLast();

    Option<Tuple2<T, List<T>>> popFirst();
}
