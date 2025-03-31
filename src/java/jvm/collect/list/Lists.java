package jvm.collect.list;

import magma.collect.list.List_;
import magma.collect.stream.head.HeadedStream;
import magma.collect.stream.head.RangeHead;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Lists {
    public static <T> List<T> toNative(List_<T> list) {
        return list.stream().foldWithInitial(new ArrayList<T>(), (current, element) -> {
            current.add(element);
            return current;
        });
    }

    public static <T> List_<T> empty() {
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

    public static <T> boolean equalsTo(List_<T> first, List_<T> second, BiFunction<T, T, Boolean> equator) {
        if (first.size() != second.size()) return false;

        return new HeadedStream<>(new RangeHead(first.size()))
                .allMatch(index -> equator.apply(first.get(index), second.get(index)));
    }
}
