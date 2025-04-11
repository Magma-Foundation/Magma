package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Impl {
    private record ExceptionalIOError(IOException exception) implements Main.IOError {
        @Override
        public String display() {
            StringWriter writer = new StringWriter();
            this.exception.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    private record PathImpl(Path path) implements Main.Path_ {
        @Override
        public Main.Path_ resolveSibling(String sibling) {
            return new PathImpl(this.path.resolveSibling(sibling));
        }

        @Override
        public Main.List_<String> listNames() {
            ArrayList<String> names = new ArrayList<>();
            for (Path path1 : this.path) {
                names.add(path1.toString());
            }
            return new JavaList<>(names);
        }
    }

    private record JavaList<T>(List<T> elements) implements Main.List_<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.List_<T> add(T element) {
            this.elements.add(element);
            return this;
        }

        @Override
        public void addAll(Main.List_<T> elements) {
            elements.iter().forEach(this::add);
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.HeadedIterator<>(new Main.RangeHead(this.elements.size())).map(this.elements::get);
        }

        @Override
        public Main.Option<Main.Tuple<T, Main.List_<T>>> popFirst() {
            if (this.elements.isEmpty()) {
                return new Main.None<>();
            }

            T first = this.elements.getFirst();
            List<T> slice = this.elements.subList(1, this.elements.size());
            return new Main.Some<>(new Main.Tuple<>(first, new JavaList<>(slice)));
        }

        @Override
        public T pop() {
            return this.elements.removeFirst();
        }

        @Override
        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        @Override
        public T peek() {
            return this.elements.getFirst();
        }

        @Override
        public int size() {
            return this.elements.size();
        }

        @Override
        public Main.List_<T> slice(int startInclusive, int endExclusive) {
            return new JavaList<>(this.elements.subList(startInclusive, endExclusive));
        }

        @Override
        public T get(int index) {
            return this.elements.get(index);
        }
    }

    static Main.Option<Main.IOError> writeString(Main.Path_ target, String output) {
        try {
            java.nio.file.Files.writeString(unwrap(target), output);
            return new Main.None<>();
        } catch (IOException e) {
            return new Main.Some<>(new ExceptionalIOError(e));
        }
    }

    private static Path unwrap(Main.Path_ path) {
        return path.listNames()
                .popFirst()
                .map(list -> list.right().iter().fold(Paths.get(list.left()), Path::resolve))
                .orElse(Paths.get("."));
    }

    static Main.Result<String, Main.IOError> readString(Main.Path_ source) {
        try {
            return new Main.Ok<>(java.nio.file.Files.readString(unwrap(source)));
        } catch (IOException e) {
            return new Main.Err<>(new ExceptionalIOError(e));
        }
    }

    public static Main.Path_ get(String first, String... elements) {
        return new PathImpl(Paths.get(first, elements));
    }

    public static <T> Main.List_<T> emptyList() {
        return new JavaList<>();
    }

    public static <T> Main.List_<T> listOf(T... elements) {
        return new JavaList<>(Arrays.asList(elements));
    }

    public static <T> Main.List_<T> listFromNative(List<T> list) {
        return new JavaList<>(list);
    }

    public static <T> boolean contains(
            Main.List_<T> list,
            T element,
            BiFunction<T, T, Boolean> equator
    ) {
        return list.iter().anyMatch(child -> equator.apply(child, element));
    }

    public static <T> boolean equalsList(Main.List_<T> first, Main.List_<T> second, BiFunction<T, T, Boolean> equator) {
        if (first.size() != second.size()) {
            return false;
        }

        return new Main.HeadedIterator<>(new Main.RangeHead(first.size())).allMatch(index -> {
            return equator.apply(first.get(index), second.get(index));
        });
    }
}
