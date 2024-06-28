package magma.api.collect.stream;

import magma.api.option.Option;
import magma.api.option.Some;

public record RequiredCollector<C, T>(Collector<T, C> collector) implements Collector<Option<T>, Option<C>> {
    @Override
    public Option<C> createInitial() {
        return new Some<>(collector.createInitial());
    }

    @Override
    public Option<C> fold(Option<C> current, Option<T> next) {
        return current.and(next).map(inner -> collector.fold(inner.left(), inner.right()));
    }
}
