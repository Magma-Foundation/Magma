package magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

class Lists {
    public record JavaList<T>(java.util.List<T> elements) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.List<T> add(T element) {
            this.elements.add(element);
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
        public Optional<Integer> indexOf(T element) {
            var index = this.elements.indexOf(element);
            return index == -1 ? Optional.empty() : Optional.of(index);
        }

        @Override
        public T get(int index) {
            return this.elements.get(index);
        }
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<>();
    }

    public static <T> Main.List<T> fromArray(T[] array) {
        return new JavaList<>(Arrays.asList(array));
    }
}
