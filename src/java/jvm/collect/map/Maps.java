package jvm.collect.map;

import magma.collect.map.Map_;

public class Maps {
    public static <K, V> Map_<K, V> empty() {
        return new JavaMap<>();
    }
}
