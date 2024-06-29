package magma.api.contain;

import magma.api.contain.collect.Collector;

public record ListCollector<T>(List<T> initial) implements Collector<T, List<T>> {
    @Override
    public List<T> createInitial() {
        return initial;
    }

    @Override
    public List<T> fold(List<T> current, T next) {
        return current.addLast(next);
    }
}
