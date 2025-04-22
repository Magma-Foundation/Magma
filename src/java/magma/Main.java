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
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang.exe", "-o", "main.exe", "./src/java/magma/main.c")
                    .inheritIO()
                    .start()
                    .waitFor();

            new ProcessBuilder("main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        int paramStart = input.indexOf("(");
        String name = input.substring("def".length(), paramStart).strip();
        String withParams = input.substring(paramStart + "(".length()).strip();
        int paramEnd = withParams.indexOf(")");
        String afterParams = withParams.substring(paramEnd + ")".length());
        int arrow = afterParams.indexOf("=>");
        String stripped = afterParams.substring(0, arrow).strip();
        String body = afterParams.substring(arrow + "=>".length()).strip();

        String type = stripped.substring(1).strip();
        String outputType = type.equals("I32") ? "int" : type;

        String inner = body.substring(1, body.length() - 1).strip();
        return outputType + " " + name + "(){" +
                inner +
                "\n}\n";
    }
}
