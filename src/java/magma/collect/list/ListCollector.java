package magma.collect.list;

import jvm.collect.list.Lists;
import magma.collect.Collector;

public class ListCollector<T> implements Collector<T, List_<T>> {
    @Override
    public List_<T> createInitial() {
        return Lists.empty();
    }

    @Override
    public List_<T> fold(List_<T> tList, T t) {
        return tList.add(t);
    }
}
