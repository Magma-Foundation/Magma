package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Optional;

public class Impl {
    private record ExceptionalIOError(IOException exception) implements Main.IOError {
        @Override
        public String display() {
            StringWriter writer = new StringWriter();
            this.exception.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    static Optional<Main.IOError> writeString(Path target, String output) {
        try {
            java.nio.file.Files.writeString(target, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(new ExceptionalIOError(e));
        }
    }

    static Main.Result<String, Main.IOError> readString(Path source) {
        try {
            return new Main.Ok<>(java.nio.file.Files.readString(source));
        } catch (IOException e) {
            return new Main.Err<>(new ExceptionalIOError(e));
        }
    }
}
