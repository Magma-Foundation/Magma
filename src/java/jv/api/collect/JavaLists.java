package jv.api.collect;

import jv.api.io.JavaList;
import magma.api.collect.List_;

import java.util.ArrayList;
import java.util.List;

public class JavaLists {
    public static <T> List<T> toNative(List_<T> list) {
        return list.stream().fold(new ArrayList<T>(), (current, element) -> {
            current.add(element);
            return current;
        });
    }

    public static List_<String> fromNative(List<String> list) {
        return new JavaList<>(list);
    }
}
