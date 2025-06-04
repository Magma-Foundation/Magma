package magmac.api.iter.collect;

import magmac.api.Tuple2;
import magmac.api.result.Ok;
import magmac.api.result.Result;

/**
 * Collector that transforms {@link magmac.api.result.Result} values while accumulating.
 */

public record ResultCollector<T, C, X>(
        Collector<T, C> collector
) implements Collector<Result<T, X>, Result<C, X>> {
    @Override
    public Result<C, X> createInitial() {
        return new Ok<>(this.collector.createInitial());
    }

    @Override
    public Result<C, X> fold(Result<C, X> currentResult, Result<T, X> element) {
        return currentResult.and(() -> element)
                .mapValue((Tuple2<C, T> tuple) -> this.collector.fold(tuple.left(), tuple.right()));
    }
}
