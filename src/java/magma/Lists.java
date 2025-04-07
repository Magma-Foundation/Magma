package magma;

import java.util.List;
import java.util.function.BiFunction;

class Lists {
    public static <T> Main.List_<T> empty() {
        return new JavaList<>();
    }

    public static <T> boolean contains(Main.List_<T> list, T element, BiFunction<T, T, Boolean> equator) {
        return list.stream().anyMatch(child -> equator.apply(element, child));
    }

    public static <T> boolean equalsTo(Main.List_<T> elements, Main.List_<T> other, BiFunction<T, T, Boolean> equator) {
        if (elements.size() != other.size()) return false;

        return new Main.HeadedStream<>(new Main.RangeHead(elements.size())).allMatch(index -> {
            T left = elements.apply(index).orElse(null);
            T right = other.apply(index).orElse(null);
            return equator.apply(left, right);
        });
    }

    public static <T> Main.Option<Integer> indexOf(Main.List_<T> list, T element, BiFunction<T, T, Boolean> equator) {
        return list.streamWithIndices()
                .filter(tuple -> equator.apply(tuple.right(), element))
                .next()
                .map(Main.Tuple::left);
    }

    public static <T> Main.List_<T> fromNative(List<T> list) {
        return new JavaList<>(list);
    }
}
