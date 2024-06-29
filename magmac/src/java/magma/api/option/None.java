package magma.api.option;

import magma.api.Tuple;

import java.util.function.Function;
import java.util.function.Supplier;

public class None<T> implements Option<T> {
    public static <T> None<T> None() {
        return new None<>();
    }

    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return None();
    }

    @Override
    public T orElseGet(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(false, other);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return None();
    }

    @Override
    public T orElsePanic() {
        throw new RuntimeException("Option was empty!");
    }

    @Override
    public <R> Option<Tuple<T, R>> and(Option<R> other) {
        return None();
    }
}
