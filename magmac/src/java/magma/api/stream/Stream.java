package magma.api.stream;

import magma.api.option.Option;

import java.util.function.Function;

public interface Stream<T> extends Head<T> {
    <R> Stream<R> map(Function<T, R> mapper);

    <C> C collect(Collector<T, C> collector);

    Option<T> head();
}
