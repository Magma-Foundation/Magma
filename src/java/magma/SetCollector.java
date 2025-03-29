package magma;

import magma.collect.Collector;
import magma.collect.Set_;

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
