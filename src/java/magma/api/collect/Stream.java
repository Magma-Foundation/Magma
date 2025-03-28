package magma.api.collect;

import magma.api.option.Option;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Stream<T> {
    <R> R fold(R initial, BiFunction<R, T, R> folder);

    <R> Stream<R> map(Function<T, R> mapper);

    <R> R collect(Collector<T, R> collector);
}
