package magma;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    public record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }
    }

    public record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }
    }

    private static class State {
        private final Deque<Character> queue;
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

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private char pop() {
            return this.queue.pop();
        }

        private boolean hasElements() {
            return !this.queue.isEmpty();
        }

        private State exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private State enter() {
            this.depth = this.depth + 1;
            return this;
        }

        public List<String> segments() {
            return this.segments;
        }

        public char peek() {
            return this.queue.peek();
        }
    }

    private static final List<String> imports = new ArrayList<>();
    private static final List<String> structs = new ArrayList<>();
    private static final List<String> globals = new ArrayList<>();
    private static final List<String> methods = new ArrayList<>();
    private static int counter = 0;

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        magma.Files.readString(source)
                .match(input -> compileAndWrite(input, source), Optional::of)
                .ifPresent(Throwable::printStackTrace);
    }

    private static Optional<IOException> compileAndWrite(String input, Path source) {
        Path target = source.resolveSibling("main.c");
        String output = compile(input);
        return magma.Files.writeString(target, output);
    }

    private static String compile(String input) {
        List<String> segments = divide(input, Main::divideStatementChar);
        return parseAll(segments, Main::compileRootSegment)
                .map(list -> {
                    List<String> copy = new ArrayList<String>();
                    copy.addAll(imports);
                    copy.addAll(structs);
                    copy.addAll(globals);
                    copy.addAll(methods);
                    copy.addAll(list);
                    return copy;
                })
                .map(compiled -> mergeAll(compiled, Main::mergeStatements))
                .or(() -> generatePlaceholder(input)).orElse("");
    }

    private static Optional<String> compileStatements(String input, Function<String, Optional<String>> compiler) {
        return compileAndMerge(divide(input, Main::divideStatementChar), compiler, Main::mergeStatements);
    }

    private static Optional<String> compileAndMerge(List<String> segments, Function<String, Optional<String>> compiler, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return parseAll(segments, compiler).map(compiled -> mergeAll(compiled, merger));
    }

    private static String mergeAll(List<String> compiled, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return compiled.stream().reduce(new StringBuilder(), merger, (_, next) -> next).toString();
    }

    private static Optional<List<String>> parseAll(List<String> segments, Function<String, Optional<String>> compiler) {
        return segments.stream().reduce(Optional.of(new ArrayList<String>()), (maybeCompiled, segment) -> maybeCompiled.flatMap(allCompiled -> compiler.apply(segment).map(compiledSegment -> {
            allCompiled.add(compiledSegment);
            return allCompiled;
        })), (_, next) -> next);
    }

    private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    }

    private static List<String> divide(String input, BiFunction<State, Character, State> divider) {
        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        State state = new State(queue);
        while (state.hasElements()) {
            char c = state.pop();

            if (c == '\'') {
                state.append(c);
                char maybeSlash = state.pop();
                state.append(maybeSlash);

                if (maybeSlash == '\\') state.append(state.pop());
                state.append(state.pop());
                continue;
            }

            if (c == '\"') {
                state.append(c);

                while (state.hasElements()) {
                    char next = state.pop();
                    state.append(next);

                    if (next == '\\') state.append(state.pop());
                    if (next == '"') {
                        break;
                    }
                }

                continue;
            }

            state = divider.apply(state, c);
        }

        return state.advance().segments();
    }

    private static State divideStatementChar(State state, char c) {
        State appended = state.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '}' && isShallow(appended)) return appended.advance().exit();
        if (c == '{' || c == '(') return appended.enter();
        if (c == '}' || c == ')') return appended.exit();
        return appended;
    }

    private static boolean isShallow(State state) {
        return state.depth == 1;
    }

    private static Optional<String> compileRootSegment(String input) {
        if (input.startsWith("package ")) return Optional.of("");

        String stripped = input.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String content = right.substring(0, right.length() - ";".length());
                String joined = String.join("/", content.split(Pattern.quote(".")));
                imports.add("#include \"./" + joined + "\"\n");
                return Optional.of("");
            }
        }

        Optional<String> maybeClass = compileToStruct(input, "class ", new ArrayList<>());
        if (maybeClass.isPresent()) return maybeClass;

        return generatePlaceholder(input);
    }

    private static Optional<String> compileToStruct(String input, String infix, List<String> typeParams) {
        int classIndex = input.indexOf(infix);
        if (classIndex < 0) return Optional.empty();

        String afterKeyword = input.substring(classIndex + infix.length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart >= 0) {
            String name = afterKeyword.substring(0, contentStart).strip();
            String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
            if (withEnd.endsWith("}")) {
                String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                return compileStatements(inputContent, input1 -> compileClassMember(input1, typeParams)).map(outputContent -> {
                    structs.add("struct " + name + " {\n" + outputContent + "};\n");
                    return "";
                });
            }
        }
        return Optional.empty();
    }

    private static Optional<String> compileClassMember(String input, List<String> typeParams) {
        return compileWhitespace(input)
                .or(() -> compileToStruct(input, "interface ", typeParams))
                .or(() -> compileToStruct(input, "record ", typeParams))
                .or(() -> compileToStruct(input, "class ", typeParams))
                .or(() -> compileGlobalInitialization(input, typeParams))
                .or(() -> compileDefinitionStatement(input))
                .or(() -> compileMethod(input, typeParams))
                .or(() -> generatePlaceholder(input));
    }

    private static @NotNull Optional<String> compileDefinitionStatement(String input) {
        String stripped = input.strip();
        if (stripped.endsWith(";")) {
            String content = stripped.substring(0, stripped.length() - ";".length());
            return compileDefinition(content).map(result -> "\t" + result + ";\n");
        }
        return Optional.empty();
    }

    private static Optional<String> compileGlobalInitialization(String input, List<String> typeParams) {
        return compileInitialization(input, typeParams).map(generated -> {
            globals.add(generated + ";\n");
            return "";
        });
    }

    private static Optional<String> compileInitialization(String input, List<String> typeParams) {
        if (!input.endsWith(";")) return Optional.empty();

        String withoutEnd = input.substring(0, input.length() - ";".length());
        int valueSeparator = withoutEnd.indexOf("=");
        if (valueSeparator < 0) return Optional.empty();

        String definition = withoutEnd.substring(0, valueSeparator).strip();
        String value = withoutEnd.substring(valueSeparator + "=".length()).strip();
        return compileDefinition(definition).flatMap(outputDefinition -> {
            return compileValue(value, typeParams).map(outputValue -> {
                return outputDefinition + " = " + outputValue;
            });
        });
    }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) return Optional.of("");
        return Optional.empty();
    }

    private static Optional<String> compileMethod(String input, List<String> typeParams) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return Optional.empty();

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + "(".length());

        return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) return Optional.empty();

            String params = withParams.substring(0, paramEnd);
            return compileValues(params, definition -> {
                return compileWhitespace(definition)
                        .or(() -> compileDefinition(definition))
                        .or(() -> generatePlaceholder(definition));
            }).flatMap(outputParams -> {
                String header = "\t".repeat(0) + outputDefinition + "(" + outputParams + ")";
                String body = withParams.substring(paramEnd + ")".length()).strip();
                if (body.startsWith("{") && body.endsWith("}")) {
                    String inputContent = body.substring("{".length(), body.length() - "}".length());
                    return compileStatements(inputContent, input1 -> compileStatementOrBlock(input1, typeParams)).flatMap(outputContent -> {
                        methods.add(header + " {" + outputContent + "\n}\n");
                        return Optional.of("");
                    });
                }

                return Optional.of(header + ";");
            });
        });
    }

    private static Optional<String> compileValues(String input, Function<String, Optional<String>> compiler) {
        List<String> divided = divide(input, Main::divideValueChar);
        return compileValues(divided, compiler);
    }

    private static State divideValueChar(State state, char c) {
        if (c == '-') {
            if (state.peek() == '>') {
                state.pop();
                return state.append('-').append('>');
            }
        }

        if (c == ',' && state.isLevel()) return state.advance();

        State appended = state.append(c);
        if (c == '<' || c == '(') return appended.enter();
        if (c == '>' || c == ')') return appended.exit();
        return appended;
    }

    private static Optional<String> compileValues(List<String> params, Function<String, Optional<String>> compiler) {
        return compileAndMerge(params, compiler, Main::mergeValues);
    }

    private static Optional<String> compileStatementOrBlock(String input, List<String> typeParams) {
        return compileWhitespace(input)
                .or(() -> compileConditional(input, typeParams, "if "))
                .or(() -> compileConditional(input, typeParams, "while "))
                .or(() -> compileInitialization(input, typeParams).map(value -> "\n\t" + value + ";"))
                .or(() -> compileStatement(input, typeParams).map(result -> "\n\t" + result + ";"))
                .or(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileConditional(String input, List<String> typeParams, String prefix) {
        String stripped = input.strip();
        if (stripped.startsWith(prefix)) {
            String afterKeyword = stripped.substring(prefix.length()).strip();
            if (afterKeyword.startsWith("(")) {
                String withoutConditionStart = afterKeyword.substring(1);
                int conditionEnd = -1;
                int depth = 0;
                for (int i = 0; i < withoutConditionStart.length(); i++) {
                    char c = withoutConditionStart.charAt(i);
                    if (c == ')' && depth == 0) {
                        conditionEnd = i;
                        break;
                    }
                    if (c == '(') depth++;
                    if (c == ')') depth--;
                }

                if (conditionEnd >= 0) {
                    String oldCondition = withoutConditionStart.substring(0, conditionEnd).strip();
                    String withBraces = withoutConditionStart.substring(conditionEnd + ")".length()).strip();
                    return compileValue(oldCondition, typeParams).flatMap(newCondition -> {
                        if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                            String content = withBraces.substring(1, withBraces.length() - 1);
                            return compileStatements(content, statement -> compileStatement(statement, typeParams)).map(statements -> {
                                return "\n\t" + prefix + "(" + newCondition + ") " + statements;
                            });
                        } else {
                            return compileValue(withBraces, typeParams).map(result -> {
                                return "\n\t" + prefix + "(" + newCondition + ") " + result;
                            });
                        }
                    });
                }
            }
        }

        return Optional.empty();
    }

    private static Optional<String> compileStatement(String input, List<String> typeParams) {
        String stripped = input.strip();
        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            if (withoutEnd.startsWith("return ")) {
                return compileValue(withoutEnd.substring("return ".length()), typeParams).map(result -> "return " + result);
            }

            int valueSeparator = withoutEnd.indexOf("=");
            if (valueSeparator >= 0) {
                String destination = withoutEnd.substring(0, valueSeparator).strip();
                String source = withoutEnd.substring(valueSeparator + "=".length()).strip();
                return compileValue(destination, typeParams).flatMap(newDest -> {
                    return compileValue(source, typeParams).map(newSource -> {
                        return newDest + " = " + newSource;
                    });
                });
            }

            Optional<String> maybeInvocation = compileInvocation(withoutEnd, typeParams);
            if (maybeInvocation.isPresent()) return maybeInvocation;
        }

        return Optional.empty();
    }

    private static Optional<String> compileValue(String input, List<String> typeParams) {
        String stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return Optional.of(stripped);
        }

        if (stripped.startsWith("new ")) {
            String slice = stripped.substring("new ".length());
            int argsStart = slice.indexOf("(");
            if (argsStart >= 0) {
                String type = slice.substring(0, argsStart);
                String withEnd = slice.substring(argsStart + "(".length()).strip();
                if (withEnd.endsWith(")")) {
                    String argsString = withEnd.substring(0, withEnd.length() - ")".length());
                    return compileType(type, typeParams).flatMap(outputType -> compileArgs(argsString, typeParams).map(value -> outputType + value));
                }
            }
        }

        if (stripped.startsWith("!")) {
            return compileValue(stripped.substring(1), typeParams).map(result -> "!" + result);
        }

        Optional<String> value = compileLambda(stripped, typeParams);
        if (value.isPresent()) return value;

        Optional<String> invocation = compileInvocation(input, typeParams);
        if (invocation.isPresent()) return invocation;

        int methodIndex = stripped.lastIndexOf("::");
        if (methodIndex >= 0) {
            String type = stripped.substring(0, methodIndex).strip();
            String property = stripped.substring(methodIndex + "::".length()).strip();

            return generateLambdaWithReturn(Collections.emptyList(), "\n\treturn " + type + "." + property + "()").map(result -> result + ";");
        }

        int separator = input.lastIndexOf(".");
        if (separator >= 0) {
            String object = input.substring(0, separator).strip();
            String property = input.substring(separator + ".".length()).strip();
            return compileValue(object, typeParams).map(compiled -> compiled + "." + property);
        }

        if (isSymbol(stripped) || isNumber(stripped)) {
            return Optional.of(stripped);
        }

        return generatePlaceholder(input);
    }

    private static Optional<String> compileLambda(String input, List<String> typeParams) {
        int arrowIndex = input.indexOf("->");
        if (arrowIndex < 0) return Optional.empty();

        String beforeArrow = input.substring(0, arrowIndex).strip();
        List<String> paramNames;
        if (isSymbol(beforeArrow)) {
            paramNames = Collections.singletonList(beforeArrow);
        } else if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
            String inner = beforeArrow.substring(1, beforeArrow.length() - 1);
            paramNames = Arrays.stream(inner.split(Pattern.quote(",")))
                    .map(String::strip)
                    .toList();
        } else {
            return Optional.empty();
        }

        String value = input.substring(arrowIndex + "->".length()).strip();
        if (value.startsWith("{") && value.endsWith("}")) {
            String slice = value.substring(1, value.length() - 1);
            return compileStatements(slice, statement -> compileStatementOrBlock(statement, typeParams)).flatMap(result -> {
                return generateLambdaWithReturn(paramNames, result);
            });
        }

        return compileValue(value, typeParams).flatMap(newValue -> {
            return generateLambdaWithReturn(paramNames, "\n\treturn " + newValue + ";");
        });
    }

    private static Optional<String> generateLambdaWithReturn(List<String> paramNames, String returnValue) {
        int current = counter;
        counter++;
        String lambdaName = "__lambda" + current + "__";

        String joined = paramNames.stream()
                .map(name -> "auto " + name)
                .collect(Collectors.joining(", ", "(", ")"));
        methods.add("auto " + lambdaName + joined + " {" + returnValue + "\n}\n");
        return Optional.of(lambdaName);
    }

    private static boolean isNumber(String input) {
        return IntStream.range(0, input.length())
                .map(input::charAt)
                .allMatch(Character::isDigit);
    }

    private static Optional<String> compileInvocation(String input, List<String> typeParams) {
        String stripped = input.strip();
        if (stripped.endsWith(")")) {
            String sliced = stripped.substring(0, stripped.length() - ")".length());

            int argsStart = -1;
            int depth = 0;
            int i = sliced.length() - 1;
            while (i >= 0) {
                char c = sliced.charAt(i);
                if (c == '(' && depth == 0) {
                    argsStart = i;
                    break;
                }

                if (c == ')') depth++;
                if (c == '(') depth--;
                i--;
            }

            if (argsStart >= 0) {
                String type = sliced.substring(0, argsStart);
                String withEnd = sliced.substring(argsStart + "(".length()).strip();
                return compileValue(type, typeParams).flatMap(caller -> {
                    return compileArgs(withEnd, typeParams).map(value -> caller + value);
                });
            }
        }
        return Optional.empty();
    }

    private static Optional<String> compileArgs(String argsString, List<String> typeParams) {
        return compileValues(argsString, arg -> {
            return compileWhitespace(arg).or(() -> compileValue(arg, typeParams));
        }).map(args -> {
            return "(" + args + ")";
        });
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) return cache.append(element);
        return cache.append(", ").append(element);
    }

    private static Optional<String> compileDefinition(String definition) {
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator < 0) return Optional.empty();

        String beforeName = definition.substring(0, nameSeparator).strip();
        String name = definition.substring(nameSeparator + " ".length()).strip();
        if (!isSymbol(name)) return Optional.empty();

        int typeSeparator = -1;
        int depth = 0;
        int i = beforeName.length() - 1;
        while (i >= 0) {
            char c = beforeName.charAt(i);
            if (c == ' ' && depth == 0) {
                typeSeparator = i;
                break;
            } else {
                if (c == '>') depth++;
                if (c == '<') depth--;
            }
            i--;
        }

        if (typeSeparator >= 0) {
            String beforeType = beforeName.substring(0, typeSeparator).strip();

            List<String> typeParams;
            if (beforeType.endsWith(">")) {
                String withoutEnd = beforeType.substring(0, beforeType.length() - ">".length());
                int typeParamStart = withoutEnd.indexOf("<");
                if (typeParamStart >= 0) {
                    String substring = withoutEnd.substring(typeParamStart + 1);
                    typeParams = splitValues(substring);
                } else {
                    typeParams = Collections.emptyList();
                }
            } else {
                typeParams = Collections.emptyList();
            }

            String inputType = beforeName.substring(typeSeparator + " ".length());
            return compileType(inputType, typeParams).flatMap(outputType -> Optional.of(generateDefinition(typeParams, outputType, name)));
        } else {
            return compileType(beforeName, Collections.emptyList()).flatMap(outputType -> Optional.of(generateDefinition(Collections.emptyList(), outputType, name)));
        }
    }

    private static List<String> splitValues(String substring) {
        String[] paramsArrays = substring.strip().split(Pattern.quote(","));
        return Arrays.stream(paramsArrays)
                .map(String::strip)
                .filter(param -> !param.isEmpty())
                .toList();
    }

    private static String generateDefinition(List<String> maybeTypeParams, String type, String name) {
        String typeParamsString;
        if (maybeTypeParams.isEmpty()) {
            typeParamsString = "";
        } else {
            typeParamsString = "<" + String.join(", ", maybeTypeParams) + "> ";
        }

        return typeParamsString + type + " " + name;
    }

    private static Optional<String> compileType(String input, List<String> typeParams) {
        if (input.equals("void")) return Optional.of("void");

        if (input.equals("int") || input.equals("Integer") || input.equals("boolean") || input.equals("Boolean")) {
            return Optional.of("int");
        }

        if (input.equals("char") || input.equals("Character")) {
            return Optional.of("char");
        }

        if (input.endsWith("[]")) {
            return compileType(input.substring(0, input.length() - "[]".length()), typeParams)
                    .map(value -> value + "*");
        }

        String stripped = input.strip();
        if (isSymbol(stripped)) {
            if (typeParams.contains(stripped)) {
                return Optional.of(stripped);
            } else {
                return Optional.of("struct " + stripped);
            }
        }

        if (stripped.endsWith(">")) {
            String slice = stripped.substring(0, stripped.length() - ">".length());
            int argsStart = slice.indexOf("<");
            if (argsStart >= 0) {
                String base = slice.substring(0, argsStart).strip();
                String params = slice.substring(argsStart + "<".length()).strip();
                return compileValues(params, type -> {
                    return compileWhitespace(type).or(() -> compileType(type, typeParams));
                }).map(compiled -> {
                    return base + "<" + compiled + ">";
                });
            }
        }

        return generatePlaceholder(input);
    }

    private static boolean isSymbol(String input) {
        return IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .allMatch(Character::isLetter);
    }

    private static Optional<String> generatePlaceholder(String input) {
        return Optional.of("/* " + input + " */");
    }
}
