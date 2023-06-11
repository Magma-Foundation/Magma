package com.meti;

public class Iterables {
    public static <T> Iterable.Collector<NativeSet<T>, T> toSet() {
        return new Iterable.Collector<>() {
            @Override
            public NativeSet<T> initial() {
                return NativeSet.empty();
            }

            @Override
            public NativeSet<T> fold(NativeSet<T> current, T element) {
                return current.add(element);
            }
        };
    }
}
