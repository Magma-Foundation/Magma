package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static class CompileException extends Exception {
        public CompileException(String message, String context) {
            super(message + ": " + context);
        }
    }

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("Main.c");
            Files.writeString(target, compile(input));
        } catch (IOException | CompileException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) throws CompileException {
        throw new CompileException("Invalid input", input);
    }
}
