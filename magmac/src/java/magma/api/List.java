package magma.api;

import magma.api.option.Option;
import magma.api.stream.Stream;
import magma.java.JavaList;

import java.util.function.Function;

public interface List<T> {
    List<T> add(T next);

    Option<Tuple<T, List<T>>> pop();

    JavaList<T> push(T element);

    Stream<T> stream();

    boolean contains(T element);

    boolean isEmpty();

    Option<T> last();

    int size();

    Option<List<T>> mapLast(Function<T, T> mapper);
}
