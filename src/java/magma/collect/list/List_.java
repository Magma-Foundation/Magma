package magma.collect.list;

import magma.collect.stream.Stream;
import magma.option.Option;
import magma.option.Tuple;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

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

    boolean isEmpty();

    List_<T> clear();

    Option<T> findLast();

    void forEach(Consumer<T> consumer);

    List_<T> mapLast(Function<T, T> mapper);
}
