package magma;

import java.io.IOException;
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
        return compileAll(divideByStatements(input), compiler, Main::mergeStatements);
    }

    private static Result<String, CompileException> compileAll(List<String> segments, Function<String, Result<String, CompileException>> compiler, BiFunction<StringBuilder, String, StringBuilder> getAppend) {
        Result<StringBuilder, CompileException> output = new Ok<>(new StringBuilder());
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

        Result<String, CompileException> maybeMethod = compileMethod(input);
        if (maybeMethod.isOk()) return maybeMethod;

        return invalidateInput("class segment", input);
    }

    private static Result<String, CompileException> compileMethod(String input) {
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

    private static Result<String, CompileException> compileDefinition(String definition) {
        int separator = definition.lastIndexOf(" ");
        if (separator < 0) return createMissingInfixError(definition, " ");

        String beforeName = definition.substring(0, separator).strip();

        int typeSeparator = -1;
        int depth = 0;
        for (int i = beforeName.length() - 1; i >= 0; i--) {
            char c = beforeName.charAt(i);
            if (c == ' ' && depth == 0) {
                typeSeparator = i;
                break;
            } else {
                if (c == '>') depth++;
                if (c == '<') depth--;
            }
        }

        String type = typeSeparator == -1
                ? beforeName
                : beforeName.substring(typeSeparator + " ".length());

        String name = definition.substring(separator + " ".length());
        return generateDefinition(new MapNode()
                .withString("type", type)
                .withString("name", name));
    }

    private static Result<String, CompileException> generateDefinition(Node node) {
        return new StringRule("type").generate(node).and(() -> new StringRule("name").generate(node)).mapValue(tuple -> {
            return tuple.left() + " " + tuple.right();
        });
    }

    private static Err<String, CompileException> createMissingInfixError(String input, String infix) {
        return new Err<>(new CompileException("Infix '" + infix + "' not present", input));
    }
}
