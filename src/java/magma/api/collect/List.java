package magma.api.collect;

import magma.api.option.Option;
import magma.app.Main;

public interface List<T> {
    List<T> add(T element);

    Main.Query<T> query();

    int size();

    Option<List<T>> subList(int startInclusive, int endExclusive);

    Option<T> findLast();

    Option<T> findFirst();

    Option<T> find(int index);

    Main.Query<Main.Tuple<Integer, T>> queryWithIndices();

    List<T> addAll(List<T> others);

    boolean contains(T element);

    Main.Query<T> queryReversed();

    List<T> addFirst(T element);
}
