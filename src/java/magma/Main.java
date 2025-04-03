package magma;

import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;
import magma.result.Results;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final Map<String, String> expansions = new HashMap<>();

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
        List<String> segments = divideByStatements(input);

        return compileAllToList(segments, new Rule() {
            @Override
            public Result<String, CompileException> generate(Node input) {
                return new Ok<>(input.value());
            }

            @Override
            public Result<Node, CompileException> parse(String input) {
                return compile0(input).mapValue(Node::new);
            }

            private Result<String, CompileException> compile0(String input1) {
                return compileRootSegment(input1);
            }
        })
                .<List<String>>mapValue(Main::addExpansions)
                .mapValue(compiled -> mergeSegmentsAll(Main::mergeStatements, compiled));
    }

    private static ArrayList<String> addExpansions(List<String> list) {
        String joined = expansions.entrySet()
                .stream()
                .map(entry -> {
                    return "// expand " + entry.getKey() + " from " + entry.getValue() + "\n";
                })
                .collect(Collectors.joining());

        ArrayList<String> copy = new ArrayList<>();
        copy.add(joined);
        copy.addAll(list);
        return copy;
    }

    private static Result<String, CompileException> divideAndCompile(String input, Rule compiler) {
        return compileAll(divideByStatements(input), compiler, Main::mergeStatements);
    }

    private static Result<String, CompileException> compileAll(List<String> segments, Rule compiler, Function<Tuple<StringBuilder, String>, StringBuilder> merger) {
        Result<List<String>, CompileException> maybeCompiled = compileAllToList(segments, compiler);
        return maybeCompiled.mapValue(compiled -> mergeSegmentsAll(merger, compiled));
    }

    private static String mergeSegmentsAll(Function<Tuple<StringBuilder, String>, StringBuilder> merger, List<String> compiled) {
        StringBuilder output = new StringBuilder();
        for (String segment : compiled) {
            output = merger.apply(new Tuple<>(output, segment));
        }

        return output.toString();
    }

    private static Result<List<String>, CompileException> compileAllToList(List<String> segments, Rule compiler) {
        Result<List<String>, CompileException> maybeCompiled = new Ok<>(new ArrayList<String>());
        for (String segment : segments) {
            maybeCompiled = maybeCompiled.and(() -> compiler.parse(segment).flatMapValue(compiler::generate)).mapValue(tuple -> {
                tuple.left().add(tuple.right());
                return tuple.left();
            });
        }
        return maybeCompiled;
    }

    private static StringBuilder mergeStatements(Tuple<StringBuilder, String> tuple) {
        return tuple.left().append(tuple.right());
    }

    private static List<String> divideByStatements(String input) {
        return divide(input, Main::divideStatementChar);
    }

    private static List<String> divide(String input, BiFunction<State, Character, State> divider) {
        State current = new State(IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new)));

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

            if (c == '"') {
                current.append(c);

                State withNext = current;
                while (!withNext.isLevel()) {
                    char popped = withNext.pop();
                    withNext = withNext.append(popped);

                    if (popped == '\\') withNext = withNext.popAndAppend();
                    if (popped == '"') break;
                }
                current = withNext;
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
                    return divideAndCompile(inputContent, new Rule() {
                        @Override
                        public Result<String, CompileException> generate(Node input) {
                            return new Ok<>(input.value());
                        }

                        @Override
                        public Result<Node, CompileException> parse(String input) {
                            return compileClassSegment(input).mapValue(Node::new);
                        }
                    }).mapValue(outputContent -> {
                        return "struct " + name + " {\n};\n" + outputContent;
                    });
                }
            }
        }

        return invalidate(input, "root segment");
    }

    private static <T> Result<T, CompileException> invalidate(String input, String type) {
        return new Err<>(new CompileException("Invalid " + type, input));
    }

    private static Result<String, CompileException> compileClassSegment(String input) {
        Result<String, CompileException> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isOk()) return maybeWhitespace;

        Result<String, CompileException> maybeMethod = compileMethod(input);
        if (maybeMethod.isOk()) return maybeMethod;

        if (input.contains("=")) {
            return new Ok<>("int value = temp;\n");
        }

        return invalidate(input, "class segment");
    }

    private static Result<String, CompileException> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return createInfixError(input, "(");

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + 1);

        return compileDefinition(inputDefinition).flatMapValue(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                return compileValues(withParams.substring(0, paramEnd), new Rule() {
                    @Override
                    public Result<String, CompileException> generate(Node input) {
                        return new Ok<>(input.value());
                    }

                    @Override
                    public Result<Node, CompileException> parse(String input) {
                        return compile0(input).mapValue(Node::new);
                    }

                    private Result<String, CompileException> compile0(String definition) {
                        return compileDefinition(definition);
                    }
                }).mapValue(newParams -> {
                    return outputDefinition + "(" +
                            newParams +
                            "){\n}\n";
                });
            } else {
                return createInfixError(withParams, ")");
            }
        });
    }

    private static Result<String, CompileException> compileValues(String input, Rule compiler) {
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

        Rule typeRule = new TypeCompileRule();
        return typeRule.parse(innerType)
                .flatMapValue(typeRule::generate)
                .mapValue(outerType -> outerType + " " + name);
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) continue;
            return false;
        }

        return true;
    }

    private static <T> Result<T, CompileException> createInfixError(String input, String infix) {
        return new Err<>(new CompileException(input, "Infix '" + infix + "' not present"));
    }

    private static Result<String, CompileException> compileWhitespace(String input) {
        if (input.isBlank()) return new Ok<>("");
        return new Err<>(new CompileException(input, "Input not blank"));
    }

    private static class TypeCompileRule implements Rule {
        @Override
        public Result<String, CompileException> generate(Node input) {
            return new Ok<>(input.value());
        }

        @Override
        public Result<Node, CompileException> parse(String input) {
            if (input.endsWith("[]")) {
                String inner = input.substring(0, input.length() - "[]".length());
                return transformAndGenerateGeneric("Array", Collections.singletonList(inner));
            }

            Result<Node, CompileException> maybeGeneric = compileGeneric(input);
            if (maybeGeneric.isOk()) return maybeGeneric;

            Result<Node, CompileException> maybeSymbol = compileSymbol(input);
            if (maybeSymbol.isOk()) return maybeSymbol;

            return invalidate("type", input);
        }

        private Result<Node, CompileException> compileGeneric(String input) {
            if (!input.endsWith(">")) return new Err<>(new CompileException("Suffix '>' not present", input));

            String withoutEnd = input.substring(0, input.length() - ">".length());
            int start = withoutEnd.indexOf("<");
            if (start < 0) return createInfixError(withoutEnd, "<");

            String caller = withoutEnd.substring(0, start).strip();
            String arguments = withoutEnd.substring(start + "<".length());
            List<String> args = divide(arguments, Main::divideValueChar);

            return transformAndGenerateGeneric(caller, args);
        }

        private Result<Node, CompileException> transformAndGenerateGeneric(String caller, List<String> args) {
            return compileAllToList(args, new TypeCompileRule()).mapValue(segments -> {
                String inner = mergeSegmentsAll(tuple -> mergeDelimited(tuple, "_"), segments);

                String expansion = caller + "__" + inner + "__";
                expansions.put(expansion, caller + "<" + String.join(", ", segments) + ">");
                return new Node(expansion);
            });
        }

        private Result<Node, CompileException> compileSymbol(String input) {
            String stripped = input.strip();
            if (isSymbol(stripped)) {
                return new Ok<>(new Node(stripped));
            }

            return new Err<>(new CompileException("Not a symbol", stripped));
        }
    }
}
