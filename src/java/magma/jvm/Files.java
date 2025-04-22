package magma.jvm;

import java.io.IOException;
import java.nio.file.Path;

public class Files {
    public static String readString(Path path) throws IOException {
        return java.nio.file.Files.readString(path);
    }

    public static void writeString(Path path, String output) throws IOException {
        java.nio.file.Files.writeString(path, output);
    }
}
