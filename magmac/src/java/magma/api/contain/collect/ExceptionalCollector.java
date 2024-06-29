package magma.api.contain.collect;

import magma.api.result.Ok;
import magma.api.result.Result;

public record ExceptionalCollector<C, E, T>(
        Collector<T, C> collector
) implements Collector<Result<T, E>, Result<C, E>> {
    @Override
    public Result<C, E> createInitial() {
        return new Ok<>(collector.createInitial());
    }

    @Override
    public Result<C, E> fold(Result<C, E> current, Result<T, E> next) {
        return current.flatMapValue(inner -> next.mapValue(inner0 -> collector.fold(inner, inner0)));
    }
}
