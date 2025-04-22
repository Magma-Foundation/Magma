package magma.jvm;

import magma.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StandardLibrary {
    public record ExceptionalIOError(Exception error) implements Main.IOError {
        @Override
        public String display() {
            StringWriter writer = new StringWriter();
            this.error.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    private record JVMPath(Path path) implements Main.Path {
        public static Path unwrap(Main.Path path) {
            List<String> list = path.stream().toList();
            Path current = Paths.get(list.getFirst());
            for (String child : list) {
                current = current.resolve(child);
            }
            return current;
        }

        @Override
        public Stream<String> stream() {
            ArrayList<String> segments = new ArrayList<>();
            for (int i = 0; i < this.path.getNameCount(); i++) {
                segments.add(this.path.getName(i).toString());
            }
            return segments.stream();
        }

        @Override
        public Main.Path resolveSibling(String sibling) {
            return new JVMPath(this.path.resolveSibling(sibling));
        }

        @Override
        public String asString() {
            return path.toString();
        }
    }

    public static Main.Result<String, Main.IOError> readString(Main.Path path) {
        try {
            return new Main.Ok<>(java.nio.file.Files.readString(JVMPath.unwrap(path)));
        } catch (IOException e) {
            return new Main.Err<>(new ExceptionalIOError(e));
        }
    }

    public static Optional<Main.IOError> writeString(Main.Path path, String output) {
        try {
            java.nio.file.Files.writeString(JVMPath.unwrap(path), output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(new ExceptionalIOError(e));
        }
    }

    public static Optional<Main.IOError> execute(List<String> command) {
        try {
            new ProcessBuilder(command)
                    .inheritIO()
                    .start()
                    .waitFor();
            return Optional.empty();
        } catch (InterruptedException | IOException e) {
            return Optional.of(new ExceptionalIOError(e));
        }
    }

    public static Main.Path getPath(String first, String... elements) {
        return new JVMPath(Paths.get(first, elements));
    }
}
