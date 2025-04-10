package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
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
        return compileAll(input, Main::compileRootSegment);
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

        String stripped = input.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String content = right.substring(0, right.length() - ";".length());
                String joined = String.join("/", content.split(Pattern.quote(".")));
                return "#include \"./" + joined + "\"\n";
            }
        }

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String substring = input.substring(0, classIndex);
            String newModifiers = compileModifiers(substring);

            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    String outputContent = compileAll(inputContent, Main::compileClassMember);
                    return newModifiers + " struct " + name + " {\n};\n" + outputContent;
                }
            }
        }

        return generatePlaceholder(input);
    }

    private static String compileModifiers(String substring) {
        String[] oldModifiers = substring.strip().split(" ");
        String newModifiers = Arrays.stream(oldModifiers)
                .map(String::strip)
                .map(Main::generatePlaceholder)
                .collect(Collectors.joining(" "));
        return newModifiers;
    }

    private static String compileClassMember(String input) {
        return compileMethod(input).orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String inputDefinition = input.substring(0, paramStart).strip();
            String withParams = input.substring(paramStart + "(".length());

            return compileDefinition(inputDefinition).map(outputDefinition -> {
                return outputDefinition + "(" + generatePlaceholder(withParams);
            });
        }
        return Optional.empty();
    }

    private static Optional<String> compileDefinition(String definition) {
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = definition.substring(0, nameSeparator).strip();
            String name = definition.substring(nameSeparator + " ".length()).strip();

            int typeSeparator = beforeName.lastIndexOf(" ");
            if (typeSeparator >= 0) {
                String modifiers = beforeName.substring(0, typeSeparator);
                String type = beforeName.substring(typeSeparator + " ".length());
                return Optional.of(compileModifiers(modifiers) + " " + compileType(type) + " " + name);
            }
        }
        return Optional.empty();
    }

    private static String compileType(String input) {
        if (input.equals("void")) return "void";

        return generatePlaceholder(input);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
