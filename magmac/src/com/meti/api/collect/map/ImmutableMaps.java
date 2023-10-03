package com.meti.api.collect.map;

public class ImmutableMaps {
    private ImmutableMaps() {
    }

    public static <K, V> Map<K, V> empty() {
        return new ImmutableMap<>();
    }
}
