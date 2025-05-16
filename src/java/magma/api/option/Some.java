package magma.api.option;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.app.Main;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record Some<T>(T value) implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<R>(mapper.apply(this.value));
    }

    @Override
    public T orElse(T other) {
        return this.value;
    }

    @Override
    public T orElseGet(Supplier<T> supplier) {
        return this.value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        consumer.accept(this.value);
    }

    @Override
    public Option<T> or(Supplier<Option<T>> other) {
        return this;
    }

    @Override
    public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
        return mapper.apply(this.value);
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        if (predicate.test(this.value)) {
            return this;
        }
        return new None<T>();
    }

    @Override
    public Tuple2<Boolean, T> toTuple(T other) {
        return new Tuple2Impl<Boolean, T>(true, this.value);
    }

    @Override
    public <R> Option<Tuple2<T, R>> and(Supplier<Option<R>> other) {
        return other.get().map((R otherValue) -> new Tuple2Impl<T, R>(this.value, otherValue));
    }
}
