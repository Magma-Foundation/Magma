package magma;

import java.util.HashMap;
import java.util.Map;

public record JavaMap<K, V>(Map<K, V> inner) implements Main.Map_<K, V> {
    public JavaMap() {
        this(new HashMap<>());
    }

    @Override
    public Main.Map_<K, V> with(K key, V value) {
        HashMap<K, V> copy = new HashMap<>(inner);
        copy.put(key, value);
        return new JavaMap<>(copy);
    }

    @Override
    public Main.Option<V> find(K key) {
        if (inner.containsKey(key)) {
            return new Main.Some<>(inner.get(key));
        } else {
            return new Main.None<>();
        }
    }
}
