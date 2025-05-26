package magmac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            Path sourceDirectory = Paths.get(".", "src", "java");
            Set<Path> sources = Files.walk(sourceDirectory).collect(Collectors.toSet());
            for (Path source : sources) {
                if (Files.isRegularFile(source)) {
                    String name = source.getFileName().toString();
                    if (name.endsWith(".java")) {
                        String input = Files.readString(source);
                    }
                }
            }

            Path target = Paths.get(".", "diagram.puml");
            Files.writeString(target, "@startuml\n@enduml\n");
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}
