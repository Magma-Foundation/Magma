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

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String string = compile(input, Main::compileRootSegment);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, string + "int main(){\n\treturn 0;\n}\n");
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input, Function<String, String> compiler) {
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

        StringBuilder output = new StringBuilder();
        for (String segment : state.segments) {
            output.append(compiler.apply(segment));
        }

        return output.toString();
    }

    private static String compileRootSegment(String input) {
        Optional<String> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isPresent()) return maybeWhitespace.get();

        if (input.startsWith("package ")) return "";
        if (input.strip().startsWith("import ")) return "#include <temp.h>\n";

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    String outputContent = compile(inputContent, Main::compileClassSegment);
                    return "struct " + name + " {\n};\n" + outputContent;
                }
            }
        }

        return invalidate("root segment", input);
    }

    private static String invalidate(String type, String input) {
        System.err.println("Invalid " + type + ": " + input);
        return input;
    }

    private static String compileClassSegment(String input) {
        Optional<String> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isPresent()) return maybeWhitespace.get();

        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String header = input.substring(0, paramStart).strip();
            int nameSeparator = header.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                String inputType = header.substring(0, nameSeparator).strip();
                String name = header.substring(nameSeparator + " ".length()).strip();

                int typeSeparator = inputType.lastIndexOf(" ");
                String outputType = typeSeparator == -1
                        ? inputType
                        : inputType.substring(typeSeparator + " ".length());

                return outputType + " " + name + "(){\n}\n";
            }
        }
        return invalidate("class segment", input);
    }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) return Optional.of("");
        return Optional.empty();
    }
}

