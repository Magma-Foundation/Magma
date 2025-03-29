package magma.option;class package magma.option;{package magma.option;

import java.util.function.Consumer;class import java.util.function.Consumer;{

import java.util.function.Consumer;
import java.util.function.Function;class import java.util.function.Function;{
import java.util.function.Function;
import java.util.function.Predicate;class import java.util.function.Predicate;{
import java.util.function.Predicate;
import java.util.function.Supplier;class import java.util.function.Supplier;{
import java.util.function.Supplier;

public record Some<T>(T value) implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public T orElseGet(Supplier<T> other) {
        return value;
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(true, value);
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        consumer.accept(value);
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return predicate.test(value) ? this : new None<>();
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public <R> R match(Function<T, R> ifPresent, Supplier<R> ifEmpty) {
        return ifPresent.apply(value);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Option<T> or(Supplier<Option<T>> other) {
        return this;
    }
}
class public record Some<T>(T value) implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public T orElseGet(Supplier<T> other) {
        return value;
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(true, value);
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        consumer.accept(value);
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return predicate.test(value) ? this : new None<>();
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public <R> R match(Function<T, R> ifPresent, Supplier<R> ifEmpty) {
        return ifPresent.apply(value);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Option<T> or(Supplier<Option<T>> other) {
        return this;
    }
}{

public record Some<T>(T value) implements Option<T> {
    @Override
    public <R> Option<R> map(Function<T, R> mapper) {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public T orElseGet(Supplier<T> other) {
        return value;
    }

    @Override
    public Tuple<Boolean, T> toTuple(T other) {
        return new Tuple<>(true, value);
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        consumer.accept(value);
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return predicate.test(value) ? this : new None<>();
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public <R> R match(Function<T, R> ifPresent, Supplier<R> ifEmpty) {
        return ifPresent.apply(value);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Option<T> or(Supplier<Option<T>> other) {
        return this;
    }
}
