package com.meti.collect.result;

import com.meti.collect.Pair;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collector;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ExceptionalStream<T, E extends Throwable> implements Stream<Result<T, E>> {
    private final Stream<Result<T, E>> parent;

    public ExceptionalStream(Stream<Result<T, E>> parent) {
        this.parent = parent;
    }

    @Override
    public <C> Option<C> foldRight(Function<Result<T, E>, C> mapper, BiFunction<C, Result<T, E>, C> folder) {
        return parent.foldRight(mapper, folder);
    }

    @Override
    public <R> Stream<R> two(BiFunction<Result<T, E>, Result<T, E>, R> mapper, Function<Result<T, E>, R> remaining) {
        return parent.two(mapper, remaining);
    }

    @Override
    public <R> Stream<Pair<Result<T, E>, R>> cross(Supplier<Stream<R>> other) {
        return parent.cross(other);
    }

    @Override
    public <C> Option<C> foldRightFromTwo(BiFunction<Result<T, E>, Result<T, E>, C> initial, BiFunction<C, Result<T, E>, C> folder) {
        return parent.foldRightFromTwo(initial, folder);
    }

    @Override
    public <R> R into(Function<Stream<Result<T, E>>, R> mapper) {
        return parent.into(mapper);
    }

    @Override
    public <C> C foldRightFromInitial(C initial, BiFunction<C, Result<T, E>, C> folder) {
        return parent.foldRightFromInitial(initial, folder);
    }

    @Override
    public <R> Stream<Pair<Result<T, E>, R>> extend(Function<Result<T, E>, R> extender) {
        return parent.extend(extender);
    }

    @Override
    public Stream<Result<T, E>> concat(Stream<Result<T, E>> other) {
        return parent.concat(other);
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

    public <R> ExceptionalStream<R, E> mapInner(Function<T, R> mapper) {
        return new ExceptionalStream<>(parent.map(inner -> inner.mapValue(mapper)));
    }

    public <C> Result<C, E> collectExceptionally(Collector<T, C> collector) {
        return parent.collect(Collectors.exceptionally(collector));
    }

    public <C> Result<C, E> foldRightFromInitialExceptionally(C initial, BiFunction<C, T, C> folder) {
        return parent.collect(Collectors.exceptionally(new Collector<T, C>() {
            @Override
            public C initial() {
                return initial;
            }

            @Override
            public C fold(C current, T element) {
                return folder.apply(current, element);
            }
        }));
    }
}
