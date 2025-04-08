package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            Files.writeString(Paths.get(".", "src", "java", "magma", "main.c"), "int main(){\n\treturn 0;\n}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
