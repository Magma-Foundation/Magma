package magmac.api.collect.list;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.head.HeadedIter;
import magmac.api.head.RangeHead;
import magmac.api.iter.Iter;
import magmac.api.iter.collect.ListCollector;

import java.util.ArrayList;
import java.util.function.BiFunction;

public record JVMList<T>(java.util.List<T> elements) implements List<T> {
    public JVMList() {
        this(new ArrayList<>());
    }

    @Override
    public List<T> addLast(T element) {
        ArrayList<T> copy = new ArrayList<>(this.elements);
        copy.add(element);
        return new JVMList<>(copy);
    }

    @Override
    public Iter<T> iter() {
        return new HeadedIter<>(new RangeHead(this.elements.size())).map(this::get);
    }

    @Override
    public List<T> addAllLast(List<T> others) {
        return others.iter().fold(this.createInitial(), List::addLast);
    }

    private List<T> createInitial() {
        return this;
    }

    @Override
    public List<T> removeAll(List<T> others) {
        return this.iter()
                .filter((T value) -> !others.contains(value))
                .collect(new ListCollector<>());
    }

    @Override
    public T get(int index) {
        return this.elements.get(index);
    }

    @Override
    public List<T> sort(BiFunction<T, T, Integer> sorter) {
        ArrayList<T> copy = new ArrayList<>(this.elements);
        copy.sort(sorter::apply);
        return new JVMList<>(copy);
    }

    @Override
    public boolean contains(T element) {
        return this.elements.contains(element);
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public Option<Tuple2<List<T>, T>> popLast() {
        if (this.elements.isEmpty()) {
            return new None<>();
        }

        java.util.List<T> slice = this.elements.subList(0, this.elements.size() - 1);
        return new Some<>(new Tuple2<>(new JVMList<>(slice), this.elements.getLast()));
    }

    @Override
    public Option<Tuple2<T, List<T>>> popFirst() {
        if (this.elements.isEmpty()) {
            return new None<>();
        }

        java.util.List<T> slice = this.elements.subList(1, this.elements.size());
        return new Some<>(new Tuple2<>(this.elements.getFirst(), new JVMList<>(slice)));
    }

    @Override
    public List<T> addFirst(T element) {
        ArrayList<T> copy = new ArrayList<>();
        copy.add(element);
        copy.addAll(this.elements);
        return new JVMList<>(copy);
    }

    @Override
    public Option<T> findLast() {
        if (this.elements.isEmpty()) {
            return new None<>();
        }
        return new Some<>(this.elements.getLast());
    }
}
