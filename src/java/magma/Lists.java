package magma;

import java.util.ArrayList;
import java.util.Arrays;

class Lists {
    public record JavaList<T>(java.util.List<T> elements) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.List<T> addLast(T element) {
            this.elements.addLast(element);
            return this;
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.Iterator<>(new Main.RangeHead(this.elements.size())).map(this.elements::get);
        }

        @Override
        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        @Override
        public boolean contains(T element) {
            return this.elements.contains(element);
        }

        @Override
        public Main.Option<Integer> indexOf(T element) {
            var index = this.elements.indexOf(element);
            return index == -1 ? new Main.None<>() : new Main.Some<>(index);
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
        public Main.List<T> subList(int startInclusive, int endExclusive) {
            return new JavaList<>(this.elements.subList(startInclusive, endExclusive));
        }

        @Override
        public T last() {
            return this.elements.getLast();
        }

        @Override
        public Main.List<T> addAll(Main.List<T> elements) {
            return elements.iter().<Main.List<T>>fold(this, Main.List::addLast);
        }

        @Override
        public Main.List<T> removeLast() {
            return new JavaList<>(this.elements.subList(0, this.elements.size() - 1));
        }

        @Override
        public T removeAndGetLast() {
            return this.elements.removeLast();
        }
    }

    public static <T> Main.List<T> listEmpty() {
        return new JavaList<>();
    }

    public static <T> Main.List<T> listFromArray(T[] array) {
        return new JavaList<>(Arrays.asList(array));
    }

    public static <T> Main.List<T> listFrom(T... elements) {
        return new JavaList<>(Arrays.asList(elements));
    }
}
