package jvm.api.collect;

import magma.api.collect.Map_;

public class Maps {
    public static <K, V> Map_<K, V> empty() {
        return new JavaMap<>();
    }
}
