package magma;

import magma.locate.FirstLocator;
import magma.locate.LastLocator;
import magma.locate.TypeSeparatorLocator;
import magma.result.Result;
import magma.result.Results;
import magma.split.IndexSplitter;
import magma.split.Splitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        final var source = Paths.get(".", "src", "java", "magma", "Main.java");
        readSafe(source)
                .match(input -> runWithInput(source, input), Optional::of)
                .ifPresent(Throwable::printStackTrace);
    }

    private static Optional<IOException> runWithInput(Path source, String input) {
        final var output = compileRoot(input);
        final var target = source.resolveSibling("Main.c");
        return writeSafe(target, output);
    }

    private static Optional<IOException> writeSafe(Path target, String output) {
        return Results.wrapRunnable(() -> Files.writeString(target, output));
    }

    private static Result<String, IOException> readSafe(Path source) {
        return Results.wrapSupplier(() -> Files.readString(source));
    }

    private static String compileRoot(String input) {
        return compileAllStatements(input, Main::compileRootSegment)
                .map(output -> output + "int main(){\n\treturn 0;\n}\n")
                .orElse("");
    }

    private static Optional<String> compileAllStatements(String input, StringRule compiler) {
        return compileAll(divide(input, Main::divideAtStatementChar), compiler, Main::mergeStatements);
    }

    private static Optional<String> compileAll(List<String> segments, StringRule compiler, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return parseAll(segments, compiler).flatMap(output -> generateAll(output, merger));
    }

    private static Optional<String> generateAll(List<String> output, BiFunction<StringBuilder, String, StringBuilder> merger) {
        final var reduced = output.stream().reduce(new StringBuilder(), merger, (_, next) -> next);
        return Optional.of(reduced.toString());
    }

    private static Optional<List<String>> parseAll(List<String> segments, StringRule compiler) {
        return segments.stream().reduce(Optional.of(new ArrayList<>()), (strings, segment) -> strings.flatMap(output -> {
            return compiler.apply(segment).map(str -> {
                output.add(str);
                return output;
            });
        }), (_, next) -> next);
    }

    private static StringBuilder mergeStatements(StringBuilder buffer, String element) {
        return buffer.append(element);
    }

    private static List<String> divide(String input, BiFunction<State, Character, State> applier) {
        final var queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        var current = new State(queue);
        while (true) {
            final var maybeNextState = divideWithEscapes(current, applier);
            if (maybeNextState.isPresent()) {
                current = maybeNextState.get();
            } else {
                break;
            }
        }

        return current.advance().segments();
    }

    private static Optional<State> divideWithEscapes(
            State current,
            BiFunction<State, Character, State> applicator
    ) {
        final var maybeNext = current.pop();
        if (maybeNext.isEmpty()) return Optional.empty();

        final char next = maybeNext.orElse('\0');
        return Optional.of(divideAtSingleQuotes(current, next)
                .or(() -> divideAtDoubleQuotes(current, next))
                .orElseGet(() -> applicator.apply(current, next)));
    }

    private static Optional<State> divideAtDoubleQuotes(State initial, char c) {
        if (c != '"') return Optional.empty();

        var current = initial.append(c);
        while (true) {
            final var popped = current.pop();
            if (popped.isEmpty()) break;

            final char next = popped.orElse('\0');
            current = current.append(next);

            if (next == '\\') {
                final var state = current.popAndAppend();
                if (state.isEmpty()) break;
                current = state.get();
            }
            if (next == '"') {
                break;
            }
        }

        return Optional.of(current);
    }

    private static Optional<State> divideAtSingleQuotes(State current, char next) {
        if (next != '\'') return Optional.empty();

        return current.append(next).pop().flatMap(value -> {
            final State withValue = current.append(value);
            final Optional<State> withEscaped = value == '\\'
                    ? withValue.popAndAppend()
                    : Optional.of(withValue);

            return withEscaped.flatMap(State::popAndAppend);
        });
    }

    private static State divideAtStatementChar(State current, char next) {
        final var appended = current.append(next);
        if (next == ';' && appended.isLevel()) return appended.advance();
        if (next == '}' && appended.isShallow()) return appended.advance().exit();
        if (next == '{' || next == '(') return appended.enter();
        if (next == '}' || next == ')') return appended.exit();
        return appended;
    }

    private static Optional<String> compileRootSegment(String input) {
        final var stripped = input.strip();
        if (stripped.isEmpty()) return Optional.of("");

        if (stripped.startsWith("package ")) return Optional.of("");
        if (stripped.startsWith("import ")) {
            final var right = stripped.substring("import ".length());
            return truncateRight(right, ";", content -> {
                final var namespace = content.split(Pattern.quote("."));
                return Optional.of("#include <" + String.join("/", namespace) + ".h>\n");
            });
        }

        return compileClass(input).or(() -> invalidate("root segment", input));
    }

    private static Optional<String> compileClass(String input) {
        return split(input, new IndexSplitter("class", new FirstLocator()), tuple -> {
            return split(tuple.right(), new IndexSplitter("{", new FirstLocator()), tuple0 -> {
                final var name = tuple0.left().strip();
                final var withEnd = tuple0.right().strip();
                return truncateRight(withEnd, "}", inputContent -> {
                    return compileAllStatements(inputContent, Main::compileClassSegment).flatMap(outputContent -> {
                        return Optional.of("struct " + name + " {\n};\n" + outputContent);
                    });
                });
            });
        });
    }

    private static <T> Optional<T> invalidate(String type, String input) {
        return printError("Invalid " + type + ": " + input);
    }

    private static <T> Optional<T> printError(String message) {
        System.err.println(message);
        return Optional.empty();
    }

    private static Optional<String> compileClassSegment(String input) {
        if (input.isBlank()) return Optional.of("");

        return compileMethod(input)
                .or(() -> invalidate("class segment", input));
    }

    private static Optional<String> compileMethod(String input) {
        return split(input, new IndexSplitter("(", new FirstLocator()), tuple -> {
            return compileDefinition(tuple.left().strip()).flatMap(outputDefinition -> {
                return split(tuple.right(), new IndexSplitter(")", new FirstLocator()), tuple1 -> {
                    return compileAllValues(tuple1.left(), Main::compileDefinition).flatMap(outputParams -> {
                        return truncateLeft(tuple1.right().strip(), "{", right -> {
                            return truncateRight(right, "}", content -> {
                                return compileAllStatements(content, Main::compileStatement).map(compiled -> {
                                    return outputDefinition + "(" +
                                            outputParams +
                                            "){\n" +
                                            compiled +
                                            "}\n";
                                });
                            });
                        });
                    });
                });
            });
        });
    }

    private static Optional<String> compileStatement(String input) {
        final var stripped = input.strip();
        if (stripped.isEmpty()) return Optional.of("");

        if (stripped.startsWith("return ")) return Optional.of("\treturn temp;\n");
        if (stripped.startsWith("if ")) return Optional.of("\tif (temp) {\n\t}\n");
        if (stripped.startsWith("while ")) return Optional.of("\twhile (temp) {\n\t}\n");

        final var maybeInitialization = truncateRight(stripped, ";", left -> {
            return split(left, new IndexSplitter("=", new FirstLocator()), tuple -> {
                final var header = tuple.left();
                return compileDefinition(header).map(definition -> {
                    return compileValue(tuple.right()).map(value -> {
                        return "\t" + definition + " = " + value + ";\n";
                    });
                });
            });
        });

        if (maybeInitialization.isPresent()) return maybeInitialization.get();

        if (stripped.endsWith(");")) return Optional.of("\ttemp();\n");

        return invalidate("statement", input);
    }

    private static Optional<String> compileValue(String input) {
        final var maybeString = compileString(input);
        if (maybeString.isPresent()) return maybeString;

        final var maybeInvocation = compileInvocation(input);
        if (maybeInvocation.isPresent()) return maybeInvocation;

        final var maybeDataAccess = compileDataAccess(input);
        if (maybeDataAccess.isPresent()) return maybeDataAccess;

        final var stripped = input.strip();
        if (isSymbol(stripped)) return Optional.of(stripped);

        return invalidate("value", input);
    }

    private static Optional<String> compileString(String input) {
        return truncateLeft(input.strip(), "\"", right -> {
            return truncateRight(right, "\"", content -> {
                return Optional.of("\"" + content + "\"");
            });
        });
    }

    private static Optional<String> compileDataAccess(String input) {
        return split(input, new IndexSplitter(".", new LastLocator()), tuple -> {
            return compileValue(tuple.left()).map(inner -> inner + "." + tuple.right());
        });
    }

    private static Optional<String> compileInvocation(String input) {
        return truncateRight(input, ")", left -> {
            return split(left, new IndexSplitter("(", new LastLocator()), tuple -> {
                return compileValue(tuple.left()).flatMap(value -> {
                    return compileAllValues(tuple.right(), Main::compileValue).map(arguments -> {
                        return value + "(" + arguments + ")";
                    });
                });
            });
        });
    }

    private static Optional<String> truncateLeft(String input, String prefix, StringRule compiler) {
        return input.startsWith(prefix)
                ? compiler.apply(input.substring(prefix.length()))
                : Optional.empty();
    }

    private static Optional<String> compileAllValues(String input, StringRule compiler) {
        return compileAllValues(input, compiler, (buffer, element) -> {
            return mergeDelimited(buffer, element, ", ");
        });
    }

    private static Optional<String> compileAllValues(String input, StringRule compiler, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return compileAll(divideByValues(input), compiler, merger);
    }

    private static StringBuilder mergeDelimited(StringBuilder buffer, String element, String delimiter) {
        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(delimiter).append(element);
    }

    private static List<String> divideByValues(String input) {
        return divide(input, (state, c) -> {
            if (c == ',' && state.isLevel()) return state.advance();

            final var appended = state.append(c);
            if (c == '<') return appended.enter();
            if (c == '>') return appended.exit();
            return appended;
        });
    }

    private static Optional<String> compileDefinition(String input) {
        final var stripped = input.strip();
        return split(stripped, new IndexSplitter(" ", new LastLocator()), tuple -> {
            final var beforeName = tuple.left();
            final var name = tuple.right();

            final var inputType = split(beforeName, new IndexSplitter(" ", new TypeSeparatorLocator()),
                    tuple1 -> Optional.of(tuple1.right())).orElse(beforeName);

            return parseType(inputType).flatMap(type -> {
                if (type.is("functional")) {
                    return generateFunctionalType(type, name);
                } else {
                    return generateSymbol(type).map(inner -> inner + " " + name);
                }
            });
        });
    }

    private static Optional<String> generateType(MapNode node) {
        return node.is("functional") ? generateFunctionalType(node, "") : generateSymbol(node);
    }

    private static Optional<MapNode> parseType(String input) {
        final var maybeArray = truncateRight(input, "[]", array -> Optional.of(array + "*"));
        if (maybeArray.isPresent()) return maybeArray.map(Main::wrapAsSymbol);

        final var maybeGeneric = truncateRight(input, ">", withoutEnd -> {
            return split(withoutEnd, new IndexSplitter("<", new FirstLocator()), tuple -> {
                List<String> segments = divideByValues(tuple.right());
                return parseAll(segments, input1 -> parseType(input1).flatMap(Main::generateType)).flatMap(compiled -> modifyGeneric(tuple.left().strip(), compiled));
            });
        });
        if (maybeGeneric.isPresent()) return maybeGeneric;

        final var stripped = input.strip();
        if (isSymbol(stripped)) {
            final var stripped1 = stripped.equals("var") ? "auto" : stripped;
            return Optional.of(wrapAsSymbol(stripped1));
        }

        return invalidate("type", input);
    }

    private static Optional<String> generateSymbol(MapNode node) {
        return node.findString("value");
    }

    private static Optional<MapNode> modifyGeneric(String name, List<String> paramTypes) {
        if (name.equals("Function")) {
            final var paramType = paramTypes.get(0);
            final var returns = paramTypes.get(1);
            final var node = new MapNode()
                    .withType("functional")
                    .withString("return", returns)
                    .withStringList("params", List.of(paramType));

            return Optional.of(node);
        }
        if (name.equals("BiFunction")) {
            final var leftType = paramTypes.get(0);
            final var rightType = paramTypes.get(1);
            final var returns = paramTypes.get(2);
            final var node = new MapNode()
                    .withType("functional")
                    .withString("return", returns)
                    .withStringList("params", List.of(leftType, rightType));

            return Optional.of(node);
        }

        return generateGeneric(name, paramTypes);
    }

    private static Optional<MapNode> generateGeneric(String name, List<String> paramTypes) {
        return generateAll(paramTypes, (buffer, element) -> mergeDelimited(buffer, element, "_"))
                .map(outputParams -> name + "_" + outputParams)
                .map(Main::wrapAsSymbol);
    }

    private static MapNode wrapAsSymbol(String value) {
        return new MapNode().withType("symbol").withString("value", value);
    }

    private static Optional<String> generateFunctionalType(MapNode mapNode, String name) {
        final var returns = mapNode.findString("return").orElse("");
        final var params = mapNode.findStringList("params").orElse(Collections.emptyList());
        final var joinedParams = String.join(", ", params);

        return Optional.of(returns + " (*" + name + ")(" + joinedParams + ")");
    }

    private static boolean isSymbol(String input) {
        return IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .allMatch(ch -> ch == '_' || Character.isLetter(ch));
    }

    private static <T> Optional<T> truncateRight(String input, String suffix, Function<String, Optional<T>> mapper) {
        return input.endsWith(suffix)
                ? mapper.apply(input.substring(0, input.length() - suffix.length()))
                : Optional.empty();
    }

    private static <T> Optional<T> split(
            String input,
            Splitter splitter,
            Function<Tuple<String, String>, Optional<T>> mapper
    ) {
        return splitter.split(input).flatMap(mapper);
    }
}
