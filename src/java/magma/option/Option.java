package magma.option;

import magma.collect.list.List_;
import magma.compile.Node;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    T orElseGet(Supplier<T> other);

    Tuple<Boolean, T> toTuple(T other);

    void ifPresent(Consumer<T> consumer);

    T orElse(T other);

    Option<T> filter(Predicate<T> predicate);

    boolean isPresent();

    <R> R match(Function<T, R> ifPresent, Supplier<R> ifEmpty);

    boolean isEmpty();

    Option<T> or(Supplier<Option<T>> other);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);
}
