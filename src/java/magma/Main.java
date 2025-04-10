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
    private interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(value);
        }
    }

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        readString(source)
                .match(input -> compileAndWrite(input, source), Optional::of)
                .ifPresent(Throwable::printStackTrace);
    }

    private static Optional<IOException> compileAndWrite(String input, Path source) {
        Path target = source.resolveSibling("main.c");
        String output = compile(input);
        return writeString(target, output);
    }

    private static Optional<IOException> writeString(Path target, String output) {
        try {
            Files.writeString(target, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    private static Result<String, IOException> readString(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static String compile(String input) {
        return compileStatements(input, Main::compileRootSegment).or(() -> generatePlaceholder(input)).orElse("");
    }

    private static Optional<String> compileStatements(String input, Function<String, Optional<String>> compiler) {
        return compileAndMerge(divide(input), compiler, Main::mergeStatements);
    }

    private static Optional<String> compileAndMerge(List<String> segments, Function<String, Optional<String>> compiler, BiFunction<StringBuilder, String, StringBuilder> mergeStatements) {
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

        Optional<String> maybeClass = compileToStruct(input, "class ");
        if (maybeClass.isPresent()) return maybeClass;

        return generatePlaceholder(input);
    }

    private static Optional<String> compileToStruct(String input, String infix) {
        int classIndex = input.indexOf(infix);
        if (classIndex < 0) return Optional.empty();

        String substring = input.substring(0, classIndex);

        String afterKeyword = input.substring(classIndex + infix.length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart >= 0) {
            String name = afterKeyword.substring(0, contentStart).strip();
            String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
            if (withEnd.endsWith("}")) {
                String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                return compileModifiers(substring).flatMap(newModifiers0 -> {
                    return compileStatements(inputContent, Main::compileClassMember).map(outputContent -> {
                        return newModifiers0 + " struct " + name + " {\n" +
                                outputContent +
                                "\n};\n";
                    });
                });
            }
        }
        return Optional.empty();
    }

    private static Optional<String> compileModifiers(String substring) {
        String[] oldModifiers = substring.strip().split(" ");
        List<String> list = Arrays.stream(oldModifiers)
                .map(String::strip)
                .filter(modifier -> !modifier.isEmpty())
                .toList();

        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list.stream()
                .map(Main::generatePlaceholder)
                .flatMap(Optional::stream)
                .collect(Collectors.joining(" ")));
    }

    private static Optional<String> compileClassMember(String input) {
        return compileToStruct(input, "interface ")
                .or(() -> compileMethod(input))
                .or(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return Optional.empty();

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + "(".length());

        return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) return Optional.empty();

            String substring = withParams.substring(0, paramEnd);
            return compileValues(substring, definition -> compileDefinition(definition).or(() -> generatePlaceholder(definition))).flatMap(outputParams -> {
                String header = outputDefinition + "(" + outputParams + ")";
                String body = withParams.substring(paramEnd + ")".length()).strip();
                if (body.startsWith("{") && body.endsWith("}")) {
                    String inputContent = body.substring("{".length(), body.length() - "}".length());
                    return compileStatements(inputContent, Main::compileStatement).flatMap(outputContent -> {
                        return Optional.of(header + " {" + outputContent + "\n}");
                    });
                }

                return Optional.of(header + ";");
            });
        });
    }

    private static Optional<String> compileValues(String input, Function<String, Optional<String>> compiler) {
        String[] paramsArrays = input.strip().split(Pattern.quote(","));
        List<String> params = Arrays.stream(paramsArrays).map(String::strip).toList();

        return compileValues(params, compiler);
    }

    private static Optional<String> compileValues(List<String> params, Function<String, Optional<String>> compoiler) {
        return compileAndMerge(params, compoiler, Main::mergeValues);
    }

    private static Optional<String> compileStatement(String input) {
        return generatePlaceholder(input);
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) return cache.append(element);
        return cache.append(", ").append(element);
    }

    private static Optional<String> compileDefinition(String definition) {
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = definition.substring(0, nameSeparator).strip();
            String name = definition.substring(nameSeparator + " ".length()).strip();

            int typeSeparator = beforeName.lastIndexOf(" ");
            if (typeSeparator >= 0) {
                String beforeType = beforeName.substring(0, typeSeparator).strip();

                String modifiers;
                Optional<String> typeParams;
                if (beforeType.endsWith(">")) {
                    String withoutEnd = beforeType.substring(0, beforeType.length() - ">".length());
                    int typeParamStart = withoutEnd.indexOf("<");
                    if (typeParamStart >= 0) {
                        modifiers = withoutEnd.substring(0, typeParamStart);
                        String substring = withoutEnd.substring(typeParamStart + 1);
                        typeParams = compileValues(substring, Main::compileTypeParam);
                    } else {
                        modifiers = beforeType;
                        typeParams = Optional.empty();
                    }
                } else {
                    modifiers = beforeType;
                    typeParams = Optional.empty();
                }

                String inputType = beforeName.substring(typeSeparator + " ".length());
                Optional<String> compiledModifiers = compileModifiers(modifiers.strip());
                return compileType(inputType).flatMap(outputType -> {
                    return Optional.of(generateDefinition(compiledModifiers, typeParams, outputType, name));
                });
            } else {
                return compileType(beforeName).flatMap(outputType -> {
                    return Optional.of(generateDefinition(Optional.empty(), Optional.empty(), outputType, name));
                });
            }
        }
        return Optional.empty();
    }

    private static Optional<String> compileTypeParam(String input) {
        if (isSymbol(input.strip())) return Optional.of(input);
        return generatePlaceholder(input);
    }

    private static String generateDefinition(Optional<String> maybeModifiers, Optional<String> maybeTypeParams, String type, String name) {
        String modifiersString = maybeModifiers.map(modifiers -> modifiers + " ").orElse("");
        String typeParamsString = maybeTypeParams.map(inner -> "<" + inner + "> ").orElse("");
        return modifiersString + typeParamsString + type + " " + name;
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
