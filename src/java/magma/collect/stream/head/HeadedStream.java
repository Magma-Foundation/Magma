package magma.collect.stream.head;

import magma.collect.stream.Collector;
import magma.collect.stream.Stream;
import magma.option.Option;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public record HeadedStream<T>(Head<T> head) implements Stream<T> {
    @Override
    public <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder) {
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
        return foldWithInitial(collector.createInitial(), collector::fold);
    }

    @Override
    public Option<T> next() {
        return head.next();
    }

    @Override
    public boolean anyMatch(Predicate<T> predicate) {
        return foldWithInitial(false, (current, t) -> current || predicate.test(t));
    }

    @Override
    public boolean allMatch(Predicate<T> predicate) {
        return foldWithInitial(true, (aBoolean, t) -> aBoolean && predicate.test(t));
    }

    @Override
    public <R> Option<R> foldMapping(Function<T, R> mapper, BiFunction<R, T, R> folder) {
        return head.next().map(mapper).map(initial -> foldWithInitial(initial, folder));
    }

    @Override
    public Stream<T> filter(Predicate<T> predicate) {
        return flatMap(element -> new HeadedStream<>(predicate.test(element)
                ? new SingleHead<>(element)
                : new EmptyHead<>()));
    }

    @Override
    public <R> Stream<R> flatMap(Function<T, Stream<R>> mapper) {
        return this.<Stream<R>>foldWithInitial(new HeadedStream<>(new EmptyHead<>()),
                (rStream, t) -> rStream.concat(mapper.apply(t)));
    }

    @Override
    public <R, X> magma.result.Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder) {
        return this.<Result<R, X>>foldWithInitial(new Ok<>(initial), (current, t) -> current.flatMapValue(inner -> folder.apply(inner, t)));
    }

    @Override
    public Stream<T> concat(Stream<T> other) {
        return new HeadedStream<>(() -> head.next().or(other::next));
    }
}
