package magma.api.collect;

import magma.api.collect.stream.Stream;
import magma.api.option.Option;

public interface List<T> {
    List<T> add(T next);

    Stream<T> stream();

    boolean contains(T element);

    boolean isEmpty();

    Option<T> last();

    int size();
}
