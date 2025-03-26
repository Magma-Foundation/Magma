package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String output = Results.unwrap(compile(input));
            Files.writeString(source.resolveSibling("Main.c"), output);
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static Result<String, CompileException> compile(String input) {
        return compileAll(input, Main::compileRootSegment);
    }

    private static Result<String, CompileException> compileAll(String input, Function<String, Result<String, CompileException>> compiler) {
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
                char popped = queue.pop();
                buffer.append(popped);
                if (popped == '\\') buffer.append(queue.pop());

                buffer.append(queue.pop());
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

    private static Result<String, CompileException> compileRootSegment(String segment) {
        String stripped = segment.strip();
        if (stripped.isEmpty()) return new Ok<>("");

        if (segment.startsWith("package ")) return new Ok<>("");
        if (stripped.startsWith("import ")) return new Ok<>("#include <temp.h>\n");

        int classIndex = segment.indexOf("class ");
        if (classIndex >= 0) {
            String right = segment.substring(classIndex + "class ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String name = right.substring(0, contentStart).strip();
                String withEnd = right.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    return compileAll(inputContent, Main::compileClassSegment)
                            .mapValue(outputContent -> "struct " + name + " {\n};\n" + outputContent);
                }
            }
        }

        return invalidateInput("root segment", segment);
    }

    private static Result<String, CompileException> invalidateInput(String type, String input) {
        return new Err<>(new CompileException("Invalid " + type, input));
    }

    private static Result<String, CompileException> compileClassSegment(String input) {
        if (input.isBlank()) return new Ok<>("");

        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String definition = input.substring(0, paramStart).strip();
            int separator = definition.lastIndexOf(" ");
            if (separator >= 0) {
                String name = definition.substring(separator + " ".length());
                return new Ok<>("void " + name + "(){\n}\n");
            }
        }
        return invalidateInput("class segment", input);
    }
}
