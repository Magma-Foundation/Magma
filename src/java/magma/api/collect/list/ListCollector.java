package magma.api.collect.list;

import jvm.api.collect.list.Lists;
import magma.api.collect.Collector;

public class ListCollector<T> implements Collector<T, List<T>> {
    @Override
    public List<T> createInitial() {
        return Lists.empty();
    }

    @Override
    public List<T> fold(final List<T> current, final T element) {
        return current.addLast(element);
    }
}
