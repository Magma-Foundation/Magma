package com.meti.api.collect.map;

import com.meti.api.collect.java.JavaMap;

import java.util.HashMap;
import java.util.Map;

public class Maps {
    public static <A, B> Map<A, B> toNativeMap(JavaMap<A, B> current) {
        var toReturn = new HashMap<A, B>();
        for (A key : current.keys()) {
            current.applyOptionally(key).ifPresent(value -> toReturn.put(key, value));
        }
        return toReturn;
    }
}
