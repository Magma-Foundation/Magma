package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static class State {
        public final Deque<Character> queue;
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private State(Deque<Character> queue, List<String> segments, StringBuilder buffer, int depth) {
            this.queue = queue;
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State(Deque<Character> queue) {
            this(queue, new ArrayList<>(), new StringBuilder(), 0);
        }

        private void popAndAppend() {
            append(pop());
        }

        private Character pop() {
            return queue.pop();
        }

        private boolean isEmpty() {
            return queue.isEmpty();
        }

        private void append(char c) {
            buffer.append(c);
        }

        private void enter() {
            this.depth = depth + 1;
        }

        private void exit() {
            this.depth = depth - 1;
        }

        private void advance() {
            segments().add(buffer.toString());
            this.buffer = new StringBuilder();
        }

        private boolean isShallow() {
            return depth == 1;
        }

        private boolean isLevel() {
            return depth == 0;
        }

        public List<String> segments() {
            return segments;
        }
    }

    public static final List<String> RESERVED_KEYWORDS = List.of("private");

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            String string = compile(input, Main::compileRootSegment).orElse("");
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, string + "int main(){\n\treturn 0;\n}\n");
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static Optional<String> compile(String input, Function<String, Optional<String>> compiler) {
        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        State state = new State(queue);
        while (!state.isEmpty()) {
            char c = state.pop();
            state.append(c);

            if (c == '\'') {
                char popped = state.pop();
                state.append(popped);

                if (popped == '\\') state.popAndAppend();
                state.popAndAppend();
                continue;
            }

            if (c == '"') {
                while (!state.isEmpty()) {
                    char next = state.pop();
                    state.append(next);

                    if (next == '\\') state.append(state.pop());
                    if (next == '"') break;
                }
                continue;
            }

            if (c == ';' && state.isLevel()) {
                state.advance();
            } else if (c == '}' && state.isShallow()) {
                state.advance();
                state.exit();
            } else {
                if (c == '{') state.enter();
                if (c == '}') state.exit();
            }
        }
        state.advance();

        Optional<StringBuilder> maybeOutput = Optional.of(new StringBuilder());
        for (String segment : state.segments) {
            maybeOutput = maybeOutput.flatMap(output -> {
                return compiler.apply(segment).map(compiled -> {
                    return output.append(compiled);
                });
            });
        }

        return maybeOutput.map(StringBuilder::toString);
    }

    private static Optional<String> compileRootSegment(String input) {
        Optional<String> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isPresent()) return maybeWhitespace;

        if (input.startsWith("package ")) return Optional.of("");
        if (input.strip().startsWith("import ")) return Optional.of("#include <temp.h>\n");

        Optional<String> maybeClass = compileClass(input);
        if (maybeClass.isPresent()) return maybeClass;

        return invalidate("root segment", input);
    }


    private static Optional<String> compileClass(String input) {
        int classIndex = input.indexOf("class ");
        if (classIndex < 0) return Optional.empty();

        String afterKeyword = input.substring(classIndex + "class ".length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) return Optional.empty();

        String name = afterKeyword.substring(0, contentStart).strip();
        if (!isSymbol(name)) return Optional.empty();

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) return Optional.empty();

        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        return compile(inputContent, input1 -> compileClassSegment(input1, name)).map(outputContent -> {
            return "struct " + name + " {\n};\n" + outputContent;
        });
    }

    private static Optional<String> invalidate(String type, String input) {
        System.err.println("Invalid " + type + ": " + input);
        return Optional.empty();
    }

    private static Optional<String> compileClassSegment(String input, String name) {
        Optional<String> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isPresent()) return maybeWhitespace;

        Optional<String> maybeClass = compileClass(input);
        if (maybeClass.isPresent()) return maybeClass;

        Optional<String> inputType = compileMethod(input);
        if (inputType.isPresent()) return inputType;

        if (input.contains("(")) {
            return generateStructType(name).map(type -> generateMethod(type, "new"));
        }

        if (input.endsWith(";")) {
            return Optional.of("int temp;\n");
        }

        return invalidate("class segment", input);
    }


    private static Optional<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return Optional.empty();

        String header = input.substring(0, paramStart).strip();
        int nameSeparator = header.lastIndexOf(" ");
        if (nameSeparator < 0) return Optional.empty();

        String beforeName = header.substring(0, nameSeparator).strip();
        String name = header.substring(nameSeparator + " ".length()).strip();

        int typeSeparator = beforeName.lastIndexOf(" ");
        String inputType = typeSeparator == -1
                ? beforeName
                : beforeName.substring(typeSeparator + " ".length());

        return compileType(inputType).map(outputType -> {
            return generateMethod(outputType, name);
        });
    }

    private static String generateMethod(String type, String name) {
        return type + " " + name + "(){\n}\n";
    }

    private static Optional<String> compileType(String type) {
        String stripped = type.strip();
        if (isSymbol(stripped)) {
            if (!RESERVED_KEYWORDS.contains(stripped)) {
                return generateStructType(stripped);
            }
        }

        if (type.endsWith(">")) {
            return generateStructType("Temp");
        }

        return invalidate("type", type);
    }

    private static Optional<String> generateStructType(String name) {
        return Optional.of("struct " + name);
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) return Optional.of("");
        return Optional.empty();
    }
}

