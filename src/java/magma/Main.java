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

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return compileAll(input, input1 -> compileRootSegment(input1, 0));
    }

    private static String compileAll(String input, Function<String, String> compiler) {
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
            else if (c == '}' && depth == 1) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
                depth--;
            }
            else {
                if (c == '{' || c == '(') {
                    depth++;
                }
                if (c == '}' || c == ')') {
                    depth--;
                }
            }
        }
        segments.add(buffer.toString());

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compiler.apply(segment));
        }
        return output.toString();
    }

    private static String compileRootSegment(String input, int depth) {
        String stripped = input.strip();
        String indent = "\n" + "\t".repeat(depth);
        if (stripped.endsWith("}")) {
            String withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            int contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                String beforeContent = withoutEnd.substring(0, contentStart);
                String afterContent = withoutEnd.substring(contentStart + "{".length());
                return format(generatePlaceholder(beforeContent) + "{" + compileAll(afterContent, input1 -> {
                    return compileRootSegment(input1, depth + 1);
                }) + indent + "}", depth, indent);
            }
        }

        return format(generatePlaceholder(stripped), depth, indent);
    }

    private static String format(String output, int depth, String indent) {
        if (depth == 0) {
            return output + "\n";
        } else {
            return indent + output;
        }
    }

    private static String generatePlaceholder(String stripped) {
        return "/*" + stripped + "*/";
    }
}
