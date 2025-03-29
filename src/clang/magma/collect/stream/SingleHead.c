package magma.collect.stream;class package magma.collect.stream;{package magma.collect.stream;

import magma.option.None;class import magma.option.None;{

import magma.option.None;
import magma.option.Option;class import magma.option.Option;{
import magma.option.Option;
import magma.option.Some;class import magma.option.Some;{
import magma.option.Some;

public class SingleHead<T> implements Head<T> {
    private final T value;
    private boolean retrieved = false;

    public SingleHead(T value) {
        this.value = value;
    }

    @Override
    public Option<T> next() {
        if (retrieved) return new None<>();

        retrieved = true;
        return new Some<>(value);
    }
}
class public class SingleHead<T> implements Head<T> {
    private final T value;
    private boolean retrieved = false;

    public SingleHead(T value) {
        this.value = value;
    }

    @Override
    public Option<T> next() {
        if (retrieved) return new None<>();

        retrieved = true;
        return new Some<>(value);
    }
}{

public class SingleHead<T> implements Head<T> {
    private final T value;
    private boolean retrieved = false;

    public SingleHead(T value) {
        this.value = value;
    }

    @Override
    public Option<T> next() {
        if (retrieved) return new None<>();

        retrieved = true;
        return new Some<>(value);
    }
}
