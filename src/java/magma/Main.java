package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main() {
        try {
            var root = Paths.get(".", "src", "java", "magma");
            var input = Files.readString(root.resolve("main.java"));

            var target = root.resolve("main.c");
            Files.writeString(target, generatePlaceholder(input) + "\nint main(){\n\treturn 0;\n}\n");

            new ProcessBuilder("clang.exe", target.toAbsolutePath().toString(), "-o", "main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/* ", "content-start")
                .replace("*/", "content-end");

        return "/* " + replaced + " */";
    }
}
