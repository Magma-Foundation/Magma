package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            buffer = buffer.append(c);

            if (c == ';') {
                var copy = new ArrayList<String>(segments);
                copy.add(buffer.toString());
                segments = copy;
                buffer = new StringBuilder();
            }
        }
        var copy = new ArrayList<String>(segments);
        copy.add(buffer.toString());

        var output = new StringBuilder();
        for (var segment : copy) {
            output.append(generatePlaceholder(segment));
        }

        return output.toString();
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
