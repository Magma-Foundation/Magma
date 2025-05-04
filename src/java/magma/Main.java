package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, "/* " + input + " */");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
