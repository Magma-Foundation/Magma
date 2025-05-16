package magma.api.collect;

import magma.annotate.Actual;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.query.HeadedQuery;
import magma.api.collect.query.Query;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.ArrayList;

@Actual
public record JVMList<T>(java.util.List<T> list) implements List<T> {
    @Override
    public List<T> addLast(T element) {
        this.list.add(element);
        return this;
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

    private T getFirst() {
        return this.list.getFirst();
    }

    private T get(int index) {
        return this.list.get(index);
    }

    @Override
    public Query<Tuple2<Integer, T>> queryWithIndices() {
        var query = new HeadedQuery<Integer>(new RangeHead(this.list.size()));
        return query.map((Integer index) -> new Tuple2Impl<Integer, T>(index, this.list.get(index)));
    }

    @Override
    public List<T> addAll(List<T> others) {
        return others.query().foldWithInitial(this.toList(), (List<T> list1, T element) -> list1.addLast(element));
    }

    private List<T> toList() {
        return this;
    }

    @Override
    public boolean contains(T element) {
        return this.list.contains(element);
    }

    @Override
    public Query<T> queryReversed() {
        var query = new HeadedQuery<Integer>(new RangeHead(this.list.size()));
        return query.map((Integer index) -> this.list.size() - index - 1).map((Integer index1) -> this.list.get(index1));
    }

    @Override
    public List<T> addFirst(T element) {
        var copy = new ArrayList<T>();
        copy.addFirst(element);
        copy.addAll(this.list);
        return new JVMList<>(copy);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean equalsTo(List<T> other) {
        return this.equals(other);
    }

    @Override
    public List<T> removeValue(T element) {
        var copy = new ArrayList<T>(this.list);
        copy.remove(element);
        return new JVMList<>(copy);
    }

    @Override
    public Option<List<T>> removeLast() {
        if (this.list.isEmpty()) {
            return new None<>();
        }

        var copy = new ArrayList<T>(this.list);
        copy.removeLast();
        return new Some<>(new JVMList<>(copy));
    }

    @Override
    public Option<List<T>> subList(int startInclusive, int endExclusive) {
        return new Some<List<T>>(new JVMList<T>(this.list.subList(startInclusive, endExclusive)));
    }

    @Override
    public Option<T> findLast() {
        return new Some<T>(this.getLast());
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
