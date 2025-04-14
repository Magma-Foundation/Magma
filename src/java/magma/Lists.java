package magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

        @Override
        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        @Override
        public Main.Tuple<T, Main.List_<T>> pop() {
            List<T> slice = this.elements.subList(1, this.elements.size());
            return new Main.Tuple<>(this.elements.getFirst(), new JavaList<>(slice));
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.HeadedIterator<>(new Main.RangeHead(this.elements.size())).map(this.elements::get);
        }

        @Override
        public Main.List_<T> addAll(Main.List_<T> elements) {
            return elements.iter().<Main.List_<T>>fold(this, Main.List_::add);
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
        return indexOf(list, element, equator).isPresent();
    }

    public static <T> Optional<Integer> indexOf(Main.List_<T> list, T element, BiFunction<T, T, Boolean> equator) {
        for (int i = 0; i < list.size(); i++) {
            T found = list.get(i);
            if (equator.apply(found, element)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
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
