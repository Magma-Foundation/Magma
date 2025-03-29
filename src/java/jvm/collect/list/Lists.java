package jvm.collect.list;

import magma.collect.list.List_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lists {
    public static <T> List<T> toNative(List_<T> list) {
        return list.stream().fold(new ArrayList<T>(), (current, element) -> {
            current.add(element);
            return current;
        });
    }

    public static <T> JavaList<T> empty() {
        return new JavaList<>();
    }

    public static <T> List_<T> fromNative(List<T> list) {
        return new JavaList<>(list);
    }

    public static <T> List_<T> fromArray(T[] array) {
        return fromNative(Arrays.asList(array));
    }

    public static <T> List_<T> of(T... elements) {
        return fromArray(elements);
    }
}
