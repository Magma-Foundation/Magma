package com.meti.api.collect;

import com.meti.api.iterator.Iterator;
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
}
