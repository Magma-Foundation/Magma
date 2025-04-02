package magma;

import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;
import magma.result.Results;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String string = Results.unwrap(compileRoot(input));
            Path target = source.resolveSibling("Main.c");
            Files.writeString(target, string);
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static Result<String, CompileException> compileRoot(String input) throws CompileException {
        return divideAndCompile(input, Main::compileRootSegment);
    }

    private static Result<String, CompileException> divideAndCompile(String input, Function<String, Result<String, CompileException>> compiler) {
        return compileAll(divideByStatements(input), compiler, Main::mergeStatements);
    }

    private static Result<String, CompileException> compileAll(List<String> segments, Function<String, Result<String, CompileException>> compiler, Function<Tuple<StringBuilder, String>, StringBuilder> merger) {
        Result<StringBuilder, CompileException> output = new Ok<>(new StringBuilder());
        for (String segment : segments) {
            output = output.and(() -> compiler.apply(segment)).mapValue(merger);
        }

        return output.mapValue(StringBuilder::toString);
    }

    private static StringBuilder mergeStatements(Tuple<StringBuilder, String> tuple) {
        return tuple.left().append(tuple.right());
    }

    private static List<String> divideByStatements(String input) {
        return divide(input, Main::divideStatementChar);
    }

    private static List<String> divide(String input, BiFunction<State, Character, State> divider) {
        State state = new State(IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new)));

        State current = state;
        while (!current.isEmpty()) {
            char c = current.pop();

            if (c == '\'') {
                current.append(c);

                char maybeSlash = current.pop();
                current.append(maybeSlash);
                if (maybeSlash == '\\') {
                    current.popAndAppend();
                }

                current.popAndAppend();
                continue;
            }

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

    private static Result<String, CompileException> compileRootSegment(String input) {
        Result<String, CompileException> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isOk()) return maybeWhitespace;

        if (input.startsWith("package ")) return new Ok<>("");

        String stripped = input.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String content = right.substring(0, right.length() - ";".length());
                String[] segments = content.split(Pattern.quote("."));
                String joined = String.join("/", segments);
                return new Ok<>("#include \"" + joined + ".h\"\n");
            }
        }

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    return divideAndCompile(inputContent, Main::compileClassSegment).mapValue(outputContent -> {
                        return "struct " + name + " {\n};\n" + outputContent;
                    });
                }
            }
        }

        return invalidate(input, "root segment");
    }

    private static Result<String, CompileException> invalidate(String input, String type) {
        return new Err<>(new CompileException(input, "Invalid " + type));
    }

    private static Result<String, CompileException> compileClassSegment(String input) {
        Result<String, CompileException> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isOk()) return maybeWhitespace;

        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String inputDefinition = input.substring(0, paramStart).strip();
            String withParams = input.substring(paramStart + 1);

            return compileDefinition(inputDefinition).flatMapValue(outputDefinition -> {
                int paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    return compileValues(withParams.substring(0, paramEnd), Main::compileDefinition).mapValue(newParams -> {
                        return outputDefinition + "(" +
                                newParams +
                                "){\n}\n";
                    });
                } else {
                    return createInfixError(withParams, ")");
                }
            });
        }

        return invalidate(input, "class segment");
    }

    private static Result<String, CompileException> compileValues(String input, Function<String, Result<String, CompileException>> compiler) {
        List<String> args = divide(input, Main::divideValueChar);
        return compileAll(args, compiler, Main::mergeValues);
    }

    private static State divideValueChar(State state, Character c) {
        if (c == ',' && state.isLevel()) return state.advance();

        State appended = state.append(c);
        if (c == '<') return appended.enter();
        if (c == '>') return appended.exit();
        return appended;
    }

    private static StringBuilder mergeValues(Tuple<StringBuilder, String> tuple) {
        return mergeDelimited(tuple, ", ");
    }

    private static StringBuilder mergeDelimited(Tuple<StringBuilder, String> tuple, String delimiter) {
        StringBuilder buffer = tuple.left();
        String element = tuple.right();

        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(delimiter).append(element);
    }

    private static Result<String, CompileException> compileDefinition(String definition) {
        int separator = definition.lastIndexOf(" ");
        if (separator < 0) return createInfixError(definition, " ");

        String beforeName = definition.substring(0, separator);

        Optional<Integer> maybeTypeSeparator = Optional.empty();
        int depth = 0;
        for (int i = beforeName.length() - 1; i >= 0; i--) {
            char c = beforeName.charAt(i);
            if (c == ' ' && depth == 0) {
                maybeTypeSeparator = Optional.of(i);
                break;
            } else {
                if (c == '>') depth++;
                if (c == '<') depth--;
            }
        }

        String innerType = maybeTypeSeparator
                .map(typeSeparator -> beforeName.substring(typeSeparator + " ".length()))
                .orElse(beforeName);

        String name = definition.substring(separator + 1).strip();
        return compileType(innerType).mapValue(outerType -> outerType + " " + name);
    }

    private static Result<String, CompileException> compileType(String input) {
        if (input.endsWith("[]")) {
            return new Ok<>("Array_" + input.substring(0, input.length() - "[]".length()));
        }

        if (input.endsWith(">")) {
            String withoutEnd = input.substring(0, input.length() - ">".length());
            int start = withoutEnd.indexOf("<");
            if (start >= 0) {
                String caller = withoutEnd.substring(0, start).strip();
                String arguments = withoutEnd.substring(start + "<".length());
                List<String> args = divide(arguments, Main::divideValueChar);
                return compileAll(args, Main::compileType, tuple -> mergeDelimited(tuple, "_")).mapValue(inner -> {
                    return caller + "__" + inner + "__";
                });
            }
        }

        String stripped = input.strip();
        if(isSymbol(stripped)) {
            return new Ok<>(stripped);
        }

        return invalidate("type", input);
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(Character.isLetter(c)) continue;
            return false;
        }

        return true;
    }

    private static Result<String, CompileException> createInfixError(String input, String infix) {
        return new Err<>(new CompileException("Infix '" + infix + "' not present", input));
    }

    private static Result<String, CompileException> compileWhitespace(String input) {
        if (input.isBlank()) return new Ok<>("");
        return new Err<>(new CompileException("Input not blank", input));
    }
}
