package com.meti.api.option;

import com.meti.api.result.Result;
import com.meti.api.result.Tuple;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public record ErrorOption<T, E extends Throwable>(Option<Result<T, E>> parent) implements Option<Result<T, E>> {
    @Override
    public boolean isEmpty() {
        return parent.isEmpty();
    }

    @Override
    public Result<T, E> $() throws OptionException {
        return parent.$();
    }

    @Override
    public <R> Option<R> map(Function<Result<T, E>, R> mapper) {
        return parent.map(mapper);
    }

    @Override
    public Result<T, E> orElse(Result<T, E> other) {
        return parent.orElse(other);
    }

    @Override
    public Tuple<Boolean, Result<T, E>> toTuple(Result<T, E> other) {
        return parent.toTuple(other);
    }

    @Override
    public boolean isPresent() {
        return parent.isPresent();
    }

    @Override
    public <U, R> Option<R> xnor(Option<U> contentEnd, BiFunction<Result<T, E>, U, R> mapper, Supplier<R> supplier) {
        return parent.xnor(contentEnd, mapper, supplier);
    }

    @Override
    public Result<T, E> orElseGet(Supplier<Result<T, E>> supplier) {
        return parent.orElseGet(supplier);
    }

    @Override
    public <R> Option<R> flatMap(Function<Result<T, E>, Option<R>> mapper) {
        return parent.flatMap(mapper);
    }

    @Override
    public <R> R into(Function<Option<Result<T, E>>, R> mapper) {
        return parent.into(mapper);
    }

    public <R> ErrorOption<R, E> mapValue(Function<T, R> mapper) {
        return new ErrorOption<>(map(inner -> inner.mapValue(mapper)));
    }

    public <R extends Throwable> ErrorOption<T, R> mapErr(Function<E, R> mapper) {
        return new ErrorOption<>(map(inner -> inner.mapErr(mapper)));
    }
}
