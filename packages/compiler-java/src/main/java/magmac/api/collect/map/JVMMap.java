package magmac.api.collect.map;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.collect.list.JVMList;
import magmac.api.iter.Iter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * {@link Map} implementation backed by a Java {@link java.util.Map}.
 */

public
record JVMMap<K, V>(java.util.Map<K, V> map) implements Map<K, V> {
    @Override
    public V getOrDefault(K key, V other) {
        return this.map.getOrDefault(key, other);
    }

    @Override
    public Iter<Tuple2<K, V>> iter() {
        return new JVMList<>(new ArrayList<>(this.map.entrySet()))
                .iter()
                .map((java.util.Map.Entry<K, V> entry) -> new Tuple2<>(entry.getKey(), entry.getValue()));
    }

    @Override
    public Map<K, V> put(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    @Override
    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public V get(K key) {
        return this.map.get(key);
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Map<K, V> mapOrPut(K key, Function<V, V> mapper, Supplier<V> supplier) {
        if (this.map.containsKey(key)) {
            this.map.put(key, mapper.apply(this.map.get(key)));
        }
        else {
            this.map.put(key, supplier.get());
        }

        return this;
    }

    @Override
    public Option<Tuple2<Map<K, V>, V>> removeByKey(K key) {
        if (!this.map.containsKey(key)) {
            return new None<>();
        }

        java.util.Map<K, V> copy = new HashMap<>(this.map);
        var removed = copy.remove(key);
        return new Some<>(new Tuple2<>(new JVMMap<>(copy), removed));
    }
}
