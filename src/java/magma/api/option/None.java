package magma.api.option;

import magma.api.result.Tuple;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class None<T> implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new None<>();
    }

    @Override
    public T orElseGet(Supplier<T> other) {
        return other.get();
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(false, other);
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
    }

    @Override
    public Option<T> or(Supplier<Option<T>> other) {
        return other.get();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other) {
        return new None<>();
    }
}
