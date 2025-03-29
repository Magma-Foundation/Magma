package magma.option;class package magma.option;{package magma.option;

import java.util.function.Consumer;class import java.util.function.Consumer;{

import java.util.function.Consumer;
import java.util.function.Function;class import java.util.function.Function;{
import java.util.function.Function;
import java.util.function.Predicate;class import java.util.function.Predicate;{
import java.util.function.Predicate;
import java.util.function.Supplier;class import java.util.function.Supplier;{
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
    public T orElse(T other) {
        return other;
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return new None<>();
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <R> R match(Function<T, R> ifPresent, Supplier<R> ifEmpty) {
        return ifEmpty.get();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Option<T> or(Supplier<Option<T>> other) {
        return other.get();
    }
}
class public class None<T> implements Option<T> {
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
    public T orElse(T other) {
        return other;
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return new None<>();
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <R> R match(Function<T, R> ifPresent, Supplier<R> ifEmpty) {
        return ifEmpty.get();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Option<T> or(Supplier<Option<T>> other) {
        return other.get();
    }
}{

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
    public T orElse(T other) {
        return other;
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return new None<>();
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <R> R match(Function<T, R> ifPresent, Supplier<R> ifEmpty) {
        return ifEmpty.get();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Option<T> or(Supplier<Option<T>> other) {
        return other.get();
    }
}
