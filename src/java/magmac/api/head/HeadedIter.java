package magmac.api.head;

import magmac.api.Option;
import magmac.api.iter.Iter;
import magmac.api.iter.collect.Collector;
import magmac.api.result.Ok;
import magmac.api.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public record HeadedIter<T>(Head<T> head) implements Iter<T> {
    @Override
    public <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder) {
        return this.fold(this.createInitial(initial),
                (rxResult, t) -> rxResult.flatMapValue(
                        r -> folder.apply(r, t)));
    }

    private <R, X> Result<R, X> createInitial(R initial) {
        return new Ok<>(initial);
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
            Option<R> option = this.head.next().map(next -> folder.apply(finalCurrent, next));
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
        return this.fold(collector.createInitial(), (current, element) -> collector.fold(current, element));
    }

    @Override
    public Iter<T> filter(Predicate<T> predicate) {
        return this.flatMap(value -> {
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
                .<Head<R>>map(initial -> new FlatMapHead<>(this.head, mapper, initial))
                .orElse(new EmptyHead<>()));
    }

    @Override
    public Iter<T> concat(Iter<T> other) {
        return new HeadedIter<>(() -> this.head.next().or(() -> other.next()));
    }
}
