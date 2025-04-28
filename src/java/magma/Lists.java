package magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

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
            return this.iterWithIndices().map(Main.Tuple::right);
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
        public Main.Option<T> find(int index) {
            if (index < this.elements.size()) {
                return new Main.Some<>(this.elements.get(index));
            }
            else {
                return new Main.None<>();
            }
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

        @Override
        public Main.List<T> mapLast(Function<T, T> mapper) {
            this.elements.set(this.elements.size() - 1, mapper.apply(this.elements.getLast()));
            return this;
        }

        @Override
        public Main.Iterator<Main.Tuple<Integer, T>> iterWithIndices() {
            return new Main.Iterator<>(new Main.RangeHead(this.elements.size())).map(index -> {
                var element = this.elements.get(index);
                return new Main.Tuple<>(index, element);
            });
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
