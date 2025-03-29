package magma.collect.stream;

import magma.collect.Collector;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Stream<T> {
    <R> R fold(R initial, BiFunction<R, T, R> folder);

    <R> Stream<R> map(Function<T, R> mapper);

    <C> C collect(Collector<T, C> collector);
}
