package magmac.api.head;

import magmac.api.collect.Collector;
import magmac.api.iter.Iter;
import magmac.api.result.Ok;
import magmac.api.result.Result;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public record HeadedIter<T>(Head<T> head) implements Iter<T> {
    @Override
    public <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder) {
        return this.<Result<R, X>>fold(new Ok<>(initial),
                (rxResult, t) -> rxResult.flatMapValue(
                        r -> folder.apply(r, t)));
    }

    @Override
    public <R> Iter<R> map(Function<T, R> mapper) {
        return new HeadedIter<>(() -> this.head.next().map(mapper));
    }

    @Override
    public <R> R fold(R initial, BiFunction<R, T, R> folder) {
        R current = initial;
        while (true) {
            R finalCurrent = current;
            Optional<R> optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
            if (optional.isPresent()) {
                current = optional.get();
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
        return this.flatMap(value -> new HeadedIter<>(predicate.test(value)
                ? new SingleHead<>(value)
                : new EmptyHead<>()));
    }

    @Override
    public Optional<T> next() {
        return this.head.next();
    }

    private <R> Iter<R> flatMap(Function<T, Iter<R>> mapper) {
        return new HeadedIter<>(this.head.next()
                .map(mapper)
                .<Head<R>>map(initial -> new FlatMapHead<>(this.head, mapper, initial))
                .orElse(new EmptyHead<>()));
    }
}
