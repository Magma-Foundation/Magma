package magmac.api.collect.map;

import magmac.api.Tuple2;
import magmac.api.iter.Iter;
import magmac.api.iter.Iters;

import java.util.ArrayList;

public record JVMMap<K, V>(java.util.Map<K, V> internal) implements Map<K, V> {
    @Override
    public V getOrDefault(K key, V other) {
        return this.internal.getOrDefault(key, other);
    }

    @Override
    public Iter<Tuple2<K, V>> iterEntries() {
        return Iters.fromList(new ArrayList<>(this.internal.entrySet()))
                .map(entry -> new Tuple2<>(entry.getKey(), entry.getValue()));
    }

    @Override
    public Map<K, V> put(K key, V value) {
        this.internal.put(key, value);
        return this;
    }

    @Override
    public boolean containsKey(K key) {
        return this.internal.containsKey(key);
    }

    @Override
    public V get(K key) {
        return this.internal.get(key);
    }

    @Override
    public boolean isEmpty() {
        return this.internal.isEmpty();
    }
}
