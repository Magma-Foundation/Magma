package magma.jvm;

import magma.Main;
import magma.Main.IOError;
import magma.Main.None;
import magma.Main.Option;
import magma.Main.Some;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StandardLibrary {
    public record ExceptionalIOError(Exception error) implements IOError {
        @Override
        public String display() {
            StringWriter writer = new StringWriter();
            this.error.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    private record JVMPath(Path path) implements Main.Path {
        public static Path unwrap(Main.Path path) {
            return path.iter()
                    .foldWithMapper(Paths::get, Path::resolve)
                    .orElse(Paths.get("."));
        }

        @Override
        public Main.Iterator<String> iter() {
            ArrayList<String> segments = new ArrayList<>();
            for (int i = 0; i < this.path.getNameCount(); i++) {
                segments.add(this.path.getName(i).toString());
            }
            return new JavaList<>(segments).iter();
        }

        @Override
        public Main.Path resolveSibling(String sibling) {
            return new JVMPath(this.path.resolveSibling(sibling));
        }

        @Override
        public String asString() {
            return this.path.toString();
        }
    }

    public record JavaList<T>(List<T> list) implements Main.List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public Main.Iterator<T> iter() {
            return new Main.HeadedIterator<>(new Main.RangeHead(this.list.size())).map(this.list::get);
        }

        @Override
        public void add(T element) {
            this.list.add(element);
        }

        @Override
        public int size() {
            return this.list.size();
        }

        @Override
        public Main.List<T> subList(int startInclusive, int endExclusive) {
            return new JavaList<>(this.list.subList(startInclusive, endExclusive));
        }

        @Override
        public void addAll(Main.List<T> others) {
            this.list.addAll(unwrap(others));
        }
    }

    public static Main.Result<String, IOError> readString(Main.Path path) {
        try {
            return new Main.Ok<>(java.nio.file.Files.readString(JVMPath.unwrap(path)));
        } catch (IOException e) {
            return new Main.Err<>(new ExceptionalIOError(e));
        }
    }

    public static Option<IOError> writeString(Main.Path path, String output) {
        try {
            java.nio.file.Files.writeString(JVMPath.unwrap(path), output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new ExceptionalIOError(e));
        }
    }

    public static Option<IOError> execute(Main.List<String> command) {
        try {
            new ProcessBuilder(unwrap(command))
                    .inheritIO()
                    .start()
                    .waitFor();
            return new None<>();
        } catch (InterruptedException | IOException e) {
            return new Some<>(new ExceptionalIOError(e));
        }
    }

    public static Main.Path getPath(String first, String... elements) {
        return new JVMPath(Paths.get(first, elements));
    }

    public static <T> Main.List<T> empty() {
        return new JavaList<>();
    }

    public static <T> Main.List<T> of(T... elements) {
        return new JavaList<>(Arrays.asList(elements));
    }

    public static <T> List<T> unwrap(Main.List<T> list) {
        return list.iter().<List<T>>foldWithInitial(new ArrayList<>(), (ts, t) -> {
            ts.add(t);
            return ts;
        });
    }
}
