package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static class CompileException extends Exception {
        public CompileException(String message, String context) {
            super(message + ": " + context);
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            String output = compile(input);

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, output + "int main(){\n\treturn 0;\n}");
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) throws CompileException {
        throw new CompileException("Invalid root", input);
    }
}
