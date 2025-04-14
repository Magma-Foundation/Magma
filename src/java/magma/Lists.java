package magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
