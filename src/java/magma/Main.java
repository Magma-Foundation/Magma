package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n";

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        return compile(input, Main::compileRootSegment);
    }

    private static String compile(String input, Function<String, String> compiler) {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
                depth--;
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments.add(buffer.toString());

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compiler.apply(segment));
        }

        return output.toString();
    }

    private static String compileRootSegment(String input) {
        if (input.startsWith("package ")) return "";
        if (input.strip().startsWith("import ")) return "#include <temp.h>\n";
        int keywordIndex = input.indexOf("class ");
        if (keywordIndex >= 0) {
            String modifiers = input.substring(0, keywordIndex);
            String right = input.substring(keywordIndex + "class ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String name = right.substring(0, contentStart).strip();
                String body = right.substring(contentStart + "{".length()).strip();
                if (body.endsWith("}")) {
                    String inputContent = body.substring(0, body.length() - "}".length());
                    String outputContent = compile(inputContent, Main::compileClassSegment);
                    return generatePlaceholder(modifiers) + "struct " + name + " {\n};\n" + outputContent;
                }
            }
        }

        return invalidate("root segment", input);
    }

    private static String invalidate(String type, String input) {
        System.err.println("Invalid " + type + ": " + input);
        return generatePlaceholder(input);
    }

    private static String compileClassSegment(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String header = input.substring(0, paramStart).strip();
            int nameSeparator = header.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                String name = header.substring(nameSeparator + " ".length());
                return "void " + name + "(){\n}\n";
            }
        }

        return invalidate("class segment", input);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}