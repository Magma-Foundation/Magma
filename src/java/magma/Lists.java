package magma;

import java.util.ArrayList;
import java.util.Arrays;

public class Lists {
    record JavaList<T>(java.util.List<T> list) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.HeadedIterator<Integer>(new Main.RangeHead(this.list.size())).map(this.list::get);
        }

        @Override
        public void add(T element) {
            this.list.add(element);
        }
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<T>();
    }

    public static <T> Main.List<T> of(T... elements) {
        return new JavaList<>(Arrays.asList(elements));
    }
}
