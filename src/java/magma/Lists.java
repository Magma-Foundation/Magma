package magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

class Lists {
    private record JavaList<T>(List<T> elements) implements Main.List_<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public T get(int index) {
            return this.elements.get(index);
        }

        @Override
        public int size() {
            return this.elements.size();
        }

        @Override
        public Main.List_<T> add(T element) {
            this.elements.add(element);
            return this;
        }
    }

    public static <T> Main.List_<T> fromNativeList(List<T> list) {
        return new JavaList<>(list);
    }

    public static <T> List<T> toNativeList(Main.List_<T> list) {
        ArrayList<T> copy = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            copy.add(list.get(i));
        }
        return copy;
    }

    public static <T> Main.List_<T> emptyList() {
        return new JavaList<>();
    }

    public static <T> Main.List_<T> of(T... elements) {
        return new JavaList<>(Arrays.asList(elements));
    }

    public static <T> boolean contains(Main.List_<T> list, T element, BiFunction<T, T, Boolean> equator) {
        for (int i = 0; i < list.size(); i++) {
            T found = list.get(i);
            if (equator.apply(found, element)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean equalsTo(Main.List_<T> first, Main.List_<T> second, BiFunction<T, T, Boolean> equator) {
        if (first.size() != second.size()) {
            return false;
        }

        for (int i = 0; i < first.size(); i++) {
            T firstElement = first.get(i);
            T secondElement = second.get(i);

            if (!equator.apply(firstElement, secondElement)) {
                return false;
            }
        }

        return true;
    }
}
