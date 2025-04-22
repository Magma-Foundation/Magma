package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "main.mgs");
            String input = Files.readString(source);

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, input);

            new ProcessBuilder("clang.exe", "-o", "main.exe", "./src/java/magma/main.c")
                    .inheritIO()
                    .start()
                    .wait();

            new ProcessBuilder("main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
