package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            String input = Files.readString(Paths.get(".", "src", "java", "magma", "Main.java"));
            throw new CompileException("Invalid root", input);
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static class CompileException extends Exception {
        public CompileException(String message, String context) {
            super(message + ": " + context);
        }
    }
}
