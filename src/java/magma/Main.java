package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("Main.c");
            Files.writeString(target, "/* " + input + " */");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
