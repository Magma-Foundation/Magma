package magma;

import java.util.ArrayList;

public class Lists {
    record JavaList<T>(java.util.List<T> list) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public void add(T element) {
            this.list.add(element);
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.Iterator<>(new Main.RangeHead(this.list.size())).map(this.list::get);
        }
    }

    public static <T> Main.List<T> emptyList() {
        return new JavaList<>();
    }
}
