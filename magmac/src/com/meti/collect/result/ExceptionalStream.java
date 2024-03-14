package com.meti.collect.result;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collector;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;

import java.util.function.Function;
import java.util.function.Predicate;

public class ExceptionalStream<T, E extends Throwable> implements Stream<Result<T, E>> {
    private final Stream<Result<T, E>> parent;

    public ExceptionalStream(Stream<Result<T, E>> parent) {
        this.parent = parent;
    }

    @Override
    public Option<Result<T, E>> next() {
        return parent.next();
    }

    @Override
    public <R> Stream<R> map(Function<Result<T, E>, R> mapper) {
        return parent.map(mapper);
    }

    @Override
    public Stream<Result<T, E>> filter(Predicate<Result<T, E>> predicate) {
        return parent.filter(predicate);
    }

    @Override
    public <R> Stream<R> flatMap(Function<Result<T, E>, Stream<R>> mapper) {
        return parent.flatMap(mapper);
    }

    @Override
    public <C> C collect(Collector<Result<T, E>, C> collector) {
        return parent.collect(collector);
    }

    public <R> ExceptionalStream<R, E> mapValues(Function<T, R> mapper) {
        return new ExceptionalStream<>(parent.map(inner -> inner.mapValue(mapper)));
    }

    public <C> Result<C, E> collectExceptionally(Collector<T, C> collector) {
        return parent.collect(Collectors.exceptionally(collector));
    }
}
