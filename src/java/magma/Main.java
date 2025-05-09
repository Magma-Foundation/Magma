package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main() {
        try {
            var target = Paths.get(".", "src", "java", "magma", "main.c");
            Files.writeString(target, "int main(){\n\treturn 0;\n}\n");

            new ProcessBuilder("clang.exe", target.toAbsolutePath().toString(), "-o", "main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
