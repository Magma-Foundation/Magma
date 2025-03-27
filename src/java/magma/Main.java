package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        try (Stream<Path> stream = Files.walk(Paths.get(".", "src", "java"))) {
            Set<Path> sources = stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toSet());

            for (Path source : sources) {
                String input = Files.readString(source);

                ArrayList<String> segments = new ArrayList<>();
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    buffer.append(c);
                    if (c == ';') {
                        segments.add(buffer.toString());
                        buffer = new StringBuilder();
                    }
                }
                segments.add(buffer.toString());

                for (String segment : segments) {
                    throw new CompileException("Invalid root segment", segment);
                }
            }
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}
