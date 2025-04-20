package magma;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static class State {
        private final List<String> segments;
        private final Deque<Character> queue;
        private StringBuilder buffer;
        private int depth;

        private State(Deque<Character> queue, List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.queue = queue;
        }

        public State(Deque<Character> queue) {
            this(queue, new ArrayList<>(), new StringBuilder(), 0);
        }

        private State enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        private State exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        public char pop() {
            return this.queue.pop();
        }

        public char peek() {
            if (this.queue.isEmpty()) {
                return '\'';
            }
            return this.queue.peek();
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        return compileStatementValues(input, Main::compileRootSegment);
    }

    private static String compileStatementValues(String input, Function<String, String> compiler) {
        return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements);
    }

    private static String compileAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, String> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        State current = new State(queue);
        while (current.hasNext()) {
            char c = current.pop();
            State finalCurrent = current;
            current = foldDoubleQuotes(finalCurrent, c)
                    .or(() -> foldSingleQuotes(finalCurrent, c))
                    .orElseGet(() -> folder.apply(finalCurrent, c));
        }

        List<String> segments = current.advance().segments;

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            String compiled = compiler.apply(segment);
            output = merger.apply(output, compiled);
        }
        return output.toString();
    }

    private static Optional<State> foldSingleQuotes(State state, char c) {
        if (c != '\'') {
            return Optional.empty();
        }

        State appended = state.append(c);

        char maybeSlash = state.pop();
        State withMaybeSlash = appended.append(maybeSlash);

        State withEscaped = maybeSlash == '\\'
                ? withMaybeSlash.append(withMaybeSlash.pop())
                : withMaybeSlash;

        return Optional.of(withEscaped.append(withEscaped.pop()));
    }

    private static Optional<State> foldDoubleQuotes(State state, char c) {
        if (c != '\"') {
            return Optional.empty();
        }

        State appended = state.append(c);
        while (appended.hasNext()) {
            char next = appended.pop();
            appended = appended.append(next);

            if (next == '\\') {
                appended = appended.append(appended.pop());
            }
            if (next == '\"') {
                break;
            }
        }
        return Optional.of(appended);
    }

    private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    }

    private static State foldStatementChar(State state, char c) {
        State appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{' || c == '(') {
            return appended.enter();
        }
        if (c == '}' || c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static String compileRootSegment(String segment) {
        String stripped = segment.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        }

        return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileClass(String stripped, int depth) {
        int classIndex = stripped.indexOf("class ");
        if (classIndex < 0) {
            return Optional.empty();
        }

        return compileModifiers(stripped.substring(0, classIndex)).flatMap(modifiers -> {
            String afterKeyword = stripped.substring(classIndex + "class ".length());
            return compileClassWithModifiers(modifiers, afterKeyword, depth);
        });
    }

    private static Optional<String> compileClassWithModifiers(String modifiers, String afterKeyword, int depth) {
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }
        String className = afterKeyword.substring(0, contentStart).strip();
        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        String outputContent = compileStatementValues(inputContent, input -> compileClassSegment(input, depth + 1));

        String beforeNode = depth == 0 ? "" : "\n\t";
        String afterChildren = depth == 0 ? "" : "\n" + "\t".repeat(depth);
        return Optional.of(beforeNode + modifiers + " class " + className + " {" + outputContent + afterChildren + "}");
    }

    private static Optional<String> compileModifiers(String input) {
        List<String> modifiers = Arrays.stream(input.strip().split(" "))
                .map(String::strip)
                .toList();

        if (modifiers.stream().allMatch(Main::isText)) {
            return Optional.of(String.join(" ", modifiers));
        }
        else {
            return Optional.empty();
        }
    }

    private static String compileClassSegment(String input, int depth) {
        return compileWhitespace(input)
                .or(() -> compileClass(input, depth))
                .or(() -> compileDefinitionStatement(input))
                .or(() -> compileMethod(input, depth))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static @NotNull Optional<? extends String> compileMethod(String input, int depth) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return Optional.empty();
        }
        String beforeParams = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + "(".length());
        return compileDefinition(beforeParams)
                .or(() -> compileConstructorHeader(beforeParams))
                .flatMap(outputDefinition -> assembleMethod(outputDefinition, withParams.strip(), depth));
    }

    private static Optional<String> assembleMethod(String definition, String withParams, int depth) {
        int paramEnd = withParams.indexOf(")");
        if (paramEnd < 0) {
            return Optional.empty();
        }

        String params = withParams.substring(0, paramEnd).strip();
        String outputParams = compileValueSegments(params, Main::compileParam);

        String withBraces = withParams.substring(paramEnd + ")".length()).strip();
        if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
            String inputContent = withBraces.substring(1, withBraces.length() - 1);
            String outputContent = compileStatements(inputContent, depth + 1);
            String indent = "\n" + "\t".repeat(depth);
            return Optional.of(indent + definition + "(" + outputParams + "){" + outputContent + indent + "}");
        }
        else {
            return Optional.empty();
        }
    }

    private static String compileStatements(String inputContent, int depth) {
        return compileStatementValues(inputContent, input -> compileStatementOrBlock(input, depth));
    }

    private static String compileParam(String param) {
        return compileWhitespace(param)
                .or(() -> compileDefinition(param))
                .orElseGet(() -> generatePlaceholder(param));
    }

    private static String compileStatementOrBlock(String input, int depth) {
        return compileWhitespace(input)
                .or(() -> compileStatement(input, depth))
                .or(() -> compileBlock(input, depth))
                .orElseGet(() -> generatePlaceholder(input));

    }

    private static @NotNull Optional<? extends String> compileBlock(String input, int depth) {
        String stripped = input.strip();
        if (!stripped.endsWith("}")) {
            return Optional.empty();
        }
        String withoutEnd = stripped.substring(0, stripped.length() - "}".length());
        return findContentStart(withoutEnd).flatMap(contentStart -> {
            String beforeContent = withoutEnd.substring(0, contentStart).strip();
            String inputContent = withoutEnd.substring(contentStart + "{".length());
            String outputContent = compileStatements(inputContent, depth + 1);

            String indent = "\n" + "\t".repeat(depth);
            return compileBeforeBlock(beforeContent, depth)
                    .map(value -> indent + value + " {" + outputContent + indent + "}");
        });
    }

    private static Optional<Integer> findContentStart(String input) {
        LinkedList<Tuple<Integer, Character>> queue = IntStream.range(0, input.length())
                .mapToObj(index -> new Tuple<>(index, input.charAt(index)))
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            Tuple<Integer, Character> tuple = queue.pop();
            int i = tuple.left;
            char c = tuple.right;

            if (c == '\'') {
                if (queue.pop().right == '\\') {
                    queue.pop();
                }

                queue.pop();
                continue;
            }

            if (c == '{') {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }

    private static Optional<String> compileBeforeBlock(String input, int depth) {
        String stripped = input.strip();
        if (stripped.equals("try")) {
            return Optional.of("try");
        }

        return compileBeforeBlockWithQuantity(stripped, "catch", Main::compileDefinition)
                .or(() -> compileBeforeBlockWithQuantity(stripped, "while", value -> Optional.of(compileValue(value, depth))))
                .or(() -> compileBeforeBlockWithQuantity(stripped, "if", value -> Optional.of(compileValue(value, depth))))
                .or(() -> compileBeforeBlockWithQuantity(stripped, "for", quantity -> compileEnhancedForQuantity(quantity, depth)));
    }

    private static Optional<String> compileEnhancedForQuantity(String quantity, int depth) {
        int separator = quantity.indexOf(":");
        if (separator < 0) {
            return Optional.empty();
        }

        String inputDefinition = quantity.substring(0, separator);
        String value = quantity.substring(separator + ":".length());
        return compileDefinition(inputDefinition).map(outputDefinition -> outputDefinition + " : " + compileValue(value, depth));
    }

    private static Optional<String> compileBeforeBlockWithQuantity(String input, String keyword, Function<String, Optional<String>> compileDefinition) {
        String stripped = input.strip();
        String prefix = " (";

        if (!stripped.startsWith(keyword + prefix)) {
            return Optional.empty();
        }

        String withoutPrefix = stripped.substring((keyword + prefix).length()).strip();
        if (!withoutPrefix.endsWith(")")) {
            return Optional.empty();
        }

        String inputDefinition = withoutPrefix.substring(0, withoutPrefix.length() - ")".length());
        return compileDefinition.apply(inputDefinition).map(outputDefinition -> keyword + prefix + outputDefinition + ")");

    }

    private static Optional<String> compileStatement(String input, int depth) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return Optional.empty();
        }

        String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
        return compileStatementValue(withoutEnd, depth).map(destination -> "\n" + "\t".repeat(depth) + destination + ";");
    }

    private static Optional<String> compileStatementValue(String input, int depth) {
        String stripped = input.strip();
        if (stripped.endsWith("break")) {
            return Optional.of("break");
        }

        if (stripped.startsWith("throw ")) {
            String slice = stripped.substring("throw ".length());
            return Optional.of("throw " + compileValue(slice, depth));
        }

        if (stripped.startsWith("return ")) {
            String slice = stripped.substring("return ".length());
            return Optional.of("return " + compileValue(slice, depth));
        }

        int valueSeparator = stripped.indexOf("=");
        if (valueSeparator >= 0) {
            String inputDestination = stripped.substring(0, valueSeparator).strip();
            String outputDestination = compileDefinition(inputDestination).orElseGet(() -> compileValue(inputDestination, depth));

            String source = stripped.substring(valueSeparator + "=".length()).strip();
            return Optional.of(outputDestination + " = " + compileValue(source, depth));
        }

        return compileInvocation(stripped, depth);
    }

    private static Optional<String> compileInvocation(String input, int depth) {
        String stripped = input.strip();
        if (!stripped.endsWith(")")) {
            return Optional.empty();
        }

        String withoutArgumentsEnd = stripped.substring(0, stripped.length() - ")".length());
        return findArgumentsStart(withoutArgumentsEnd).flatMap(argumentsStart -> {
            String caller = withoutArgumentsEnd.substring(0, argumentsStart);
            String inputArguments = withoutArgumentsEnd.substring(argumentsStart + "(".length());
            String outputArguments = compileValues(inputArguments, depth);
            return Optional.of(compileValue(caller, depth) + "(" + outputArguments + ")");
        });
    }

    private static Optional<Integer> findArgumentsStart(String input) {
        int depth = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            if (c == '(' && depth == 0) {
                return Optional.of(i);
            }
            if (c == ')') {
                depth++;
            }
            if (c == '(') {
                depth--;
            }
        }

        return Optional.empty();
    }

    private static String compileValues(String inputArguments, int depth) {
        return compileValueSegments(inputArguments, input -> compileValue(input, depth));
    }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return Optional.of("");
        }
        return Optional.empty();
    }

    private static String compileValue(String input, int depth) {
        String stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return stripped;
        }

        if (stripped.startsWith("'") && stripped.endsWith("'")) {
            return stripped;
        }

        if (stripped.startsWith("new ")) {
            String withoutPrefix = stripped.substring("new ".length()).strip();
            if (withoutPrefix.endsWith(")")) {
                String withoutSuffix = withoutPrefix.substring(0, withoutPrefix.length() - ")".length());
                int argumentStart = withoutSuffix.indexOf("(");
                if (argumentStart >= 0) {
                    String type = withoutSuffix.substring(0, argumentStart).strip();
                    String arguments = withoutSuffix.substring(argumentStart + "(".length()).strip();
                    return "new " + compileTypeOrPlaceholder(type) + "(" + compileValues(arguments, depth) + ")";
                }
            }
        }

        if (stripped.startsWith("!")) {
            String slice = stripped.substring(1);
            return "!" + compileValue(slice, depth);
        }

        Optional<String> maybeLambda = compileLambda(depth, stripped);
        if (maybeLambda.isPresent()) {
            return maybeLambda.get();
        }

        if (isSymbol(stripped)) {
            return stripped;
        }

        if (isNumber(stripped)) {
            return stripped;
        }

        return compileInvocation(stripped, depth)
                .or(() -> compileTernary(stripped, depth))
                .or(() -> compileOperator(stripped, "!=", depth))
                .or(() -> compileOperator(stripped, "==", depth))
                .or(() -> compileOperator(stripped, "&&", depth))
                .or(() -> compileOperator(stripped, "+", depth))
                .or(() -> compileOperator(stripped, "<", depth))
                .or(() -> compileAccess(stripped, ".", depth))
                .or(() -> compileAccess(stripped, "::", depth))
                .orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileLambda(int depth, String stripped) {
        int arrowIndex = stripped.indexOf("->");
        if (arrowIndex < 0) {
            return Optional.empty();
        }
        String beforeArrow = stripped.substring(0, arrowIndex).strip();
        String afterArrow = stripped.substring(arrowIndex + "->".length());
        if (!beforeArrow.equals("()") && !isSymbol(beforeArrow)) {
            return Optional.empty();
        }
        String lambdaValue = compileLambdaValue(depth, afterArrow);
        return Optional.of(beforeArrow + " -> " + lambdaValue);
    }

    private static String compileLambdaValue(int depth, String afterArrow) {
        String input = afterArrow.strip();
        if (!input.startsWith("{") || !input.endsWith("}")) {
            return compileValue(input, depth);
        }

        String content = input.substring(1, input.length() - 1);
        String outputContent = compileStatements(content, depth + 1);
        return "{" + outputContent + "\n" +
                "\t".repeat(depth) +
                "}";
    }

    private static Optional<String> compileTernary(String stripped, int depth) {
        int conditionIndex = stripped.indexOf("?");
        if (conditionIndex < 0) {
            return Optional.empty();
        }
        String condition = stripped.substring(0, conditionIndex);
        String afterCondition = stripped.substring(conditionIndex + "?".length());

        int actionSeparator = afterCondition.indexOf(':');
        if (actionSeparator < 0) {
            return Optional.empty();
        }
        String left = afterCondition.substring(0, actionSeparator);
        String right = afterCondition.substring(actionSeparator + ":".length());

        return Optional.of(compileValue(condition, depth) + " ? " + compileValue(left, depth) + " : " + compileValue(right, depth));
    }

    private static Optional<String> compileOperator(String input, String operator, int depth) {
        int index = input.indexOf(operator);
        if (index >= 0) {
            String left = input.substring(0, index);
            String right = input.substring(index + operator.length());
            return Optional.of(compileValue(left, depth) + " " + operator + " " + compileValue(right, depth));
        }
        else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileAccess(String stripped, String separator, int depth) {
        int index = stripped.lastIndexOf(separator);
        if (index < 0) {
            return Optional.empty();
        }

        String parent = stripped.substring(0, index);
        String property = stripped.substring(index + separator.length());
        return Optional.of(compileValue(parent, depth) + separator + property);
    }

    private static boolean isNumber(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String compileTypeOrPlaceholder(String type) {
        return compileType(type).orElseGet(() -> generatePlaceholder(type));
    }

    private static Optional<String> compileConstructorHeader(String beforeParams) {
        int nameSeparator = beforeParams.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = beforeParams.substring(0, nameSeparator).strip();
            String name = beforeParams.substring(nameSeparator + " ".length()).strip();
            return compileModifiers(beforeName).flatMap(modifiers -> {
                return Optional.of(modifiers + " " + name);
            });
        }
        if (isSymbol(beforeParams)) {
            return Optional.of(beforeParams);
        }
        return Optional.empty();
    }

    private static @NotNull Optional<? extends String> compileDefinitionStatement(String input) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return Optional.empty();
        }

        String slice = stripped.substring(0, stripped.length() - ";".length()).strip();
        return compileDefinition(slice).flatMap(definition -> Optional.of("\n\t\t" + definition + ";"));
    }

    private static Optional<String> compileDefinition(String input) {
        int nameSeparator = input.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Optional.empty();
        }

        String beforeName = input.substring(0, nameSeparator).strip();
        String name = input.substring(nameSeparator + " ".length()).strip();
        return findTypeSeparator(beforeName).flatMap(typeSeparator -> {
            String beforeType = beforeName.substring(0, typeSeparator).strip();
            String inputType = beforeName.substring(typeSeparator + " ".length()).strip();
            return compileType(inputType).flatMap(outputType -> compileModifiers(beforeType).flatMap(modifiers -> {
                String withBeforeType = modifiers + " " + outputType;
                return Optional.of(withBeforeType + " " + name);
            }));
        }).or(() -> compileType(beforeName).map(type -> type + " " + name));
    }

    private static Optional<Integer> findTypeSeparator(String input) {
        int depth = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            if (c == ' ' && depth == 0) {
                return Optional.of(i);
            }
            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        }
        return Optional.empty();
    }

    private static Optional<String> compileType(String type) {
        String stripped = type.strip();
        if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length()))
                    .map(inner -> inner + "[]");
        }

        if (stripped.endsWith(">")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            int paramStart = withoutEnd.indexOf("<");
            if (paramStart >= 0) {
                String base = withoutEnd.substring(0, paramStart).strip();
                String oldArguments = withoutEnd.substring(paramStart + "<".length());
                String newArguments = compileValueSegments(oldArguments, Main::compileTypeOrPlaceholder);
                return Optional.of(base + "<" + newArguments + ">");
            }
        }

        if (isSymbol(stripped)) {
            return Optional.of(stripped);
        }

        return Optional.empty();
    }

    private static String compileValueSegments(String input, Function<String, String> compiler) {
        return compileAll(input, Main::foldValueChar, compiler, Main::mergeValues);
    }

    private static boolean isSymbol(String input) {
        if (input.equals("private") || input.equals("public")) {
            return false;
        }

        return isText(input);
    }

    private static boolean isText(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static State foldValueChar(State state, Character c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        State appended = state.append(c);
        if (c == '-') {
            if (state.peek() == '>') {
                return state.append(state.pop());
            }
        }

        if (c == '<' || c == '(' || c == '{') {
            return appended.enter();
        }
        if (c == '>' || c == ')' || c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("/*", "<content-start>")
                .replace("*/", "<content-end>");

        return "/* " + replaced + " */";
    }
}
