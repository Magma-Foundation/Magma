package magma.jvm;

import magma.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class IO {
    public record ExceptionalIOError(Exception error) implements Main.IOError {
        @Override
        public String display() {
            StringWriter writer = new StringWriter();
            this.error.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    public static Main.Result<String, Main.IOError> readString(Path path) {
        try {
            return new Main.Ok<>(java.nio.file.Files.readString(path));
        } catch (IOException e) {
            return new Main.Err<>(new ExceptionalIOError(e));
        }
    }

    public static Optional<Main.IOError> writeString(Path path, String output) {
        try {
            java.nio.file.Files.writeString(path, output);
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
}
