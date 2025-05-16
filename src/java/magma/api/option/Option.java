package magma.api.option;

import magma.api.Tuple2;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    T orElse(T other);

    T orElseGet(Supplier<T> supplier);

    boolean isPresent();

    void ifPresent(Consumer<T> consumer);

    Option<T> or(Supplier<Option<T>> other);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    Option<T> filter(Predicate<T> predicate);

    Tuple2<Boolean,T> toTuple(T other);

    <R> Option<Tuple2<T,R>> and(Supplier<Option<R>> other);
}
