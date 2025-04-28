package magma;

import java.util.ArrayList;
import java.util.Arrays;

class Lists {
    public record JavaList<T>(java.util.List<T> list) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.List<T> add(T element) {
            this.list.add(element);
            return this;
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.Iterator<>(new Main.RangeHead(this.list.size())).map(this.list::get);
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public boolean contains(T element) {
            return this.list.contains(element);
        }
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<>();
    }

    public static <T> Main.List<T> fromArray(T[] array) {
        return new JavaList<>(Arrays.asList(array));
    }
}
