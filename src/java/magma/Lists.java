package magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

class Lists {
    public record JavaList<T>(java.util.List<T> elements) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.List<T> addLast(T element) {
            var copy = new ArrayList<>(this.elements);
            copy.addLast(element);
            return new JavaList<>(copy);
        }

        @Override
        public Main.Iterator<T> iter() {
            return this.iterateWithIndices().map(Main.Tuple::right);
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
        public Main.Option<T> findLast() {
            if (this.isEmpty()) {
                return new Main.None<>();
            }
            else {
                return new Main.Some<>(this.elements.getLast());
            }
        }

        @Override
        public Main.List<T> addAll(Main.List<T> elements) {
            return elements.iter().<Main.List<T>>fold(this, Main.List::addLast);
        }

        @Override
        public Main.Option<Main.Tuple<T, Main.List<T>>> removeLast() {
            if (this.elements.isEmpty()) {
                return new Main.None<>();
            }

            var slice = new ArrayList<>(this.elements.subList(0, this.elements.size() - 1));
            var last = this.elements.removeLast();
            return new Main.Some<>(new Main.Tuple<>(last, new JavaList<>(slice)));
        }

        @Override
        public Main.List<T> mapLast(Function<T, T> mapper) {
            var copy = new ArrayList<>(this.elements);
            copy.set(copy.size() - 1, mapper.apply(copy.getLast()));
            return new JavaList<>(copy);
        }

        @Override
        public Main.Iterator<Main.Tuple<Integer, T>> iterateWithIndices() {
            return new Main.Iterator<>(new Main.RangeHead(this.elements.size())).map(index -> {
                var element = this.elements.get(index);
                return new Main.Tuple<>(index, element);
            });
        }

        @Override
        public Main.List<T> sort(BiFunction<T, T, Integer> comparator) {
            var copy = new ArrayList<>(this.elements);
            copy.sort(comparator::apply);
            return new JavaList<>(copy);
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
