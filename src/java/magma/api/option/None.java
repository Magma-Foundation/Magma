package magma.api.option;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.app.Main;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record None<T>() implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<R>();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public T orElseGet(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
    }

    @Override
    public Option<T> or(Supplier<Option<T>> other) {
        return other.get();
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return new None<R>();
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return new None<T>();
    }

    @Override
    public Tuple2<Boolean, T> toTuple(T other) {
        return new Tuple2Impl<Boolean, T>(false, other);
    }

    @Override
    public <R> Option<Tuple2<T, R>> and(Supplier<Option<R>> other) {
        return new None<Tuple2<T, R>>();
    }
}
