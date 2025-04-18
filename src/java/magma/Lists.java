package magma;

import java.util.ArrayList;

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
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<T>();
    }
}
