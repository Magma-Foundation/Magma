package jvm.api.collect.list;

import magma.annotate.Actual;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Query;
import magma.api.collect.head.HeadedQuery;
import magma.api.collect.head.RangeHead;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.ArrayList;
import java.util.function.BiFunction;

@Actual
public record JVMList<T>(java.util.List<T> list) implements List<T> {
    @Override
    public List<T> addLast(final T element) {
        final var copy = new ArrayList<T>(this.list);
        copy.add(element);
        return new JVMList<>(copy);
    }

    @Override
    public Query<T> query() {
        return this.queryWithIndices().map((Tuple2<Integer, T> integerTTuple) -> integerTTuple.right());
    }

    @Override
    public int size() {
        return this.list.size();
    }

    private T getLast() {
        return this.list.getLast();
    }

    private T get(final int index) {
        return this.list.get(index);
    }

    @Override
    public Query<Tuple2<Integer, T>> queryWithIndices() {
        final var query = new HeadedQuery<Integer>(new RangeHead(this.list.size()));
        return query.map((Integer index) -> new Tuple2Impl<Integer, T>(index, this.list.get(index)));
    }

    @Override
    public List<T> addAll(final List<T> others) {
        return others.query().foldWithInitial(this.toList(), (List<T> list1, T element) -> list1.addLast(element));
    }

    private List<T> toList() {
        return this;
    }

    @Override
    public boolean contains(final T element, final BiFunction<T, T, Boolean> equator) {
        return this.list.contains(element);
    }

    @Override
    public Query<T> queryReversed() {
        final var query = new HeadedQuery<Integer>(new RangeHead(this.list.size()));
        return query.map((Integer index) -> this.list.size() - index - 1).map((Integer index1) -> this.list.get(index1));
    }

    @Override
    public List<T> addFirst(final T element) {
        final var copy = new ArrayList<T>();
        copy.addFirst(element);
        copy.addAll(this.list);
        return new JVMList<>(copy);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean equalsTo(final List<T> other, final BiFunction<T, T, Boolean> equator) {
        return this.equals(other);
    }

    @Override
    public List<T> removeValue(final T element, final BiFunction<T, T, Boolean> equator) {
        final var copy = new ArrayList<T>(this.list);
        copy.remove(element);
        return new JVMList<>(copy);
    }

    @Override
    public Option<List<T>> removeLast() {
        if (this.list.isEmpty()) {
            return new None<>();
        }

        final var copy = new ArrayList<T>(this.list);
        copy.removeLast();
        return new Some<>(new JVMList<>(copy));
    }

    @Override
    public List<T> sort(final BiFunction<T, T, Integer> sorter) {
        final var copy = new ArrayList<T>(this.list);
        copy.sort(sorter::apply);
        return new JVMList<>(copy);
    }

    @Override
    public Option<List<T>> subList(final int startInclusive, final int endExclusive) {
        return new Some<List<T>>(new JVMList<T>(this.list.subList(startInclusive, endExclusive)));
    }

    @Override
    public Option<T> findLast() {
        return new Some<T>(this.getLast());
    }

    @Override
    public Option<T> findFirst() {
        if (this.list.isEmpty()) {
            return new None<>();
        }
        return new Some<T>(this.list.getFirst());
    }

    @Override
    public Option<T> find(final int index) {
        return new Some<T>(this.get(index));
    }
}
