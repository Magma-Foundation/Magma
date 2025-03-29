package jvm.collect.list;

import magma.collect.list.List_;
import magma.collect.stream.HeadedStream;
import magma.collect.stream.RangeHead;
import magma.collect.stream.Stream;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;

import java.util.ArrayList;
import java.util.List;

public record JavaList<T>(List<T> list) implements List_<T> {
    public JavaList() {
        this(new ArrayList<>());
    }

    @Override
    public Stream<T> stream() {
        return new HeadedStream<>(new RangeHead(list.size()))
                .map(list::get);
    }

    @Override
    public List_<T> add(T element) {
        List<T> copy = toNativeCopy();
        copy.add(element);
        return new JavaList<>(copy);
    }

    @Override
    public List_<T> addAll(List_<T> others) {
        List<T> copy = toNativeCopy();
        copy.addAll(Lists.toNative(others));
        return new JavaList<>(copy);
    }

    @Override
    public Option<T> findFirst() {
        if (list.isEmpty()) return new None<>();
        return new Some<>(list.getFirst());
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List_<T> subList(int start, int end) {
        return new JavaList<>(list.subList(start, end));
    }

    @Override
    public boolean equalsTo(List_<T> other) {
        return list.equals(Lists.toNative(other));
    }

    private List<T> toNativeCopy() {
        return new ArrayList<>(list);
    }
}
