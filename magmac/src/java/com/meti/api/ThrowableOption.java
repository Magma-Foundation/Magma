package com.meti.api;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public record ThrowableOption<T>(Option<T> parent) implements Option<T> {
    @Override
    public T $() throws OptionException {
        return parent.$();
    }


    @Override
    public <R> R match(Function<T, R> mapper, Supplier<R> supplier) {
        return parent.match(mapper, supplier);
    }

    @Override
    public T orElse(T other) {
        return parent.orElse(other);
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return parent.map(mapper);
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return parent.flatMap(mapper);
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        parent.ifPresent(consumer);
    }

    public <E> Result<T, E> orElseThrow(Supplier<E> errSupplier) {
        return parent.<Result<T, E>>map(Ok::new).orElseGet(() -> new Err<>(errSupplier.get()));
    }
}
