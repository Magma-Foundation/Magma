package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
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

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compileRootSegment(segment));
        }
        return output.toString();
    }

    private static String compileRootSegment(String segment) {
        String stripped = segment.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        }

        int classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            String modifiers = Arrays.stream(stripped.substring(0, classIndex).strip().split(" "))
                    .map(String::strip)
                    .collect(Collectors.joining(" "));

            String afterKeyword = stripped.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String className = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String content = withEnd.substring(0, withEnd.length() - "}".length());
                    return modifiers + " class " + className + " {" + generatePlaceholder(content) + "}";
                }
            }
        }

        return generatePlaceholder(stripped);
    }

    private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("/*", "<content-start>")
                .replace("*/", "<content-end>");

        return "/* " + replaced + " */";
    }
}
