package magma.collect.stream;class package magma.collect.stream;{package magma.collect.stream;

import magma.option.None;class import magma.option.None;{

import magma.option.None;
import magma.option.Option;class import magma.option.Option;{
import magma.option.Option;
import magma.option.Some;class import magma.option.Some;{
import magma.option.Some;

public class RangeHead implements Head<Integer> {
    private final int extent;
    private int counter = 0;

    public RangeHead(int extent) {
        this.extent = extent;
    }

    @Override
    public Option<Integer> next() {
        if (counter >= extent) return new None<>();

        int value = counter;
        counter++;
        return new Some<>(value);
    }
}
class public class RangeHead implements Head<Integer> {
    private final int extent;
    private int counter = 0;

    public RangeHead(int extent) {
        this.extent = extent;
    }

    @Override
    public Option<Integer> next() {
        if (counter >= extent) return new None<>();

        int value = counter;
        counter++;
        return new Some<>(value);
    }
}{

public class RangeHead implements Head<Integer> {
    private final int extent;
    private int counter = 0;

    public RangeHead(int extent) {
        this.extent = extent;
    }

    @Override
    public Option<Integer> next() {
        if (counter >= extent) return new None<>();

        int value = counter;
        counter++;
        return new Some<>(value);
    }
}
