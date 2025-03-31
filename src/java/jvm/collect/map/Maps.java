package jvm.collect.map;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.collect.map.Map_;

import java.util.function.BiFunction;

public class Maps {
    public static <K, V> Map_<K, V> empty() {
        return new JavaMap<>();
    }

    public static <K, V> boolean equalsTo(
            Map_<K, V> first,
            Map_<K, V> second,
            BiFunction<V, V, Boolean> predicate
    ) {
        List_<K> joinedKeys = first.streamKeys()
                .concat(second.streamKeys())
                .foldWithInitial(Lists.empty(), List_::add);

        return joinedKeys.stream().allMatch(key -> hasSameEntry(first, second, predicate, key));
    }

    private static <K, V> boolean hasSameEntry(
            Map_<K, V> first,
            Map_<K, V> second,
            BiFunction<V, V, Boolean> predicate,
            K key
    ) {
        return first.find(key)
                .and(() -> second.find(key))
                .filter(tuple -> predicate.apply(tuple.left(), tuple.right()))
                .isPresent();
    }
}
