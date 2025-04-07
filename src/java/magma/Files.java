package magma;

import java.io.IOException;
import java.nio.file.Path;

public class Files {
    static Main.Option<IOError> writeString(Path target, String output) {
        try {
            java.nio.file.Files.writeString(target, output);
            return new Main.None<>();
        } catch (IOException e) {
            return new Main.Some<>(new ExceptionalIOError(e));
        }
    }

    static Main.Result<String, IOError> readString(Path source) {
        try {
            return new Main.Ok<>(java.nio.file.Files.readString(source));
        } catch (IOException e) {
            return new Main.Err<>(new ExceptionalIOError(e));
        }
    }
}
