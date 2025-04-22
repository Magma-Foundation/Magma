package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    private static final List<String> methods = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = Paths.get(".", "main.c");
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang", "-o", "main.exe", "main.c")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        String output = compileAll(input, Main::compileRootSegment);
        String joinedMethods = String.join("", methods);
        return output + joinedMethods;
    }

    private static String compileAll(String input, Function<String, String> compileRootSegment) {
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
            output.append(compileRootSegment.apply(segment));
        }

        return output.toString();
    }

    private static String compileRootSegment(String input) {
        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String beforeKeyword = input.substring(0, classIndex);
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    String outputContent = compileAll(inputContent, Main::compileClassSegment);
                    return generatePlaceholder(beforeKeyword) + "struct " + name + " {" + outputContent + "\n}\n";
                }
            }
        }
        return generatePlaceholder(input);
    }

    private static String compileClassSegment(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String definition = input.substring(0, paramStart).strip();
            String withParams = input.substring(paramStart + "(".length());
            int paramEnd = withParams.indexOf(")".toString());
            if (paramEnd >= 0) {
                String params = withParams.substring(0, paramEnd).strip();
                String withBraces = withParams.substring(paramEnd + ")".length()).strip();

                String generated = generatePlaceholder(definition) + "(" + generatePlaceholder(params) + generatePlaceholder(withBraces);
                methods.add(generated);
                return "";
            }
        }

        return generatePlaceholder(input);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
