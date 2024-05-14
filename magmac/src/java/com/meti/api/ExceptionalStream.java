package com.meti.api;

import com.meti.compile.Option;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;

import java.util.function.Function;
import java.util.function.Predicate;

public record ExceptionalStream<T, E extends Throwable>(Stream<Result<T, E>> parent) implements Stream<Result<T, E>> {
    @Override
    public Stream<Result<T, E>> filter(Predicate<Result<T, E>> predicate) {
        return parent.filter(predicate);
    }

    @Override
    public Option<Result<T, E>> head() {
        return parent.head();
    }

    @Override
    public <R> R into(Function<Stream<Result<T, E>>, R> mapper) {
        return parent.into(mapper);
    }

    @Override
    public <C> C collect(Collector<Result<T, E>, C> collector) {
        return parent.collect(collector);
    }

    @Override
    public <R> Stream<R> flatMap(Function<Result<T, E>, Stream<R>> mapper) {
        return parent.flatMap(mapper);
    }

    @Override
    public <R> Stream<R> map(Function<Result<T, E>, R> mapper) {
        return parent.map(mapper);
    }

    public <C> Result<C, E> collectExceptionally(Collector<T, C> collector) {
        return parent.collect(new Collector<>() {
            @Override
            public Result<C, E> initial() {
                return new Ok<>(collector.initial());
            }

            @Override
            public Result<C, E> fold(Result<C, E> current, Result<T, E> next) {
                return current.and(next).mapValue(tuple -> collector.fold(tuple.a(), tuple.b()));
            }
        });
    }
}
