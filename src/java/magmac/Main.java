package magmac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path sourceDirectory = Paths.get(".", "src", "java");
        try (Stream<Path> stream = Files.walk(sourceDirectory)) {
            Set<Path> sources = stream.collect(Collectors.toSet());
            StringBuilder output = new StringBuilder();
            for (Path source : sources) {
                if (!Files.isRegularFile(source)) {
                    continue;
                }
                String fileName = source.getFileName().toString();
                if (!fileName.endsWith(".java")) {
                    continue;
                }

                int fileSeparator = fileName.lastIndexOf('.');
                String name = fileName.substring(0, fileSeparator);

                output.append("class " + name + "\n");

                String input = Files.readString(source);
                List<String> segments = new ArrayList<>();
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    buffer.append(c);
                    if (';' == c) {
                        segments.add(buffer.toString());
                        buffer = new StringBuilder();
                    }
                }

                segments.add(buffer.toString());
                for (String segment : segments) {
                    String stripped = segment.strip();
                    if (stripped.endsWith(";")) {
                        String slice = stripped.substring(0, stripped.length() - ";".length());
                        if (slice.startsWith("import ")) {
                            String sliced = slice.substring("import ".length());
                            int separator = sliced.lastIndexOf('.');
                            String child = sliced.substring(separator + ".".length());
                            output.append(name + " --> " + child + "\n");
                        }
                    }
                }
            }

            Path target = Paths.get(".", "diagram.puml");
            Files.writeString(target, "@startuml\nskinparam linetype ortho\n" +
                    output +
                    "@enduml\n");
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}
