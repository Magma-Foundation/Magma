package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NativeList<T> {
    private final List<T> values;

    public NativeList(List<T> values) {
        this.values = values;
    }

    public static <T> NativeList<T> empty() {
        return new NativeList<>(new ArrayList<>());
    }

    public NativeList<T> add(T element) {
        var copy = new ArrayList<>(this.values);
        copy.add(element);
        return new NativeList<>(copy);
    }

    public T[] toArray(Function<Integer, T[]> supplier) {
        var array = supplier.apply(this.values.size());
        for (int i = 0; i < this.values.size(); i++) {
            array[i] = this.values.get(i);
        }
        return array;
    }
}
