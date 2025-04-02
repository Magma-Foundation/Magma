package magma;

import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;
import magma.result.Results;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String string = Results.unwrap(compileRoot(input));
            Path target = source.resolveSibling("Main.c");
            Files.writeString(target, string);
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static Result<String, CompileException> compileRoot(String input) throws CompileException {
        return divideAndCompile(input, Main::compileRootSegment);
    }

    private static Result<String, CompileException> divideAndCompile(String input, Function<String, Result<String, CompileException>> compiler) {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;

        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            char c = queue.pop();
            buffer.append(c);

            if (c == '\'') {
                Character maybeSlash = queue.pop();
                buffer.append(maybeSlash);
                if (maybeSlash == '\\') {
                    buffer.append(queue.pop());
                }

                buffer.append(queue.pop());
                continue;
            }

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

        Result<StringBuilder, CompileException> output = new Ok<>(new StringBuilder());
        for (String segment : segments) {
            output = output.and(() -> compiler.apply(segment))
                    .mapValue(tuple -> tuple.left().append(tuple.right()));
        }

        return output.mapValue(StringBuilder::toString);
    }

    private static Result<String, CompileException> compileRootSegment(String input) {
        Result<String, CompileException> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isOk()) return maybeWhitespace;

        if (input.startsWith("package ")) return new Ok<>("");

        String stripped = input.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String content = right.substring(0, right.length() - ";".length());
                String[] segments = content.split(Pattern.quote("."));
                String joined = String.join("/", segments);
                return new Ok<>("#include \"" + joined + ".h\"\n");
            }
        }

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    return divideAndCompile(inputContent, Main::compileClassSegment).mapValue(outputContent -> {
                        return "struct " + name + " {\n};\n" + outputContent;
                    });
                }
            }
        }

        return invalidate(input, "root segment");
    }

    private static Err<String, CompileException> invalidate(String input, String type) {
        return new Err<>(new CompileException(input, "Invalid " + type));
    }

    private static Result<String, CompileException> compileClassSegment(String input) {
        Result<String, CompileException> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isOk()) return maybeWhitespace;

        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String header = input.substring(0, paramStart).strip();
            int separator = header.lastIndexOf(" ");
            if (separator >= 0) {
                String beforeName = header.substring(0, separator);

                Optional<Integer> maybeTypeSeparator = Optional.empty();
                int depth = 0;
                for (int i = beforeName.length() - 1; i >= 0; i--) {
                    char c = beforeName.charAt(i);
                    if (c == ' ' && depth == 0) {
                        maybeTypeSeparator = Optional.of(i);
                        break;
                    } else {
                        if (c == '>') depth++;
                        if (c == '<') depth--;
                    }
                }

                String type = maybeTypeSeparator
                        .map(typeSeparator -> beforeName.substring(typeSeparator + " ".length()))
                        .orElse(beforeName);

                String name = header.substring(separator + 1).strip();
                return new Ok<>(type + " " + name + "(){\n}\n");
            }
        }

        return invalidate(input, "class segment");
    }

    private static Result<String, CompileException> compileWhitespace(String input) {
        if (input.isBlank()) return new Ok<>("");
        return new Err<>(new CompileException("Input not blank", input));
    }
}
