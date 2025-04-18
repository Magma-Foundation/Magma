package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class JavaFiles {
    static Main.Result<String, IOException> readString(Path source) {
        try {
            return new Main.Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Main.Err<>(e);
        }
    }

    static Optional<IOException> writeString(String output, Path target) {
        try {
            Files.writeString(target, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }
}
