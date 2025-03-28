package magma.api.option;

import magma.api.result.Tuple;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    T orElseGet(Supplier<T> other);

    Tuple<Boolean, T> toTuple(T other);

    void ifPresent(Consumer<T> consumer);

    Option<T> or(Supplier<Option<T>> other);

    T orElse(T other);

    <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other);
}
