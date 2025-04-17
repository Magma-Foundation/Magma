package magma;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Lists {
    record JavaList<T>(java.util.List<T> list) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.Stream<T> stream() {
            return new Main.HeadedStream<>(new Main.RangeHead(list.size())).map(list::get);
        }

        @Override
        public Main.List<T> add(T element) {
            this.list.add(element);
            return this;
        }
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<>();
    }
}
