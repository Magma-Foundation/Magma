package magma.api.collect.list;

import magma.api.Tuple2;
import magma.api.collect.Iter;

public interface Iterable<T> {
    Iter<T> iter();

    Iter<Tuple2<Integer, T>> iterWithIndices();

    Iter<T> iterReversed();
}
