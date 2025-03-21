package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        try {
            final var source = Paths.get(".", "src", "java", "magma", "Main.java");
            final var input = Files.readString(source);
            final var output = compileRoot(input);
            final var target = source.resolveSibling("Main.c");
            Files.writeString(target, output);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        return compileAllStatements(input, Main::compileRootSegment)
                .map(output -> output + "int main(){\n\treturn 0;\n}\n")
                .orElse("");
    }

    private static Optional<String> compileAllStatements(String input, Function<String, Optional<String>> compiler) {
        return compileAll(divideByStatements(input), compiler);
    }

    private static Optional<String> compileAll(List<String> segments, Function<String, Optional<String>> compiler) {
        var maybeOutput = Optional.of(new StringBuilder());
        for (var segment : segments) {
            maybeOutput = maybeOutput.flatMap(output -> compiler.apply(segment).map(output::append));
        }

        return maybeOutput.map(StringBuilder::toString);
    }

    private static List<String> divideByStatements(String input) {
        final var queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        final var state = new State(queue);
        return getStrings(state);
    }

    private static List<String> getStrings(State state) {
        var current = state;
        while (true) {
            final var maybeNextState = divideAtStatementChar(current);
            if (maybeNextState.isPresent()) {
                current = maybeNextState.get();
            } else {
                break;
            }
        }

        return current.advance().segments();
    }

    private static Optional<State> divideAtStatementChar(State current) {
        final var maybeNext = current.pop();
        if (maybeNext.isEmpty()) return Optional.empty();

        final char next = maybeNext.orElse('\0');
        final var appended = current.append(next);

        return Optional.of(divideAtSingleQuotes(appended, next)
                .orElseGet(() -> getState(appended, next)));
    }

    private static Optional<State> divideAtSingleQuotes(State current, char next) {
        if (next != '\'') return Optional.empty();

        final var maybeValue = current.pop();
        if (maybeValue.isEmpty()) return Optional.empty();

        final char value = maybeValue.orElse('\0');
        final var withValue = current.append(value);

        final State withEscaped = value == '\\'
                ? withValue.popAndAppend()
                : withValue;

        return Optional.of(withEscaped.popAndAppend());
    }

    private static State getState(State current, char next) {
        if (next == ';' && current.isLevel()) {
            current.advance();
        } else if (next == '}' && current.isShallow()) {
            current.advance();
            current.exit();
        } else {
            if (next == '{') current.enter();
            if (next == '}') current.exit();
        }

        return current;
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

    private static Optional<String> invalidate(String type, String input) {
        return printError("Invalid " + type + ": " + input);
    }

    private static Optional<String> printError(String message) {
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
                        return Optional.of(outputDefinition + "(" +
                                outputParams +
                                "){\n}\n");
                    });
                });
            });
        });
    }

    private static Optional<String> compileAllValues(String input, Function<String, Optional<String>> compiler) {
        return compileAll(divideByValues(input), compiler);
    }

    private static List<String> divideByValues(String input) {
        return Arrays.asList(input.split(Pattern.quote(",")));
    }

    private static Optional<String> compileDefinition(String input) {
        return split(input, new IndexSplitter(" ", new LastLocator()), tuple -> {
            final var beforeName = tuple.left();
            final var name = tuple.right();

            final var inputType = split(beforeName, new IndexSplitter(" ", new LastLocator()),
                    tuple1 -> Optional.of(tuple1.right())).orElse(beforeName);

            return compileType(inputType).map(outputType -> outputType + " " + name);
        });
    }

    private static Optional<String> compileType(String input) {
        final var maybeArray = truncateRight(input, "[]", array -> Optional.of(array + "*"));
        if (maybeArray.isPresent()) return maybeArray;

        final var maybeGeneric = truncateRight(input, ">", withoutEnd -> {
            return split(withoutEnd, new IndexSplitter("<", new FirstLocator()), tuple -> {
                return compileAllValues(tuple.right(), Main::compileType).map(outputParams -> {
                    return tuple.left() + "_" + outputParams;
                });
            });
        });
        if (maybeGeneric.isPresent()) return maybeGeneric;

        if (isSymbol(input)) return Optional.of(input);
        return invalidate("type", input);
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            final var c = input.charAt(i);
            if (!Character.isLetter(c)) return false;
        }

        return true;
    }

    private static Optional<String> truncateRight(String input, String suffix, Function<String, Optional<String>> mapper) {
        return input.endsWith(suffix)
                ? mapper.apply(input.substring(0, input.length() - suffix.length()))
                : Optional.empty();
    }

    private static Optional<String> split(
            String input,
            Splitter splitter,
            Function<Tuple<String, String>, Optional<String>> mapper
    ) {
        return splitter.split(input).flatMap(mapper);
    }
}
