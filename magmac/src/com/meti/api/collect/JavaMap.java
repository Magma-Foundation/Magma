package com.meti.api.collect;

public class JavaMap<K, V> implements Map<K, V> {
    private final java.util.Map<K, V> inner;

    public JavaMap(java.util.Map<K, V> inner) {
        this.inner = inner;
    }
}
