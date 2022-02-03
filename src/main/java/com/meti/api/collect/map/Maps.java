package com.meti.api.collect.map;

import com.meti.api.collect.java.JavaMap;
import com.meti.api.collect.java.Map;

import java.util.HashMap;

public class Maps {
    public static <A, B> Map<A, B> empty() {
        return new JavaMap<>();
    }

    public static <A, B> java.util.Map<A, B> toNativeMap(Map<A, B> current) {
        var toReturn = new HashMap<A, B>();
        for (A key : current.keys()) {
            current.applyOptionally(key).ifPresent(value -> toReturn.put(key, value));
        }
        return toReturn;
    }
}
