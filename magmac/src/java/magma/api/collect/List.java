package magma.api.collect;

import magma.api.Tuple;
import magma.api.option.Option;
import magma.api.collect.stream.Stream;

public interface List<T> {
    List<T> add(T next);

    Option<Tuple<T, List<T>>> popFirst();

    default Option<List<T>> popFirstAndDiscard() {
        return popFirst().map(Tuple::right);
    }

    List<T> push(T element);

    Stream<T> stream();

    boolean contains(T element);

    boolean isEmpty();

    Option<T> last();

    int size();
}
