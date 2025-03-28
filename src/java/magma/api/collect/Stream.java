package magma.api.collect;

import magma.api.option.Option;
import magma.api.result.Tuple;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<T> {
    <R> R fold(R initial, BiFunction<R, T, R> folder);

    <R> Stream<R> map(Function<T, R> mapper);

    <R> R collect(Collector<T, R> collector);

    <R> Stream<Tuple<T, R>> zip(Stream<R> other);

    Option<T> next();

    boolean allMatch(Predicate<T> predicate);
}
