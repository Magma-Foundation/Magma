package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        try {
            String input = Files.readString(Paths.get(".", "src", "java", "magma", "Main.java"));
            String output = compile(input);
            Files.writeString(Paths.get(".", "out", "main.c"), output);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return divideAndCompile(input, Main::compileRootSegment) + "int main(){\n\t__main__();\n\treturn 0;\n}";
    }

    private static String divideAndCompile(String input, Function<String, Optional<String>> compiler) {
        ArrayList<String> segments = divideStatements(input);

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            Optional<String> compiled = compiler.apply(segment);
            output.append(compiled.orElse(""));
        }

        return output.toString();
    }

    private static ArrayList<String> divideStatements(String input) {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;

        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            Character c = queue.pop();
            buffer.append(c);

            if (c == '\'') {
                Character popped = queue.pop();
                buffer.append(popped);

                if (popped == '\\') buffer.append(queue.pop());
                buffer.append(queue.pop());
                continue;
            }

            if (c == '"') {
                while (!queue.isEmpty()) {
                    Character popped = queue.pop();
                    buffer.append(popped);

                    if (popped == '\\') buffer.append(queue.pop());
                    if (popped == '"') break;
                }
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
        return segments;
    }

    private static Optional<String> compileRootSegment(String input) {
        Optional<String> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isPresent()) return maybeWhitespace;

        if (input.startsWith("package ")) return Optional.of("");
        if (input.strip().startsWith("import ")) return Optional.of("#include <temp.h>\n");

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String right = input.substring(classIndex + "class ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String name = right.substring(0, contentStart).strip();
                String withEnd = right.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    String outputContent = divideAndCompile(inputContent, Main::compileClassMember);
                    return Optional.of("struct " + name + " {\n};\n" + outputContent);
                }
            }
        }

        return invalidate("root segment", input);
    }

    private static Optional<String> invalidate(String type, String input) {
        System.err.println("Invalid " + type + ": " + input);
        return Optional.empty();
    }

    private static Optional<String> compileClassMember(String input) {
        Optional<String> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isPresent()) return maybeWhitespace;

        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String inDefinition = input.substring(0, paramStart).strip();
            String withParams = input.substring(paramStart + "(".length());

            int nameSeparator = inDefinition.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                Optional<String> outDefinition = compileDefinition(inDefinition, nameSeparator).flatMap(definition -> {
                    int paramEnd = withParams.indexOf(")");
                    if (paramEnd >= 0) {
                        String params = withParams.substring(0, paramEnd);

                        return Optional.of(definition + "(){\n}\n");
                    } else {
                        return Optional.empty();
                    }
                });

                if (outDefinition.isPresent()) return outDefinition;
            }
        }
        return invalidate("class segment", input);
    }

    private static Optional<String> compileDefinition(String definition, int nameSeparator) {
        String beforeName = definition.substring(0, nameSeparator).strip();

        int typeSeparator = beforeName.lastIndexOf(" ");
        String type = typeSeparator == -1
                ? beforeName
                : beforeName.substring(typeSeparator + " ".length());

        String oldName = definition.substring(nameSeparator + " ".length());
        String newName = oldName.equals("main")
                ? "__main__"
                : oldName;

        return Optional.of(type + " " + newName);
    }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) return Optional.of("");
        return Optional.empty();
    }
}
