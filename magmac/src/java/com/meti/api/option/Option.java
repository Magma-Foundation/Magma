package com.meti.api.option;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    T $() throws OptionException;

    <R> R match(Function<T, R> mapper, Supplier<R> supplier);

    T orElse(T other);

    <R> Option<R> map(Function<T, R> mapper);

    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    void ifPresent(Consumer<T> consumer);

    default <R> R into(Function<Option<T>, R> mapper) {
        return mapper.apply(this);
    }

    T orElseGet(Supplier<T> other);
}
