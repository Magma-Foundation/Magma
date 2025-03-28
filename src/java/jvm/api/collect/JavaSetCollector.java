package jvm.api.collect;

import magma.api.collect.Collector;
import magma.api.collect.Set_;

public class JavaSetCollector<T> implements Collector<T, Set_<T>> {
    @Override
    public Set_<T> initial() {
        return new JavaSet<T>();
    }

    @Override
    public Set_<T> fold(Set_<T> current, T next) {
        return current.add(next);
    }
}
