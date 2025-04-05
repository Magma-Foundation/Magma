package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            Path target = Paths.get(".", "src", "java", "magma", "main.c");
            Files.writeString(target, "int main(){\n\treturn 0;\n}\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
