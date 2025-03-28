package jvm.api.io;

import jvm.api.collect.HeadedStream;
import magma.api.collect.Collector;
import magma.api.collect.List_;
import magma.api.collect.RangeHead;
import magma.api.collect.Stream;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.ArrayList;
import java.util.List;

public record JavaList<T>(List<T> list) implements List_<T> {
    public JavaList() {
        this(new ArrayList<>());
    }

    public static <T> Collector<T, List_<T>> collector() {
        return new JavaListCollector<>();
    }

    @Override
    public Stream<T> stream() {
        return new HeadedStream<>(new RangeHead(list.size()))
                .map(list::get);
    }

    @Override
    public Option<List_<T>> subList(int fromInclusive, int toExclusive) {
        if (isValidRange(fromInclusive, toExclusive))
            return new Some<>(new JavaList<>(list.subList(fromInclusive, toExclusive)));

        return new None<>();
    }

    @Override
    public List_<T> add(T element) {
        List<T> copy = new ArrayList<>(list);
        copy.add(element);
        return new JavaList<>(copy);
    }

    @Override
    public int size() {
        return list.size();
    }

    private boolean isValidRange(int fromInclusive, int toExclusive) {
        return isRangeWithinBounds(fromInclusive, toExclusive) && fromInclusive <= toExclusive;
    }

    private boolean isRangeWithinBounds(int fromInclusive, int toExclusive) {
        return isIndexWithinBounds(fromInclusive) && isIndexWithinBounds(toExclusive);
    }

    private boolean isIndexWithinBounds(int index) {
        return index >= 0 && index <= list.size();
    }
}
