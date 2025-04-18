package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            ArrayList<String> segments = new ArrayList<>();
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                buffer.append(c);
                if(c == ';') {
                    segments.add(buffer.toString());
                    buffer = new StringBuilder();
                }
            }
            segments.add(buffer.toString());


            String collect = segments.stream()
                    .map(value -> "{\n\t\t\"value:\" : \"" + value + "\"\n\t},  ")
                    .collect(Collectors.joining());

            Path target = source.resolveSibling("Main.java.ast.json");
            Files.writeString(target, "{\n\t\"children\" : [" + collect + "]\n}");
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}