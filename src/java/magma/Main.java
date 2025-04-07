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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private sealed interface Result<T, X> permits Ok, Err {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }
    }

    private static class State {
        private final Deque<Character> queue;
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private State(Deque<Character> queue) {
            this(queue, new ArrayList<>(), new StringBuilder(), 0);
        }

        private State(Deque<Character> queue, List<String> segments, StringBuilder buffer, int depth) {
            this.queue = queue;
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        private State enter() {
            this.depth = depth + 1;
            return this;
        }

        private State exit() {
            this.depth = depth - 1;
            return this;
        }

        private State append(char c) {
            buffer.append(c);
            return this;
        }

        private State advance() {
            segments.add(buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private boolean isLevel() {
            return depth == 0;
        }

        private char getPop() {
            return queue.pop();
        }

        private boolean isShallow() {
            return depth == 1;
        }

        public List<String> segments() {
            return segments;
        }
    }

    private static final List<String> imports = new ArrayList<>();
    private static final List<String> structs = new ArrayList<>();
    private static final List<String> functions = new ArrayList<>();
    private static int lambdaCounter = 0;

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        readString(source)
                .match(input -> runWithInput(source, input), Optional::of)
                .ifPresent(Throwable::printStackTrace);
    }

    private static Optional<IOException> runWithInput(Path source, String input) {
        String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n";

        Path target = source.resolveSibling("main.c");
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
        List<String> segments = divideAll(input, Main::divideStatementChar);
        return parseAll(segments, Main::compileRootSegment)
                .map(compiled -> {
                    compiled.addAll(imports);
                    compiled.addAll(structs);
                    compiled.addAll(functions);
                    return compiled;
                })
                .map(compiled -> mergeAll(compiled, Main::mergeStatements))
                .orElse("");
    }

    private static Optional<String> compileStatements(String input, Function<String, Optional<String>> compiler) {
        return compileAll(divideAll(input, Main::divideStatementChar), compiler, Main::mergeStatements);
    }

    private static Optional<String> compileAll(
            List<String> segments,
            Function<String, Optional<String>> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        return parseAll(segments, compiler).map(compiled -> mergeAll(compiled, merger));
    }

    private static String mergeAll(List<String> compiled, BiFunction<StringBuilder, String, StringBuilder> merger) {
        StringBuilder output = new StringBuilder();

        for (String segment : compiled) {
            output = merger.apply(output, segment);
        }

        return output.toString();
    }

    private static Optional<List<String>> parseAll(List<String> segments, Function<String, Optional<String>> compiler) {
        Optional<List<String>> maybeCompiled = Optional.of(new ArrayList<String>());
        for (String segment : segments) {
            maybeCompiled = maybeCompiled.flatMap(allCompiled -> {
                return compiler.apply(segment).map(compiled -> {
                    allCompiled.add(compiled);
                    return allCompiled;
                });
            });
        }
        return maybeCompiled;
    }

    private static StringBuilder mergeStatements(StringBuilder output, String str) {
        return output.append(str);
    }

    private static List<String> divideAll(String input, BiFunction<State, Character, State> divider) {
        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        State state = new State(queue);
        State current = state;
        while (!isEmpty(current)) {
            char c = current.getPop();
            current = divider.apply(current, c);
        }

        return current.advance().segments();
    }

    private static State divideStatementChar(State state, char c) {
        State appended = state.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '}' && appended.isShallow()) return appended.advance().exit();
        if (c == '{') return appended.enter();
        if (c == '}') return appended.exit();
        return appended;
    }

    private static boolean isEmpty(State state) {
        return state.queue.isEmpty();
    }

    private static Optional<String> compileRootSegment(String input) {
        if (input.startsWith("package ")) return Optional.of("");

        if (input.strip().startsWith("import ")) {
            String value = "#include <temp.h>\n";
            imports.add(value);
            return Optional.of("");
        }

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
                        return generateStruct(modifiers, name) + outputContent;
                    });
                }
            }
        }

        return Optional.of(invalidate("root segment", input));
    }

    private static String generateStruct(String modifiers, String name) {
        String generated = generatePlaceholder(modifiers) + "struct " + name + " {\n};\n";
        structs.add(generated);
        return "";
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
        int paramEnd = withParams.indexOf(")");
        if (paramEnd < 0) return Optional.empty();

        String paramString = withParams.substring(0, paramEnd);
        String withBody = withParams.substring(paramEnd + ")".length()).strip();

        if (!withBody.startsWith("{") || !withBody.endsWith("}")) return Optional.empty();

        return compileValues(paramString, Main::compileDefinition).flatMap(outputParams -> {
            return compileDefinition(header).flatMap(definition -> {
                return compileStatements(withBody.substring(1, withBody.length() - 1), Main::compileStatement).map(statement -> {
                    return addFunction(definition, outputParams, statement);
                });
            });
        });
    }

    private static String addFunction(String definition, String params, String content) {
        String function = definition + "(" + params + "){" + content + "\n}\n";
        functions.add(function);
        return "";
    }

    private static Optional<String> compileValues(String input, Function<String, Optional<String>> compiler) {
        return compileAll(divideAll(input, Main::divideValueChar), compiler, Main::mergeValues);
    }

    private static State divideValueChar(State state, Character c) {
        if (c == ',' && state.isLevel()) return state.advance();

        State appended = state.append(c);
        if (c == '(') return appended.enter();
        if (c == ')') return appended.exit();
        return appended;
    }

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(", ").append(element);
    }

    private static Optional<String> compileStatement(String input) {
        String stripped = input.strip();
        if (stripped.isEmpty()) return Optional.of("");

        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());

            int separator = withoutEnd.indexOf("=");
            if (separator >= 0) {
                String inputDefinition = withoutEnd.substring(0, separator);
                String inputValue = withoutEnd.substring(separator + "=".length());
                return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
                    return compileValue(inputValue).map(outputValue -> {
                        return generateStatement(outputDefinition + " = " + outputValue);
                    });
                });
            }

            Optional<String> maybeInvocation = compileInvocation(withoutEnd);
            if (maybeInvocation.isPresent()) return maybeInvocation.map(Main::generateStatement);
        }

        return Optional.of(invalidate("statement", input));
    }

    private static String generateStatement(String value) {
        return "\n\t" + value + ";";
    }

    private static Optional<String> compileValue(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) return Optional.of(stripped);

        int arrowIndex = stripped.indexOf("->");
        if (arrowIndex >= 0) {
            String paramName = stripped.substring(0, arrowIndex).strip();
            String inputValue = stripped.substring(arrowIndex + "->".length());
            if (isSymbol(paramName)) {
                return compileValue(inputValue).map(outputValue -> generateLambda(paramName, outputValue));
            }
        }

        Optional<String> maybeInvocation = compileInvocation(stripped);
        if (maybeInvocation.isPresent()) return maybeInvocation;

        int dataSeparator = stripped.lastIndexOf(".");
        if (dataSeparator >= 0) {
            String object = stripped.substring(0, dataSeparator);
            String property = stripped.substring(dataSeparator + ".".length());

            return compileValue(object).map(newObject -> {
                return newObject + "." + property;
            });
        }

        int methodSeparator = stripped.lastIndexOf("::");
        if (methodSeparator >= 0) {
            String object = stripped.substring(0, methodSeparator);
            String property = stripped.substring(methodSeparator + "::".length());

            return compileValue(object).map(newObject -> {
                String caller = newObject + "." + property;
                String paramName = newObject.toLowerCase();
                return generateLambda(paramName, generateInvocation(caller, paramName));
            });
        }

        if (isSymbol(stripped)) {
            return Optional.of(stripped);
        }

        return Optional.of(invalidate("value", input));
    }

    private static String generateLambda(String paramName, String lambdaValue) {
        String lambda = "__lambda" + lambdaCounter + "__";
        lambdaCounter++;

        String definition = generateDefinition("", "auto", lambda);
        String param = generateDefinition("", "auto", paramName);
        addFunction(definition, param, "\n\treturn " + lambdaValue + ";");

        return lambda;
    }

    private static Optional<String> compileInvocation(String stripped) {
        if (!stripped.endsWith(")")) return Optional.empty();
        String withoutEnd = stripped.substring(0, stripped.length() - ")".length());

        int argsStart = -1;
        int depth = 0;
        for (int i = withoutEnd.length() - 1; i >= 0; i--) {
            char c = withoutEnd.charAt(i);
            if (c == '(' && depth == 0) {
                argsStart = i;
                break;
            } else {
                if (c == ')') depth++;
                if (c == '(') depth--;
            }
        }

        if (argsStart < 0) return Optional.empty();

        String inputCaller = withoutEnd.substring(0, argsStart);
        String inputArguments = withoutEnd.substring(argsStart + 1);
        return compileValues(inputArguments, Main::compileValue).flatMap(outputValues -> {
            return compileValue(inputCaller).map(outputCaller -> {
                return generateInvocation(outputCaller, outputValues);
            });
        });
    }

    private static String generateInvocation(String caller, String arguments) {
        return caller + "(" + arguments + ")";
    }

    private static Optional<String> compileDefinition(String input) {
        String stripped = input.strip();
        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = stripped.substring(0, nameSeparator);

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

            String name = stripped.substring(nameSeparator + " ".length());
            return Optional.of(generateDefinition(modifiers, compileType(type), name));
        }

        return Optional.of(invalidate("definition", stripped));
    }

    private static String generateDefinition(String modifiers, String type, String name) {
        return modifiers + type + " " + name;
    }

    private static String compileType(String type) {
        String stripped = type.strip();
        if (stripped.equals("void")) return "void";
        if (stripped.endsWith("[]")) return compileType(stripped.substring(0, stripped.length() - "[]".length())) + "*";

        if (isSymbol(stripped)) {
            return "struct " + stripped;
        }

        return invalidate("type", stripped);
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

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}