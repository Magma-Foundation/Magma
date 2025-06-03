package magmac.api;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Option<T> {
    <R> Option<R> map(Function<T, R> mapper);

    boolean isPresent();

    T orElseGet(Supplier<T> other);

    boolean isEmpty();

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    T orElse(T other);

    Option<T> filter(Predicate<T> predicate);

    Option<T> or(Supplier<Option<T>> other);

    void ifPresent(Consumer<T> consumer);
}
