package magma;

import magma.Main.List;

import java.util.ArrayList;
import java.util.Arrays;

public class Lists {
    record JavaList<T>(java.util.List<T> list) implements List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public List<T> addLast(T element) {
            this.list.add(element);
            return this;
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.Iterator<>(new Main.RangeHead(this.list.size())).map(this.list::get);
        }

        @Override
        public boolean hasElements() {
            return !this.list.isEmpty();
        }

        @Override
        public T removeFirst() {
            return this.list.removeFirst();
        }

        @Override
        public T get(int index) {
            return this.list.get(index);
        }

        @Override
        public boolean contains(T element) {
            return this.list.contains(element);
        }

        @Override
        public List<T> addFirst(T element) {
            this.list.addFirst(element);
            return this;
        }
    }

    public static <T> List<T> emptyList() {
        return new JavaList<>();
    }

    public static <T> List<T> of(T... elements) {
        return new JavaList<>(Arrays.asList(elements));
    }
}
