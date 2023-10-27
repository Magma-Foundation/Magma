package com.meti.api.collect;

import com.meti.api.iterator.Iterator;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Tuple;

import java.util.ArrayList;

public class JavaMap<K, V> implements Map<K, V> {
    private final java.util.Map<K, V> inner;

    public JavaMap(java.util.Map<K, V> inner) {
        this.inner = inner;
    }

    @Override
    public Iterator<Tuple<K, V>> iter() {
        return new NativeListIterator<>(new ArrayList<>(inner.entrySet()))
                .map(entry -> new Tuple<>(entry.getKey(), entry.getValue()));
    }

    @Override
    public Option<V> get(K key) {
        if (inner.containsKey(key)) {
            return Some.apply(inner.get(key));
        } else {
            return None.apply();
        }
    }
}
