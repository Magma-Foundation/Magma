package jvm.api.collect.list;

import magma.annotate.Actual;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Iter;
import magma.api.collect.head.HeadedIter;
import magma.api.collect.head.RangeHead;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.ArrayList;

@Actual
public record JVMList<T>(java.util.List<T> list) implements List<T> {
    @Override
    public List<T> addLast(T element) {
        var copy = new ArrayList<T>(this.list);
        copy.add(element);
        return new JVMList<T>(copy);
    }

    @Override
    public Iter<T> iter() {
        return this.iterWithIndices().map((Tuple2<Integer, T> integerTTuple) -> {
            return integerTTuple.right();
        });
    }

    @Override
    public int size() {
        return this.list.size();
    }

    private T getFirst() {
        return this.list.getFirst();
    }

    private T get(int index) {
        return this.list.get(index);
    }

    @Override
    public Iter<Tuple2<Integer, T>> iterWithIndices() {
        var query = new HeadedIter<Integer>(new RangeHead(this.list.size()));
        return query.map((Integer index) -> {
            return new Tuple2Impl<Integer, T>(index, this.list.get(index));
        });
    }

    @Override
    public List<T> addAll(Iterable<T> others) {
        return others.iter().foldWithInitial(this.toList(), (List<T> list1, T element) -> {
            return list1.addLast(element);
        });
    }

    private List<T> toList() {
        return this;
    }

    @Override
    public boolean contains(T element) {
        return this.list.contains(element);
    }

    @Override
    public Iter<T> iterReversed() {
        var query = new HeadedIter<Integer>(new RangeHead(this.list.size()));
        return query.map((Integer index) -> {
            return this.list.size() - index - 1;
        }).map((Integer index1) -> {
            return this.list.get(index1);
        });
    }

    @Override
    public List<T> addFirst(T element) {
        var copy = new ArrayList<T>();
        copy.addFirst(element);
        copy.addAll(this.list);
        return new JVMList<T>(copy);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public List<T> removeValue(T element) {
        var copy = new ArrayList<T>(this.list);
        copy.remove(element);
        return new JVMList<T>(copy);
    }

    @Override
    public Option<List<T>> removeLast() {
        if (this.list.isEmpty()) {
            return new None<List<T>>();
        }

        var copy = new ArrayList<T>(this.list);
        copy.removeLast();
        return new Some<List<T>>(new JVMList<T>(copy));
    }

    @Override
    public Option<List<T>> subList(int startInclusive, int endExclusive) {
        return new Some<List<T>>(new JVMList<T>(this.list.subList(startInclusive, endExclusive)));
    }

    @Override
    public Option<T> findLast() {
        if (this.list.isEmpty()) {
            return new None<T>();
        }
        return new Some<T>(this.list.getLast());
    }

    @Override
    public Option<T> findFirst() {
        return new Some<T>(this.getFirst());
    }

    @Override
    public Option<T> find(int index) {
        return new Some<T>(this.get(index));
    }
}
