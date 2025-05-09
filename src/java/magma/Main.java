package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Main {
    void main() {
        var parent = Paths.get(".", "src", "java", "magma");
        var source = parent.resolve("main.mgs");
        var target = parent.resolve("main.c");
        try {
            var input = Files.readString(source);
            Files.writeString(target, input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}