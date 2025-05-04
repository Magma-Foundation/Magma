/* package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main  */{};
/* 
    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var left = withoutEnd.substring(0, contentStart);
                var right = withoutEnd.substring(contentStart + "{".length());
                return generatePlaceholder(left) + "{};\n" + generatePlaceholder(right);
            }
        }

        return generatePlaceholder(stripped);
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
 */