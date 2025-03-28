package jvm.api.io;

import magma.api.collect.Collector;
import magma.api.collect.List_;

class JavaListCollector<T> implements Collector<T, List_<T>> {
    @Override
    public List_<T> initial() {
        return new JavaList<>();
    }

    @Override
    public List_<T> fold(List_<T> current, T next) {
        return current.add(next);
    }
}
