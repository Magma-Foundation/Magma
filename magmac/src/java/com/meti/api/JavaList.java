package com.meti.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record JavaList<T>(List<T> list) {
    public JavaList() {
        this(Collections.emptyList());
    }

    public Stream<T> stream() {
        return Streams.fromNativeList(list);
    }

    public JavaList<T> add(T element) {
        var copy = new ArrayList<>(list);
        copy.add(element);
        return new JavaList<>(copy);
    }
}
