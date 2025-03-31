#include "Map_.h"
magma.collect.map.Map_<K, V> with(K key, V value);
magma.option.Option<V> find(K key);
magma.collect.stream.Stream<magma.option.Tuple<K, V>> stream();
magma.collect.map.Map_<K, V> ensure(K key, V(*whenPresent)(V), V(*whenEmpty)());
magma.collect.map.Map_<K, V> withAll(magma.collect.map.Map_<K, V> other);
magma.collect.map.boolean isEmpty();
magma.collect.map.Map_<K, V> remove(K key);
magma.collect.map.boolean containsKey(K key);
magma.collect.stream.Stream<V> streamValues();

