package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        try (Stream<Path> paths = Files.walk(Paths.get(".", "src", "java"))) {
            Set<Path> sources = paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toSet());

            for (Path source : sources) {
                String input = Files.readString(source);
                throw new CompileException("Invalid root", input);
            }
        } catch (CompileException | IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}
