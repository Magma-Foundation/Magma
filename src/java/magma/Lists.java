package magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

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
        public Main.List<T> setLast(T element) {
            this.list.set(this.list.size() - 1, element);
            return this;
        }

        @Override
        public Main.List<T> sort(BiFunction<T, T, Integer> comparator) {
            ArrayList<T> copy = new ArrayList<>(this.list);
            copy.sort(comparator::apply);
            return new JavaList<>(copy);
        }

        @Override
        public Main.List<T> mapLast(Function<T, T> mapper) {
            return this.last().map(mapper).map(this::setLast).orElse(this);
        }

        @Override
        public int size() {
            return this.list.size();
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

        @Override
        public Main.Option<T> last() {
            if (this.list.isEmpty()) {
                return new Main.None<>();
            }
            else {
                return new Main.Some<>(this.list.getLast());
            }
        }
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<>();
    }

    public static <T> Main.List<T> of(T... elements) {
        return new JavaList<>(new ArrayList<>(Arrays.asList(elements)));
    }
}
