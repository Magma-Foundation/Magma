package magma.api.option;

import magma.api.Tuple;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    T orElseGet(Supplier<T> supplier);

    T orElse(T other);

    boolean isPresent();

    Tuple<Boolean, T> toTuple(T other);

    boolean isEmpty();

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    T orElsePanic();
}
