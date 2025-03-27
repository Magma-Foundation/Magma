package magma;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        Results.wrapSupplier(() -> Files.readString(source))
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(input -> compileWithSource(source, input), Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<ApplicationError> compileWithSource(Path source, String input) {
        return compile(input)
                .mapErr(ApplicationError::new)
                .match(output -> writeToTarget(source, output), Optional::of);
    }

    private static Optional<ApplicationError> writeToTarget(Path source, String output) {
        return Results.wrapRunnable(() -> Files.writeString(source.resolveSibling("Main.c"), output))
                .map(ThrowableError::new)
                .map(ApplicationError::new);
    }

    private static Result<String, CompileError> compile(String input) {
        return compileAll(input, Main::compileRootSegment);
    }

    private static Result<String, CompileError> compileAll(String input, Function<String, Result<String, CompileError>> compiler) {
        return compileAll(divideByStatements(input), compiler, Main::mergeStatements);
    }

    private static Result<String, CompileError> compileAll(List<String> segments, Function<String, Result<String, CompileError>> compiler, BiFunction<StringBuilder, String, StringBuilder> getAppend) {
        Result<StringBuilder, CompileError> output = new Ok<>(new StringBuilder());
        for (String segment : segments) {
            output = output.and(() -> compiler.apply(segment))
                    .mapValue(tuple -> getAppend.apply(tuple.left(), tuple.right()));
        }

        return output.mapValue(StringBuilder::toString);
    }

    private static StringBuilder mergeStatements(StringBuilder cache, String element) {
        return cache.append(element);
    }

    private static List<String> divideByStatements(String input) {
        return divideUsing(input, Main::divideStatementChar);
    }

    private static List<String> divideUsing(String input, BiFunction<State, Character, State> divider) {
        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        State current = new State(queue);
        while (true) {
            Optional<Character> maybeNext = current.pop();
            if (maybeNext.isEmpty()) break;

            char next = maybeNext.orElse('\0');
            current = divideCharUsing(current, next, divider);
        }

        return current.advance().stream().toList();
    }

    private static State divideCharUsing(State state, char next, BiFunction<State, Character, State> divider) {
        return divideSingleQuotesChar(state, next)
                .orElseGet(() -> divider.apply(state, next));
    }

    private static State divideStatementChar(State state, char next) {
        State current = state.append(next);
        if (next == ';' && current.isLevel()) return current.advance();
        if (next == '}' && current.isShallow()) return current.advance().exit();
        if (next == '{') return current.enter();
        if (next == '}') return current.exit();
        return current;
    }

    private static Optional<State> divideSingleQuotesChar(State state, char next) {
        if (next != '\'') return Optional.empty();

        State current = state.append(next);
        char maybeSlash = current.pop().orElse('\0');
        State appended = current.append(maybeSlash);

        Optional<State> withSlash = maybeSlash == '\\'
                ? appended.popAndAppend()
                : Optional.of(appended);

        return withSlash.flatMap(State::popAndAppend);
    }

    private static Result<String, CompileError> compileRootSegment(String segment) {
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

    private static Result<String, CompileError> invalidateInput(String type, String input) {
        return new Err<>(new CompileError("Invalid " + type, input));
    }

    private static Result<String, CompileError> compileClassSegment(String input) {
        Result<String, CompileError> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isOk()) return maybeWhitespace;

        Result<String, CompileError> maybeMethod = compileMethod(input);
        if (maybeMethod.isOk()) return maybeMethod;

        return invalidateInput("class segment", input);
    }

    private static Result<String, CompileError> compileWhitespace(String input) {
        if (input.isBlank()) return new Ok<>("");
        return new Err<>(new CompileError("Input not blank.", input));
    }

    private static Result<String, CompileError> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return createMissingInfixError(input, "(");

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + 1);
        return compileDefinition(inputDefinition).flatMapValue(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) return createMissingInfixError(withParams, ")");

            String paramString = withParams.substring(0, paramEnd);
            List<String> inputParams = divideByValues(paramString);
            return compileAll(inputParams, Main::compileDefinition, Main::mergeValues).flatMapValue(outputParams -> {
                return new Ok<>(outputDefinition + "(" +
                        outputParams +
                        "){\n}\n");
            });
        });
    }

    private static List<String> divideByValues(String paramString) {
        return divideUsing(paramString, Main::divideValueChar);
    }

    private static State divideValueChar(State state, Character c) {
        if (c == ',' && state.isLevel()) return state.advance();

        State appended = state.append(c);
        if (c == '<') return appended.enter();
        if (c == '>') return appended.exit();
        return appended;
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) return cache.append(element);
        return cache.append(", ").append(element);
    }

    private static Result<String, CompileError> compileDefinition(String input) {
        return parseSplit(input, " ").flatMapValue(Main::generateDefinition);
    }

    private static Result<Node, CompileError> parseSplit(String input, String infix) {
        int separator = input.lastIndexOf(infix);
        if (separator < 0) return createMissingInfixError(input, infix);

        String beforeName = input.substring(0, separator).strip();
        String name = input.substring(separator + infix.length());

        String type = locateTypeSeparator(beforeName)
                .map(typeSeparator -> beforeName.substring(typeSeparator + infix.length()))
                .orElse(beforeName);

        Node withType = new MapNode().withString("type", type);
        return new Ok<>(withType.merge(new MapNode().withString("name", name)));
    }

    private static Optional<Integer> locateTypeSeparator(String input) {
        int depth = 0;
        for (int index = input.length() - 1; index >= 0; index--) {
            char c = input.charAt(index);
            if (c == ' ' && depth == 0) {
                return Optional.of(index);
            } else {
                if (c == '>') depth++;
                if (c == '<') depth--;
            }
        }
        return Optional.empty();
    }

    private static Result<String, CompileError> generateDefinition(Node node) {
        return new StringRule("type").generate(node).and(() -> new StringRule("name").generate(node)).mapValue(tuple -> {
            return tuple.left() + " " + tuple.right();
        });
    }

    private static <T> Result<T, CompileError> createMissingInfixError(String input, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", input));
    }
}
