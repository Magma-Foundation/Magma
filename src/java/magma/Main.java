package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
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
                    .map(Main::compileRootSegment)
                    .collect(Collectors.joining(", "));
            String children = createJSONProperty(1, "children", "[" + collect + "]");

            Path target = source.resolveSibling("Main.java.ast.json");
            Files.writeString(target, createJSONObject(0, children));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compileRootSegment(String value) {
        String stripped = value.strip();
        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            int prefixSegment = withoutEnd.indexOf(" ");
            if (prefixSegment >= 0) {
                String left = withoutEnd.substring(0, prefixSegment);
                String right = withoutEnd.substring(prefixSegment + " ".length());
                return generateStrings(1, Map.of("type", left, "segments", right));
            }
        }

        return toValue(stripped);
    }

    private static String generateStrings(int depth, Map<String, String> strings) {
        String joined = strings.entrySet()
                .stream()
                .map(entry -> createStringProperty(depth + 1, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));

        return createJSONObject(depth, joined);
    }

    private static String toValue(String stripped) {
        return generateStrings(1, Map.of("value", stripped));
    }

    private static String createStringProperty(int depth, String name, String content) {
        return createJSONProperty(depth, name, "\"" + content + "\"");
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