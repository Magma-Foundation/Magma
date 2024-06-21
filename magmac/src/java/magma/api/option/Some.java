package magma.api.option;

import magma.api.Tuple;

import java.util.function.Function;
import java.util.function.Supplier;

public record Some<T>(T value) implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public T orElseGet(Supplier<T> supplier) {
        return value;
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(true, value);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public T orElsePanic() {
        return value;
    }
}
