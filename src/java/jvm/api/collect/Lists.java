package jvm.api.collect;

import jvm.api.io.JavaList;
import jvm.api.io.JavaListCollector;
import magma.api.collect.Collector;
import magma.api.collect.Equator;
import magma.api.collect.List_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lists {
    public static <T> List<T> toNative(List_<T> list) {
        return list.stream().foldWithInitial(new ArrayList<T>(), (current, element) -> {
            current.add(element);
            return current;
        });
    }

    public static <T> List_<T> fromNative(List<T> list) {
        return new JavaList<>(list);
    }

    public static <T> List_<T> of(T... elements) {
        return fromNative(Arrays.asList(elements));
    }

    public static <T> boolean equalsTo(List_<T> first, List_<T> second, Equator<T> equator) {
        if (first.size() != second.size()) return false;

        return first.stream().zip(second.stream()).allMatch(tuple -> equator.equalsTo(tuple.left(), tuple.right()));
    }

    public static <T> List_<T> empty() {
        return new JavaList<>();
    }

    public static <T> Collector<T, List_<T>> collectToList() {
        return new JavaListCollector<T>();
    }

    public static List_<Character> fromString(String value) {
        return Streams.streamString(value).collect(collectToList());
    }
}
