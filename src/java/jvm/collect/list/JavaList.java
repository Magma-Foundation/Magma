package jvm.collect.list;

import magma.collect.list.List_;
import magma.collect.stream.Stream;
import magma.collect.stream.head.HeadedStream;
import magma.collect.stream.head.RangeHead;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.option.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

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

    @Override
    public List_<T> sort(BiFunction<T, T, Integer> comparator) {
        ArrayList<T> copy = new ArrayList<>(list);
        copy.sort(comparator::apply);
        return new JavaList<>(copy);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public Option<Tuple<T, List_<T>>> popFirst() {
        if (list.isEmpty()) return new None<>();

        T first = list.getFirst();
        List<T> elements = list.subList(1, list.size());

        return new Some<>(new Tuple<>(first, new JavaList<>(elements)));
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public List_<T> clear() {
        return new JavaList<>(new ArrayList<>());
    }

    @Override
    public Option<T> findLast() {
        return list.isEmpty() ? new None<>() : new Some<>(list.getLast());
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        list.forEach(consumer);
    }

    @Override
    public List_<T> mapLast(Function<T, T> mapper) {
        return findLast()
                .map(mapper)
                .map(this::setLast)
                .orElse(this);
    }

    private List_<T> setLast(T last) {
        return set(list.size() - 1, last);
    }

    private JavaList<T> set(int index, T last) {
        List<T> copy = new ArrayList<>(list);
        copy.set(index, last);
        return new JavaList<>(copy);
    }

    private List<T> toNativeCopy() {
        return new ArrayList<>(list);
    }
}
