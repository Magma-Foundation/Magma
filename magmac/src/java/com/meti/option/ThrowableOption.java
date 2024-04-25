package com.meti.option;

import com.meti.option.Option;
import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.function.Function;
import java.util.function.Supplier;

public record ThrowableOption<T>(Option<T> parent) implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return parent.map(mapper);
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return parent.flatMap(mapper);
    }

    @Override
    public T orElse(T value) {
        return parent.orElse(value);
    }

    @Override
    public <R> R into(Function<Option<T>, R> mapper) {
        return parent.into(mapper);
    }

    public <E extends Throwable> Result<T, E> orElseThrow(Supplier<E> supplier) {
        return parent
                .<Result<T, E>>map(Ok::new)
                .orElse(new Err<>(supplier.get()));
    }
}
