/* package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, generatePlaceholder(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("<content-start>", "<content-start>")
                .replace("<content-end>", "<content-end>");

        return "<content-start> " + replaced + " <content-end>";
    }
}
 */