#ifndef magma_collect_map_Map_
#define magma_collect_map_Map_
#include "../../../magma/collect/stream/Stream.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Tuple.h"
magma.collect.map.Map_<K, V> with(K key, V value);
magma.option.Option<V> find(K key);
magma.collect.stream.Stream<magma.option.Tuple<K, V>> stream();
magma.collect.map.Map_<K, V> ensure(K key, V(*whenPresent)(V), V(*whenEmpty)());
magma.collect.map.Map_<K, V> withAll(magma.collect.map.Map_<K, V> other);
int isEmpty();
magma.collect.map.Map_<K, V> remove(K key);
int containsKey(K key);
magma.collect.stream.Stream<V> streamValues();
// expand magma.collect.map.Map_<K, V>
// expand magma.option.Option<V>
// expand magma.collect.stream.Stream<magma.option.Tuple<K, V>>
// expand magma.option.Tuple<K, V>
// expand magma.option.Tuple<K, V>
// expand magma.collect.map.Map_<K, V>
// expand magma.collect.map.Map_<K, V>
// expand magma.collect.map.Map_<K, V>
// expand magma.collect.map.Map_<K, V>
// expand magma.collect.stream.Stream<V>
#endif

