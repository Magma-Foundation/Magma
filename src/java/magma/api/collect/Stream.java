package magma.api.collect;

import jvm.api.io.JavaList;
import magma.api.io.Path_;
import magma.api.option.Option;
import magma.api.result.Result;
import magma.api.result.Tuple;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<T> {
    <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder);

    <R> Stream<R> map(Function<T, R> mapper);

    <R> R collect(Collector<T, R> collector);

    <R> Stream<Tuple<T, R>> zip(Stream<R> other);

    Option<T> next();

    boolean allMatch(Predicate<T> predicate);

    <R> Option<R> foldWithMapper(Function<T, R> mapper, BiFunction<R, T, R> folder);

    Stream<T> filter(Predicate<T> predicate);

    <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder);

    Stream<T> concat(Stream<T> other);
}
