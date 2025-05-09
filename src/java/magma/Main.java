package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Main {
    void main() {
        var source = Paths.get(".", "src", "java", "magma", "main.mgs");
        var target = source.resolveSibling("main.c");
        try {
            var input = Files.readString(source);
            Files.writeString(target, input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}