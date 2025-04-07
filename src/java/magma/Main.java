package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

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
        Optional<String> s = compileStatements(input, Main::compileRootSegment);
        return s.orElse("");
    }

    private static Optional<String> compileStatements(String input, Function<String, Optional<String>> compiler) {
        return compileAll(divideStatements(input), compiler, Main::mergeStatements);
    }

    private static Optional<String> compileAll(
            List<String> segments,
            Function<String, Optional<String>> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        Optional<StringBuilder> maybeOutput = Optional.of(new StringBuilder());
        for (String segment : segments) {
            maybeOutput = maybeOutput.flatMap(output -> {
                return compiler.apply(segment).map(str -> merger.apply(output, str));
            });
        }

        return maybeOutput.map(StringBuilder::toString);
    }

    private static StringBuilder mergeStatements(StringBuilder output, String str) {
        return output.append(str);
    }

    private static ArrayList<String> divideStatements(String input) {
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
        return segments;
    }

    private static Optional<String> compileRootSegment(String input) {
        if (input.startsWith("package ")) return Optional.of("");
        if (input.strip().startsWith("import ")) return Optional.of("#include <temp.h>\n");
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
                    return compileStatements(inputContent, Main::compileClassSegment).map(outputContent -> {
                        return generatePlaceholder(modifiers) + "struct " + name + " {\n};\n" + outputContent;
                    });
                }
            }
        }

        return Optional.of(invalidate("root segment", input));
    }

    private static String invalidate(String type, String input) {
        System.err.println("Invalid " + type + ": " + input);
        return generatePlaceholder(input);
    }

    private static Optional<String> compileClassSegment(String input) {
        Optional<String> maybeMethod = compileMethod(input);
        if (maybeMethod.isPresent()) return maybeMethod;

        return Optional.of(invalidate("class segment", input));
    }

    private static Optional<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return Optional.empty();

        String header = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + "(".length());
        int i = withParams.indexOf(")");
        if (i >= 0) {
            String paramString = withParams.substring(0, i);
            List<String> inputParams = Arrays.asList(paramString.split(Pattern.quote(",")));

            return compileAll(inputParams, Main::compileDefinition, Main::mergeValues)
                    .flatMap(outputParams -> {
                        return compileDefinition(header).map(definition -> {
                            return definition + "(" +
                                    outputParams +
                                    "){\n}\n";
                        });
                    });
        } else {
            return Optional.empty();
        }
    }

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(", ").append(element);
    }

    private static String compileStatement(String input) {
        return invalidate("statement", input);
    }

    private static Optional<String> compileDefinition(String header) {
        int nameSeparator = header.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = header.substring(0, nameSeparator);

            int space = beforeName.lastIndexOf(" ");

            String modifiers;
            String type;
            if (space >= 0) {
                modifiers = generatePlaceholder(beforeName.substring(0, space));
                type = beforeName.substring(space + 1);
            } else {
                modifiers = "";
                type = beforeName;
            }

            String name = header.substring(nameSeparator + " ".length());
            return Optional.of(modifiers + type + " " + name);
        }
        return Optional.of(generatePlaceholder(header));
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}