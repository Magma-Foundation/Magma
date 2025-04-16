package magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class Lists {
    private record JavaList<T>(List<T> elements) implements Main.List_<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.List_<T> add(T element) {
            this.elements.add(element);
            return this;
        }

        @Override
        public Main.List_<T> addAll(Main.List_<T> elements) {
            return elements.iter().<Main.List_<T>>fold(this, Main.List_::add);
        }

        @Override
        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        @Override
        public Main.List_<T> copy() {
            return new JavaList<>(new ArrayList<>(this.elements));
        }

        @Override
        public boolean contains(T element) {
            return this.elements.contains(element);
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.Iterator<>(new Main.RangeHead(this.elements.size()))
                    .map(this.elements::get);
        }

        @Override
        public int indexOf(T element) {
            return this.elements.indexOf(element);
        }

        @Override
        public T get(int index) {
            return this.elements.get(index);
        }
    }

    public static <T> Main.List_<T> empty() {
        return new JavaList<>();
    }

    public static <T> List<T> toNativeList(Main.List_<T> list) {
        return list.iter().fold(new ArrayList<T>(), (ts, t) -> {
            ts.add(t);
            return ts;
        });
    }

    public static <T> Main.List_<T> of(T... elements) {
        return new JavaList<>(Arrays.asList(elements));
    }

    public static <T> Main.List_<T> fromNativeCollection(Collection<T> collection) {
        return new JavaList<>(new ArrayList<>(collection));
    }
}
