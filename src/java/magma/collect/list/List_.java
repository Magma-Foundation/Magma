package magma.collect.list;

import magma.collect.stream.Stream;
import magma.option.Option;
import magma.option.Tuple;

import java.util.function.BiFunction;

public interface List_<T> {
    Stream<T> stream();

    List_<T> add(T element);

    List_<T> addAll(List_<T> others);

    Option<T> findFirst();

    int size();

    List_<T> subList(int start, int end);

    boolean equalsTo(List_<T> other);

    List_<T> sort(BiFunction<T, T, Integer> comparator);

    T get(int index);

    Option<Tuple<T, List_<T>>> popFirst();
}
