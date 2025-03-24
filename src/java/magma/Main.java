package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            String input = Files.readString(Paths.get(".", "src", "java", "magma", "Main.java"));
            String output = compile(input);
            Files.writeString(Paths.get(".", "out", "main.c"), output);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return input + "int main(){\n\treturn 0;\n}";
    }
}
