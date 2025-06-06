package magmac.api.iter.collect;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;

/**
 * Collector that accumulates elements into a {@link magmac.api.collect.list.List}.
 */

public record ListCollector<T>() implements Collector<T, List<T>> {
    @Override
    public List<T> createInitial() {
        return Lists.empty();
    }

    @Override
    public List<T> fold(List<T> current, T element) {
        return current.addLast(element);
    }
}
