package magma;

import java.util.ArrayList;
import java.util.Arrays;

public class Lists {
    record JavaList<T>(java.util.List<T> elements) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.HeadedIterator<Integer>(new Main.RangeHead(this.elements.size())).map(this.elements::get);
        }

        @Override
        public Main.List<T> add(T element) {
            this.elements.add(element);
            return this;
        }

        @Override
        public T get(int index) {
            return this.elements.get(index);
        }
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<T>();
    }

    public static <T> Main.List<T> of(T... elements) {
        return new JavaList<>(Arrays.asList(elements));
    }
}
