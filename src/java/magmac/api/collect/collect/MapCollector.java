package magmac.api.collect.collect;

import magmac.api.Tuple2;

import java.util.HashMap;
import java.util.Map;

public record MapCollector<K, V>() implements Collector<Tuple2<K, V>, Map<K, V>> {
    @Override
    public Map<K, V> createInitial() {
        return new HashMap<>();
    }

    @Override
    public Map<K, V> fold(Map<K, V> current, Tuple2<K, V> element) {
        current.put(element.left(), element.right());
        return current;
    }
}
