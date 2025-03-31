package magma.collect.map;

import jvm.collect.map.Maps;
import magma.collect.stream.Collector;
import magma.option.Tuple;

public class MapCollector<K, V> implements Collector<Tuple<K, V>, Map_<K, V>> {
    @Override
    public Map_<K, V> createInitial() {
        return Maps.empty();
    }

    @Override
    public Map_<K, V> fold(Map_<K, V> kvMap, Tuple<K, V> kvTuple) {
        return kvMap.with(kvTuple.left(), kvTuple.right());
    }
}
