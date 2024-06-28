package magma.api.collect;

import magma.api.collect.stream.Collector;

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
