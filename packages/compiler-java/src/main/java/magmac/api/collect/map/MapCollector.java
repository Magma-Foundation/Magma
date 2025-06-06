package magmac.api.collect.map;

import magmac.api.Tuple2;
import magmac.api.iter.collect.Collector;

/**
 * Collector for building a {@link Map} from key-value pairs.
 */

public record MapCollector<K, V>() implements Collector<Tuple2<K, V>, Map<K, V>> {
    @Override
    public Map<K, V> createInitial() {
        return Maps.empty();
    }

    @Override
    public Map<K, V> fold(Map<K, V> current, Tuple2<K, V> element) {
        return current.put(element.left(), element.right());
    }
}
