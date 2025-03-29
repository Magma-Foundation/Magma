package magma.collect.stream;

import magma.collect.Collector;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;

public record HeadedStream<T>(Head<T> head) implements Stream<T> {
    @Override
    public <R> R fold(R initial, BiFunction<R, T, R> folder) {
        R current = initial;
        while (true) {
            R finalCurrent = current;
            Tuple<Boolean, R> tuple = head.next()
                    .map(inner -> folder.apply(finalCurrent, inner))
                    .toTuple(current);

            if (tuple.left()) {
                current = tuple.right();
            } else {
                break;
            }
        }

        return current;
    }

    @Override
    public <R> Stream<R> map(Function<T, R> mapper) {
        return new HeadedStream<>(() -> head.next().map(mapper));
    }

    @Override
    public <C> C collect(Collector<T, C> collector) {
        return fold(collector.createInitial(), collector::fold);
    }

    @Override
    public <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder) {
        return this.<Result<R, X>>fold(new Ok<>(initial),
                (rxResult, t) -> rxResult.flatMapValue(inner -> folder.apply(inner, t)));
    }
}
