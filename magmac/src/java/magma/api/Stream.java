package magma.api;

import java.util.Optional;
import java.util.function.Function;

public interface Stream<T> {
    Optional<T> head();

    <R> Stream<R> map(Function<T, R> mapper);

    <C> C collect(Collector<T, C> collector);
}
