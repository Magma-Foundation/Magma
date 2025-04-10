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
        return compileStatements(input, Main::compileRootSegment).or(() -> generatePlaceholder(input)).orElse("");
    }

    private static Optional<String> compileStatements(String input, Function<String, Optional<String>> compiler) {
        return compileAndMerge(compiler, divide(input), Main::mergeStatements);
    }

    private static Optional<String> compileAndMerge(Function<String, Optional<String>> compiler, List<String> segments, BiFunction<StringBuilder, String, StringBuilder> mergeStatements) {
        Optional<StringBuilder> maybeOutput = Optional.of(new StringBuilder());
        for (String segment : segments) {
            maybeOutput = maybeOutput.flatMap(output -> compiler.apply(segment).map(output::append));
        }

        return maybeOutput.map(StringBuilder::toString);
    }

    private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    }

    private static ArrayList<String> divide(String input) {
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

        String stripped = input.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String content = right.substring(0, right.length() - ";".length());
                String joined = String.join("/", content.split(Pattern.quote(".")));
                return Optional.of("#include \"./" + joined + "\"\n");
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
                    return compileStatements(inputContent, Main::compileClassMember).map(outputContent -> {
                        return newModifiers + " struct " + name + " {\n};\n" + outputContent;
                    });
                }
            }
        }

        return generatePlaceholder(input);
    }

    private static String compileModifiers(String substring) {
        String[] oldModifiers = substring.strip().split(" ");
        return Arrays.stream(oldModifiers)
                .map(String::strip)
                .map(Main::generatePlaceholder)
                .flatMap(Optional::stream)
                .collect(Collectors.joining(" "));
    }

    private static Optional<String> compileClassMember(String input) {
        return compileMethod(input).or(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return Optional.empty();

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + "(".length());

        return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) return Optional.empty();

            String[] paramsArrays = withParams.substring(0, paramEnd).strip().split(Pattern.quote(","));
            List<String> params = Arrays.stream(paramsArrays).map(String::strip).toList();
            String body = withParams.substring(paramEnd + ")".length()).strip();

            return compileAndMerge(Main::compileDefinition, params, Main::mergeValues).flatMap(outputParams -> {
                if (body.startsWith("{") && body.endsWith("}")) {
                    String inputContent = body.substring("{".length(), body.length() - "}".length());
                    return compileStatements(inputContent, Main::compileStatement).flatMap(outputContent -> {
                        return Optional.of(outputDefinition + "(" + outputParams + ") {" + outputContent + "\n}");
                    });
                }

                return Optional.empty();
            });
        });
    }

    private static Optional<String> compileStatement(String input) {
        return generatePlaceholder(input);
    }

    private static StringBuilder mergeValues(StringBuilder stringBuilder, String s) {
        if (stringBuilder.isEmpty()) {
            return stringBuilder.append(s);
        }

        return stringBuilder.append(", ").append(s);
    }

    private static Optional<String> compileDefinition(String definition) {
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = definition.substring(0, nameSeparator).strip();
            String name = definition.substring(nameSeparator + " ".length()).strip();

            int typeSeparator = beforeName.lastIndexOf(" ");
            if (typeSeparator >= 0) {
                String modifiers = beforeName.substring(0, typeSeparator);
                String inputType = beforeName.substring(typeSeparator + " ".length());
                return compileType(inputType).flatMap(outputType -> {
                    return Optional.of(generateDefinition(compileModifiers(modifiers) + " ", outputType, name));
                });
            } else {
                return compileType(beforeName).flatMap(outputType -> {
                    return Optional.of(generateDefinition("", outputType, name));
                });
            }
        }
        return Optional.empty();
    }

    private static String generateDefinition(String modifiers, String type, String name) {
        return modifiers + type + " " + name;
    }

    private static Optional<String> compileType(String input) {
        if (input.equals("void")) return Optional.of("void");

        if (input.endsWith("[]")) {
            return compileType(input.substring(0, input.length() - "[]".length()))
                    .map(value -> value + "*");
        }

        if (isSymbol(input)) {
            return Optional.of("struct " + input);
        }

        return generatePlaceholder(input);
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) continue;
            return false;
        }
        return true;
    }

    private static Optional<String> generatePlaceholder(String input) {
        return Optional.of("/* " + input + " */");
    }
}
