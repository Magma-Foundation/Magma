package magma.api.stream;

import magma.api.option.Option;
import magma.api.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Stream<T> extends Head<T> {
    <R> Stream<R> map(Function<T, R> mapper);

    <C> C collect(Collector<T, C> collector);

    Option<T> head();

    <R, E> Result<R, E> foldRightToResult(R initial, BiFunction<R, T, Result<R, E>> mapper);
}
