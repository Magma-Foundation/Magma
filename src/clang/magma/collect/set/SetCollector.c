package magma.collect.set;class package magma.collect.set;{package magma.collect.set;

import jvm.collect.set.Sets;class import jvm.collect.set.Sets;{

import jvm.collect.set.Sets;
import magma.collect.Collector;class import magma.collect.Collector;{
import magma.collect.Collector;

public class SetCollector<T> implements Collector<T, Set_<T>> {
    @Override
    public Set_<T> createInitial() {
        return Sets.empty();
    }

    @Override
    public Set_<T> fold(Set_<T> tSet, T t) {
        return tSet.add(t);
    }
}
class public class SetCollector<T> implements Collector<T, Set_<T>> {
    @Override
    public Set_<T> createInitial() {
        return Sets.empty();
    }

    @Override
    public Set_<T> fold(Set_<T> tSet, T t) {
        return tSet.add(t);
    }
}{

public class SetCollector<T> implements Collector<T, Set_<T>> {
    @Override
    public Set_<T> createInitial() {
        return Sets.empty();
    }

    @Override
    public Set_<T> fold(Set_<T> tSet, T t) {
        return tSet.add(t);
    }
}
