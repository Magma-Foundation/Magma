package jvm.collect.set;

import magma.collect.set.Set_;

import java.util.Set;

public class Sets {
    public static <T> Set_<T> fromNative(Set<T> set) {
        return new JavaSet<>(set);
    }

    public static <T> Set_<T> empty() {
        return new JavaSet<>();
    }
}
