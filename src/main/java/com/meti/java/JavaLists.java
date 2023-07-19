package com.meti.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaLists {
    @SafeVarargs
    public static <T> com.meti.java.List<T> ofList(T... child) {
        return new JavaList<>(List.of(child));
    }

    @SafeVarargs
    public static <T> NonEmptyList<T> ofNonEmptyList(T first, T... child) {
        var list = new ArrayList<T>();
        list.add(first);
        list.addAll(Arrays.asList(child));
        return new NonEmptyJavaList<>(list);
    }

}
