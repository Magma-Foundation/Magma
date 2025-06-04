package magmac.api.head;

import magmac.api.Option;
import magmac.api.iter.Iter;
import magmac.api.iter.collect.Collector;
import magmac.api.result.Ok;
import magmac.api.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Iterator implementation backed by a {@link Head}.
 */

public record HeadedIter<T>(Head<T> head) implements Iter<T> {
    @Override
    public <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder) {
        return this.fold(HeadedIter.createInitial(initial),
                (Result<R, X> rxResult, T t) -> rxResult.flatMapValue(
                        (R r) -> folder.apply(r, t)));
    }

    private static <R, X> Result<R, X> createInitial(R initial) {
        return new Ok<>(initial);
    }

    @Override
    public <R> Iter<R> map(Function<T, R> mapper) {
        return new HeadedIter<>(() -> this.head.next().map(mapper));
    }

    @Override
    public <R> R fold(R initial, BiFunction<R, T, R> folder) {
        var current = initial;
        while (true) {
            var finalCurrent = current;
            var option = this.head.next().map((T next) -> folder.apply(finalCurrent, next));
            if (option.isPresent()) {
                current = option.orElse(null);
            }
            else {
                return current;
            }
        }
    }

    @Override
    public <C> C collect(Collector<T, C> collector) {
        return this.fold(collector.createInitial(), collector::fold);
    }

    @Override
    public Iter<T> filter(Predicate<T> predicate) {
        return this.flatMap((T value) -> {
            if (predicate.test(value)) {
                return new HeadedIter<>(new SingleHead<>(value));
            }
            return new HeadedIter<>(new EmptyHead<>());
        });
    }

    @Override
    public Option<T> next() {
        return this.head.next();
    }

    @Override
    public <R> Iter<R> flatMap(Function<T, Iter<R>> mapper) {
        return new HeadedIter<>(this.head.next()
                .map(mapper)
                .<Head<R>>map((Iter<R> initial) -> new FlatMapHead<>(this.head, mapper, initial))
                .orElse(new EmptyHead<>()));
    }

    @Override
    public Iter<T> concat(Iter<T> other) {
        return new HeadedIter<>(() -> this.head.next().or(other::next));
    }
}
