package com.meti.api.collect;

import com.meti.api.iterator.Iterator;
import com.meti.api.option.Option;
import com.meti.compile.Tuple;

public interface Map<K, V> {
    Iterator<Tuple<K, V>> iter();

    Option<V> get(K key);

    Map<K, V> putAll(Map<K, V> text);

    boolean hasKey(K key);

    Map<K,V> put(K key, V value);
}
