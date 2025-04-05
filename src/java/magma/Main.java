package magma;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

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

        private State popAndAppend() {
            return append(pop());
        }

        private char pop() {
            return queue.pop();
        }

        private boolean isEmpty() {
            return queue.isEmpty();
        }

        private State append(char c) {
            buffer.append(c);
            return this;
        }

        private State enter() {
            this.depth = depth + 1;
            return this;
        }

        private State exit() {
            this.depth = depth - 1;
            return this;
        }

        private State advance() {
            segments().add(buffer.toString());
            this.buffer = new StringBuilder();
            return this;
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

    public record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(value);
        }
    }

    public record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }
    }

    private record Node(String type, String name) {
    }

    public static final List<String> RESERVED_KEYWORDS = List.of("private");

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        magma.Files.readString(source)
                .match(input -> runWithSource(source, input), Optional::of)
                .ifPresent(Throwable::printStackTrace);
    }

    private static Optional<IOException> runWithSource(Path source, String input) {
        String string = compileStatements(input, Main::compileRootSegment).orElse("");
        Path target = source.resolveSibling("main.c");
        return magma.Files.writeString(target, string + "int main(){\n\t__main__();\n\treturn 0;\n}\n");
    }

    private static Optional<String> compileStatements(String input, Function<String, Optional<String>> compiler) {
        return compile(divideStatements(input, Main::processStatementChar), compiler, Main::merge);
    }

    private static Optional<String> compile(
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

    private static StringBuilder merge(StringBuilder output, String str) {
        return output.append(str);
    }

    private static List<String> divideStatements(String input, BiFunction<State, Character, State> folder) {
        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        State state = new State(queue);
        while (!state.isEmpty()) {
            char c = state.pop();

            State finalState = state;
            state = divideSingleQuotes(state, c)
                    .or(() -> divideDoubleQuotes(finalState, c))
                    .orElseGet(() -> folder.apply(finalState, c));
        }

        return state.advance().segments;
    }

    private static Optional<State> divideSingleQuotes(State state, char c) {
        if (c != '\'') return Optional.empty();
        State appended = state.append(c);

        char maybeSlash = appended.pop();
        State withMaybeSlash = appended.append(maybeSlash);
        State withEscape = maybeSlash == '\\'
                ? withMaybeSlash.popAndAppend()
                : withMaybeSlash;

        return Optional.ofNullable(withEscape.popAndAppend());
    }

    private static Optional<State> divideDoubleQuotes(State state, char c) {
        if (c != '"') return Optional.empty();

        State current = state.append(c);
        while (!current.isEmpty()) {
            char next = current.pop();
            current.append(next);

            if (next == '\\') current.append(current.pop());
            if (next == '"') break;
        }

        return Optional.of(current);
    }

    private static State processStatementChar(State state, char c) {
        State appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{' || c == '(') return appended.enter();
        if (c == '}' || c == ')') return appended.exit();
        return appended;
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
        return compileStatements(inputContent, input1 -> compileClassSegment(input1, name)).map(outputContent -> {
            return "struct " + name + " {\n};\n" + outputContent;
        });
    }

    private static Optional<String> invalidate(String type, String input) {
        System.err.println("Invalid " + type + ": " + input);
        return Optional.empty();
    }

    private static Optional<String> compileClassSegment(String input, String structName) {
        Optional<String> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isPresent()) return maybeWhitespace;

        Optional<String> maybeClass = compileClass(input);
        if (maybeClass.isPresent()) return maybeClass;

        Optional<String> inputType = compileMethod(input, structName);
        if (inputType.isPresent()) return inputType;

        if (input.indexOf("(") >= 0) {
            return generateStructType(structName)
                    .flatMap(type -> generateDefinition(new Node(type, "new")))
                    .map(definition -> generateMethod(definition, ""));
        }

        if (input.endsWith(";")) {
            return Optional.of("int temp;\n");
        }

        return invalidate("class segment", input);
    }


    private static Optional<String> compileMethod(String input, String structName) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return Optional.empty();

        String header = input.substring(0, paramStart).strip();
        return compileDefinition(header, structName).flatMap(definition -> {
            String withParams = input.substring(paramStart + "(".length());
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) return Optional.empty();

            String withBraces = withParams.substring(paramEnd + 1).strip();
            if (!withBraces.startsWith("{")) return Optional.empty();

            String withEnd = withBraces.substring(1);
            if (!withEnd.endsWith("}")) return Optional.empty();

            String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
            return compileStatements(inputContent, Main::compileStatement).map(outputContent -> {
                return generateMethod(definition, outputContent);
            });
        });
    }

    private static Optional<String> compileDefinition(String header, String structName) {
        return parseDefinition(header).flatMap(node0 -> {
            Node node = modifyDefinition(structName, node0);
            return generateDefinition(node);
        });
    }

    private static Optional<Node> parseDefinition(String header) {
        int nameSeparator = header.lastIndexOf(" ");
        if (nameSeparator < 0) return Optional.empty();

        String beforeName = header.substring(0, nameSeparator).strip();
        String oldName = header.substring(nameSeparator + " ".length()).strip();

        int typeSeparator = beforeName.lastIndexOf(" ");
        String inputType = typeSeparator == -1
                ? beforeName
                : beforeName.substring(typeSeparator + " ".length());

        return compileType(inputType).map(outputType -> {
            return new Node(outputType, oldName);
        });
    }

    private static Node modifyDefinition(String structName, Node node) {
        String newName = node.name.equals("main")
                ? "__main__"
                : structName + "_" + node.name;

        return new Node(node.type, newName);
    }

    private static Optional<String> compileStatement(String input) {
        Optional<String> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isPresent()) return maybeWhitespace;

        String stripped = input.strip();
        if (stripped.startsWith("if ")) return Optional.of("\n\tif (1) {\n\t}");
        if (stripped.startsWith("return ")) return Optional.of("\n\treturn temp;");
        if (stripped.startsWith("for")) return Optional.of("\n\tfor(;;){\n\t}");

        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            int valueSeparator = withoutEnd.indexOf("=");
            if (valueSeparator >= 0) {
                String inputDefinition = withoutEnd.substring(0, valueSeparator).strip();
                String value = withoutEnd.substring(valueSeparator + "=".length());

                return parseDefinition(inputDefinition)
                        .flatMap(Main::generateDefinition)
                        .flatMap(outputDefinition -> {
                            return compileValue(value).map(outputValue -> {
                                return "\n\t" +
                                        outputDefinition +
                                        " = " + outputValue + ";";
                            });
                        });
            }
        }

        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            Optional<String> maybeInvocation = compileInvocation(withoutEnd);
            if (maybeInvocation.isPresent()) return maybeInvocation;
        }

        if (stripped.startsWith("while ")) return Optional.of("\n\twhile(1) {\n\t}\n");
        return invalidate("statement", input);
    }

    private static Optional<String> compileValue(String value) {
        String stripped = value.strip();
        if (stripped.startsWith("new ")) return Optional.of("Temp()");

        Optional<String> maybeInvocation = compileInvocation(value);
        if (maybeInvocation.isPresent()) return maybeInvocation;

        if (value.contains("?")) return Optional.of("condition ? whenTrue : whenFalse");

        if (isSymbol(stripped)) {
            return Optional.of(stripped);
        }

        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return Optional.of(stripped);
        }

        if (isNumber(stripped)) {
            return Optional.of(stripped);
        }

        int propertySeparator = value.indexOf(".");
        if (propertySeparator >= 0) {
            String oldChild = value.substring(0, propertySeparator);
            String property = value.substring(propertySeparator + ".".length());
            return compileValue(oldChild).map(newChild -> newChild + "." + property);
        }

        return compileOperator(value, "-")
                .or(() -> compileOperator(value, "+"))
                .or(() -> invalidate("value", value));
    }

    private static Optional<String> compileOperator(String value, String operator) {
        int operatorIndex = value.indexOf(operator);
        if (operatorIndex < 0) return Optional.empty();

        String leftString = value.substring(0, operatorIndex).strip();
        String rightString = value.substring(operatorIndex + 1).strip();
        return compileValue(leftString).flatMap(left -> {
            return compileValue(rightString).map(right -> {
                return left + " " + operator + " " + right;
            });
        });
    }

    private static Optional<String> compileInvocation(String value) {
        if (!value.endsWith(")")) return Optional.empty();

        String withoutEnd = value.substring(0, value.length() - ")".length());
        int argsStart = withoutEnd.lastIndexOf("(");
        if (argsStart < 0) return Optional.empty();

        String inputCaller = withoutEnd.substring(0, argsStart);
        String inputArguments = withoutEnd.substring(argsStart + "(".length());
        List<String> arguments = divideStatements(inputArguments, Main::divideValues);

        return compile(arguments, Main::compileValue, Main::mergeValues).flatMap(outputArguments -> {
            return compileValue(inputCaller).map(outputCaller -> {
                return outputCaller + "(" + outputArguments + ")";
            });
        });
    }

    private static State divideValues(State state, char c) {
        if (c == ',') return state.advance();

        State append = state.append(c);
        if (c == '(') return append.enter();
        if (c == ')') return append.exit();
        return append;
    }

    private static StringBuilder mergeValues(StringBuilder output, String str) {
        if (output.isEmpty()) return output.append(str);
        return output.append(", ").append(str);
    }

    private static boolean isNumber(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    private static String generateMethod(String definition, String content) {
        return definition + "(){" + content + "\n}\n";
    }

    private static Optional<String> generateDefinition(Node node) {
        return Optional.of(node.type() + " " + node.name());
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

