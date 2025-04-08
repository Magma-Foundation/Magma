package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
    private static class CompileException extends Exception {
        public CompileException(String message, String context) {
            super(message + ": " + context);
        }
    }

    private static final class Node {
        private final Map<String, String> strings;

        private Node() {
            this(Collections.emptyMap());
        }

        private Node(Map<String, String> strings) {
            this.strings = strings;
        }

        private Node withString(String propertyKey, String propertyValue) {
            HashMap<String, String> copy = new HashMap<>(strings);
            copy.put(propertyKey, propertyValue);
            return new Node(copy);
        }

        private Optional<String> findString(String propertyKey) {
            return Optional.ofNullable(strings.get(propertyKey));
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            String output = compile(input);

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, output + "int main(){\n\treturn 0;\n}");
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) throws CompileException {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments.add(buffer.toString());

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compileRootSegment(segment));
        }

        return output.toString();
    }

    private static String compileRootSegment(String input) throws CompileException {
        if (input.startsWith("package ")) return "";
        if (input.strip().startsWith("import ")) return "#include \"temp.h\"\n";

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                return generateStruct(new Node().withString("name", name));
            }
        }

        throw new CompileException("Invalid root", input);
    }

    private static String generateStruct(Node node) {
        return "struct " + node.findString("name").orElse("") + " {\n};\n";
    }
}
