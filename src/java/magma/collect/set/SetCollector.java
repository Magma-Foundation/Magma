package magma.collect.set;

import jvm.collect.set.Sets;
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
