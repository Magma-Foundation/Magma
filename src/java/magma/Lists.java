package magma;

import java.util.ArrayList;
import java.util.Arrays;

class Lists {
    private record JavaList<T>(java.util.List<T> list) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.HeadedIterator<>(new Main.RangeHead(this.list.size()))
                    .map(this.list::get);
        }

        @Override
        public Main.List<T> add(T element) {
            this.list.add(element);
            return this;
        }

        @Override
        public T get(int index) {
            return this.list.get(index);
        }

        @Override
        public boolean contains(T element) {
            return this.list.contains(element);
        }
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<T>();
    }

    public static <T> Main.List<T> of(T... elements) {
        return new JavaList<>(new ArrayList<>(Arrays.asList(elements)));
    }
}
