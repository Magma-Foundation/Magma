package com.meti.collect.stream;

import com.meti.collect.Pair;
import com.meti.collect.option.Option;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PairStream<A, B> implements Stream<Pair<A, B>> {
    private final Stream<Pair<A, B>> parent;

    public PairStream(Stream<Pair<A, B>> parent) {
        this.parent = parent;
    }

    @Override
    public <R> Stream<Pair<Pair<A, B>, R>> cross(Supplier<Stream<R>> other) {
        return parent.cross(other);
    }

    @Override
    public Option<Pair<A, B>> next() {
        return parent.next();
    }

    @Override
    public <R> Stream<R> map(Function<Pair<A, B>, R> mapper) {
        return parent.map(mapper);
    }

    @Override
    public Stream<Pair<A, B>> filter(Predicate<Pair<A, B>> predicate) {
        return parent.filter(predicate);
    }

    @Override
    public <R> Stream<R> flatMap(Function<Pair<A, B>, Stream<R>> mapper) {
        return parent.flatMap(mapper);
    }

    @Override
    public <C> C collect(Collector<Pair<A, B>, C> collector) {
        return parent.collect(collector);
    }

    @Override
    public <R> R into(Function<Stream<Pair<A, B>>, R> mapper) {
        return parent.into(mapper);
    }

    @Override
    public <C> C foldRightFromInitial(C initial, BiFunction<C, Pair<A, B>, C> folder) {
        return parent.foldRightFromInitial(initial, folder);
    }

    @Override
    public <C> Option<C> foldRight(Function<Pair<A, B>, C> mapper, BiFunction<C, Pair<A, B>, C> folder) {
        return parent.foldRight(mapper, folder);
    }

    @Override
    public <C> Option<C> foldRightFromTwo(BiFunction<Pair<A, B>, Pair<A, B>, C> initial, BiFunction<C, Pair<A, B>, C> folder) {
        return parent.foldRightFromTwo(initial, folder);
    }

    @Override
    public <R> Stream<Pair<Pair<A, B>, R>> extend(Function<Pair<A, B>, R> extender) {
        return parent.extend(extender);
    }

    @Override
    public Stream<Pair<A, B>> concat(Stream<Pair<A, B>> other) {
        return parent.concat(other);
    }

    @Override
    public <R> Stream<R> two(BiFunction<Pair<A, B>, Pair<A, B>, R> mapper, Function<Pair<A, B>, R> remaining) {
        return parent.two(mapper, remaining);
    }

    public <R> Stream<Pair<A, R>> mapRight(Function<B, R> mapper) {
        return parent.map(pair -> pair.mapRight(mapper));
    }

    public Stream<B> preserveRight() {
        return parent.map(Pair::right);
    }
}
