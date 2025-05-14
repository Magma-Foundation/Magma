package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main() {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var target = source.resolveSibling("main.ts");
        try {
            var input = Files.readString(source);
            Files.writeString(target, placeholder(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String placeholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }
}