package com.meti.java;

import com.meti.core.Option;
import com.meti.core.Tuple;
import com.meti.iterate.Iterator;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Map<K, V> {
    Map<K, V> insertOrMap(K key, Function<V, V> onPresent, Supplier<V> onAbsent);

    Iterator<Tuple<K, V>> iter();

    Map<K, V> mapValue(K first, Function<V, V> mapper);

    boolean hasKey(K key);

    Option<V> applyOptionally(K key);

    V apply(Key<K> key);

    Map<K, V> insert(K key, V value);

    Iterator<Key<K>> keys();

    Map<K, V> insertAll(Map<K, V> other);

    Iterator<Tuple<K, V>> entries();
}
