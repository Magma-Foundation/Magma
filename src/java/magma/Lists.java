package magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

class Lists {
    public static <T> Main.List<T> listEmpty() {
        return new JavaList<>();
    }

    public static <T> Main.List<T> listFrom(T... elements) {
        return new JavaList<>(Arrays.asList(elements));
    }

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
        public Main.Iterator<T> iterate() {
            return this.iterateWithIndices().map(Main.Tuple::right);
        }

        @Override
        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        @Override
        public boolean contains(T element, BiFunction<T, T, Boolean> equator) {
            for (var maybeElement : this.elements) {
                if (equator.apply(element, maybeElement)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public Main.Option<T> get(int index) {
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
            return elements.iterate().<Main.List<T>>fold(this, Main.List::addLast);
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

        @Override
        public Main.Iterator<T> iterateReversed() {
            return new Main.Iterator<>(new Main.RangeHead(this.elements.size()))
                    .map(index -> this.elements.size() - index - 1)
                    .map(this.elements::get);
        }

        @Override
        public boolean equalsTo(Main.List<T> other, BiFunction<T, T, Boolean> equator) {
            if (this.elements.size() != other.size()) {
                return false;
            }

            return this.iterateWithIndices().map(tuple -> this.createElementPair(tuple, other))
                    .flatMap(Main.Iterators::fromOption)
                    .map(tuple -> equator.apply(tuple.left(), tuple.right()))
                    .fold(true, (aBoolean, aBoolean2) -> aBoolean && aBoolean2);
        }

        @Override
        public Main.Option<Main.Tuple<Main.List<T>, T>> popLast() {
            if (this.elements.isEmpty()) {
                return new Main.None<>();
            }

            var slice = this.elements.subList(0, this.elements.size() - 1);
            var last = this.elements.getLast();

            return new Main.Some<>(new Main.Tuple<>(new JavaList<>(new ArrayList<>(slice)), last));
        }

        private Main.Option<Main.Tuple<T, T>> createElementPair(Main.Tuple<Integer, T> tuple, Main.List<T> other) {
            var index = tuple.left();
            var thisElement = tuple.right();

            return other.get(index).map(otherElement -> new Main.Tuple<>(thisElement, otherElement));
        }
    }
}
