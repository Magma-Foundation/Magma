package magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        private T pop1() {
            return this.list.removeFirst();
        }

        @Override
        public T last() {
            return this.list.getLast();
        }

        @Override
        public Main.List<T> setLast(T element) {
            this.list.set(this.list.size() - 1, element);
            return this;
        }

        @Override
        public Main.Option<Main.Tuple<T, Main.List<T>>> pop() {
            if (this.list.isEmpty()) {
                return new Main.None<>();
            }

            T first = this.list.getFirst();
            List<T> slice = this.list.subList(1, this.list.size());
            return new Main.Some<>(new Main.Tuple<>(first, new JavaList<>(slice)));
        }
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<>();
    }

    public static <T> Main.List<T> of(T... elements) {
        return new JavaList<>(new ArrayList<>(Arrays.asList(elements)));
    }
}
