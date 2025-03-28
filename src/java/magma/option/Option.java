package magma.option;

import magma.result.Tuple;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    T orElseGet(Supplier<T> other);

    Tuple<Boolean, T> toTuple(T other);
}
