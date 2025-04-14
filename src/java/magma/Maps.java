package magma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Maps {
    private record JavaMap<K, V>(Map<K, V> map) implements Main.Map_<K, V> {
        public JavaMap() {
            this(new HashMap<>());
        }

        @Override
        public Main.Map_<K, V> with(K key, V value) {
            this.map.put(key, value);
            return this;
        }

        @Override
        public boolean hasKey(K key) {
            return this.map.containsKey(key);
        }

        @Override
        public V get(K key) {
            return this.map.get(key);
        }

        @Override
        public V getOrDefault(K key, V other) {
            return this.map.getOrDefault(key, other);
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public Main.Iterator<Main.Tuple<K, V>> iterEntries() {
            ArrayList<Map.Entry<K, V>> entries = new ArrayList<>(this.map.entrySet());
            return new Main.Iterator<>(new Main.RangeHead(entries.size()))
                    .map(entries::get)
                    .map(entry -> new Main.Tuple<>(entry.getKey(), entry.getValue()));
        }
    }

    public static <K, V> Main.Map_<K, V> empty() {
        return new JavaMap<>();
    }
}
