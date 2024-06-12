package magma.api;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.DoubleStream;

public interface Stream<T> {
    <R> Stream<R> map(Function<T, R> mapper);

    <C> C collect(Collector<T, C> collector);
}
