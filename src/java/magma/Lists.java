package magma;

import java.util.ArrayList;

public class Lists {
    record JavaList<T>(java.util.List<T> list) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.Stream<T> stream() {
            return new Main.HeadedStream<>(new Main.RangeHead(this.list.size())).map(this.list::get);
        }

        @Override
        public Main.List<T> add(T element) {
            this.list.add(element);
            return this;
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public T pop() {
            return this.list.removeFirst();
        }
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<>();
    }
}
