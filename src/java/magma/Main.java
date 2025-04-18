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
            int depth = 0;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                buffer.append(c);
                if (c == ';' && depth == 0) {
                    segments.add(buffer.toString());
                    buffer = new StringBuilder();
                }
                else {
                    if (c == '{') {
                        depth++;
                    }
                    if (c == '}') {
                        depth--;
                    }
                }
            }
            segments.add(buffer.toString());


            String collect = segments.stream()
                    .map(value -> {
                        String stripped = value.strip();
                        String value1 = createJSONProperty(2, "value", "\"" + stripped + "\"");
                        return createJSONObject(1, value1) + ", ";
                    })
                    .collect(Collectors.joining());

            Path target = source.resolveSibling("Main.java.ast.json");
            String children = createJSONProperty(1, "children", "[" + collect + "]");
            Files.writeString(target, createJSONObject(0, children));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String createJSONObject(int depth, String content) {
        String indent = "\t".repeat(depth);
        return "{" + content + "\n" + indent + "}";
    }

    private static String createJSONProperty(int depth, String name, String value) {
        String indent = "\t".repeat(depth);
        return "\n" + indent + "\"" + name + "\" : " + value;
    }
}