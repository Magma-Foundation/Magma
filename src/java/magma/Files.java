package magma;

import java.io.IOException;
import java.nio.file.Path;

public class Files {
    static Main.Option<IOException> writeString(Path target, String output) {
        try {
            java.nio.file.Files.writeString(target, output);
            return new Main.None<>();
        } catch (IOException e) {
            return new Main.Some<>(e);
        }
    }

    static Main.Result<String, IOException> readString(Path source) {
        try {
            return new Main.Ok<>(java.nio.file.Files.readString(source));
        } catch (IOException e) {
            return new Main.Err<>(e);
        }
    }
}
