package magma;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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

        Optional<T> findValue();

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        boolean isOk();
    }

    private interface Rule {
        Result<String, CompileError> compile(String input);
    }

    private interface DivideFolder {
        State fold(State state, Character c);
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

        private boolean hasNext() {
            return !queue.isEmpty();
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

        @Override
        public Optional<T> findValue() {
            return Optional.of(value);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(value);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(value);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(value));
        }

        @Override
        public boolean isOk() {
            return true;
        }
    }

    public record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }

        @Override
        public Optional<T> findValue() {
            return Optional.empty();
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(error));
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(error);
        }

        @Override
        public boolean isOk() {
            return false;
        }
    }

    private record Node(String type, String name) {
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record OrState(Optional<String> maybeValue, List<CompileError> errors) {
        public OrState() {
            this(Optional.empty(), Collections.emptyList());
        }

        public OrState withValue(String value) {
            return new OrState(Optional.of(value), errors);
        }

        public OrState withError(CompileError error) {
            List<CompileError> copy = new ArrayList<>(errors);
            copy.add(error);
            return new OrState(maybeValue, copy);
        }

        public Result<String, List<CompileError>> toResult() {
            return maybeValue.<Result<String, List<CompileError>>>map(Ok::new).orElseGet(() -> new Err<>(errors));
        }
    }

    private record TypeRule(String type, Rule childRule) implements Rule {
        @Override
        public Result<String, CompileError> compile(String input) {
            return childRule.compile(input)
                    .mapErr(err -> new CompileError("Not of type '" + type + "'", input, List.of(err)));
        }
    }

    private record OrRule(List<Rule> rules) implements Rule {
        @Override
        public Result<String, CompileError> compile(String input) {
            return rules.stream()
                    .reduce(new OrState(),
                            (orState, rule) -> fold(orState, rule, input),
                            (_, next) -> next)
                    .toResult()
                    .mapErr(errs -> new CompileError("No valid combination present", input, errs));
        }

        private OrState fold(OrState orState, Rule rule, String input) {
            return rule.compile(input).match(orState::withValue, orState::withError);
        }
    }

    static class CompileError {
        private final String message;
        private final String context;
        private final List<CompileError> children;

        public CompileError(String message, String context) {
            this(message, context, Collections.emptyList());
        }

        public CompileError(String message, String context, List<CompileError> children) {
            this.message = message;
            this.context = context;
            this.children = children;
        }

        public String display() {
            return format(0);
        }

        private String format(int depth) {
            return message + context + children.stream()
                    .map(compileError -> compileError.format(depth + 1))
                    .map(display -> "\n" + "\t".repeat(depth + 1) + display)
                    .collect(Collectors.joining());
        }
    }

    private static class InvocationRule implements Rule {
        @Override
        public Result<String, CompileError> compile(String input1) {
            if (!input1.endsWith(")")) return new Err<>(new CompileError("Suffix ')' not present", input1));
            String withoutEnd = input1.substring(0, input1.length() - ")".length());

            return findArgStart(withoutEnd)
                    .map(argsStart -> withArgStart(withoutEnd, argsStart))
                    .orElseGet(() -> new Err<>(new CompileError("No param start present", input1)));
        }

        private Result<String, CompileError> withArgStart(String withoutEnd, int argsStart) {
            String inputCaller = withoutEnd.substring(0, argsStart);
            String inputArguments = withoutEnd.substring(argsStart + "(".length());

            return new DivideRule(createValueRule(), Main::divideValues, Main::mergeValues).compile(inputArguments).flatMapValue(
                    outputArguments -> createValueRule().compile(inputCaller).mapValue(
                            outputCaller -> outputCaller + "(" + outputArguments + ")"));
        }

        private Optional<Integer> findArgStart(String withoutEnd) {
            int depth = 0;

            Deque<Tuple<Integer, Character>> queue = IntStream.range(0, withoutEnd.length())
                    .map(index -> withoutEnd.length() - index - 1)
                    .mapToObj(index -> new Tuple<>(index, withoutEnd.charAt(index)))
                    .collect(Collectors.toCollection(LinkedList::new));

            while (!queue.isEmpty()) {
                Tuple<Integer, Character> tuple = queue.pop();
                int i = tuple.left;
                char c = tuple.right;

                if (c == '"') {
                    queue.pop();

                    if (queue.isEmpty()) continue;
                    Tuple<Integer, Character> next = queue.peek();
                    if (next.right == '\\') {
                        queue.pop();
                    }

                    queue.pop();
                    continue;
                }

                if (c == '(' && depth == 0) {
                    return Optional.of(i);
                }

                if (c == ')') depth++;
                if (c == '(') depth--;
            }
            return Optional.empty();
        }
    }

    private record DivideRule(
            Rule rule,
            DivideFolder folder,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) implements Rule {
        @Override
        public Result<String, CompileError> compile(String input) {
            return Main.getString(divideAll(input, folder), rule(), merger());
        }
    }

    private static class DataAccessRule implements Rule {
        @Override
        public Result<String, CompileError> compile(String input1) {
            int accessSeparator = input1.lastIndexOf(".");
            if (accessSeparator < 0) return new Err<>(new CompileError("Infix '.' not present", input1));

            String oldChild = input1.substring(0, accessSeparator);
            String property = input1.substring(accessSeparator + ".".length()).strip();
            if (isSymbol(property)) {
                return createValueRule().compile(oldChild).mapValue(newChild -> newChild + "." + property);
            }

            return new Err<>(new CompileError("Not a symbol", property));
        }
    }

    private record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
        @Override
        public Result<String, CompileError> compile(String input) {
            int operatorIndex = input.indexOf(infix);
            if (operatorIndex < 0) {
                String format = "Infix '%s' not present";
                String message = format.formatted(infix);
                return new Err<>(new CompileError(message, input));
            }

            String leftString = input.substring(0, operatorIndex);
            String rightString = input.substring(operatorIndex + infix.length());
            return leftRule.compile(leftString)
                    .flatMapValue(left -> rightRule.compile(rightString).mapValue(right -> left + infix + right));
        }
    }

    private static class LazyRule implements Rule {
        private Optional<Rule> maybeChildRule = Optional.empty();

        public void set(Rule childRule) {
            this.maybeChildRule = Optional.of(childRule);
        }

        @Override
        public Result<String, CompileError> compile(String input) {
            return maybeChildRule
                    .map(rule -> rule.compile(input))
                    .orElseGet(() -> new Err<>(new CompileError("Child not set", input)));
        }
    }

    private static class EmptyRule implements Rule {
        @Override
        public Result<String, CompileError> compile(String input) {
            return input.isEmpty()
                    ? new Ok<>("")
                    : new Err<>(new CompileError("Not empty", input));
        }
    }

    private record StripRule(Rule childRule) implements Rule {
        @Override
        public Result<String, CompileError> compile(String input) {
            return childRule.compile(input.strip());
        }
    }

    private static class WrappedRule implements Rule {
        private final Function<String, Optional<String>> compiler;

        public WrappedRule(Function<String, Optional<String>> compiler) {
            this.compiler = compiler;
        }

        @Override
        public Result<String, CompileError> compile(String input) {
            return compiler.apply(input).<Result<String, CompileError>>map(Ok::new).orElseGet(() -> new Err<>(new CompileError("Invalid child", input)));
        }
    }

    private record SuffixRule(Rule childRule, String suffix) implements Rule {
        @Override
        public Result<String, CompileError> compile(String input) {
            if (input.endsWith(suffix)) {
                return childRule.compile(input.substring(0, input.length() - suffix.length()));
            } else {
                return new Err<>(new CompileError("Suffix '" + suffix + "' not present", input));
            }
        }
    }

    public static final List<String> RESERVED_KEYWORDS = List.of("private");
    private static int counter = 0;

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        Files.readString(source)
                .match(input -> runWithSource(source, input), Optional::of)
                .ifPresent(Throwable::printStackTrace);
    }

    private static Optional<IOException> runWithSource(Path source, String input) {
        String string = new DivideRule(input1 -> new WrappedRule(Main::compileRootSegment).compile(input1), Main::processStatementChar, Main::merge).compile(input).findValue().orElse("");
        Path target = source.resolveSibling("main.c");
        return Files.writeString(target, string + "int main(){\n\t__main__();\n\treturn 0;\n}\n");
    }

    private static Result<String, CompileError> getString(List<String> segments, Rule childRule, BiFunction<StringBuilder, String, StringBuilder> merger) {
        Result<StringBuilder, CompileError> maybeOutput = new Ok<>(new StringBuilder());
        for (String segment : segments) {
            maybeOutput = maybeOutput.flatMapValue(
                    output -> childRule.compile(segment).mapValue(
                            str -> merger.apply(output, str)));
        }

        return maybeOutput.mapValue(StringBuilder::toString);
    }

    private static StringBuilder merge(StringBuilder output, String str) {
        return output.append(str);
    }

    private static List<String> divideAll(String input, DivideFolder folder) {
        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        State state = new State(queue);
        while (state.hasNext()) {
            char c = state.pop();

            State finalState = state;
            state = divideSingleQuotes(state, c)
                    .or(() -> divideDoubleQuotes(finalState, c))
                    .orElseGet(() -> folder.fold(finalState, c));
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
        while (current.hasNext()) {
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
        Optional<String> maybeWhitespace = createWhitespaceRule().compile(input).findValue();
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
        return new DivideRule(input1 -> new WrappedRule(input2 -> createClassSegmentRule().compile(input2).findValue()).compile(input1), Main::processStatementChar, Main::merge).compile(inputContent).findValue()
                .map(outputContent -> "struct " + name + " {\n};\n" + outputContent);
    }

    private static Optional<String> invalidate(String type, String input) {
        System.err.println("Invalid " + type + ": " + input);
        return Optional.empty();
    }

    private static Rule createClassSegmentRule() {
        LazyRule classSegment = new LazyRule();
        classSegment.set(new OrRule(List.of(
                createWhitespaceRule(),
                new WrappedRule(Main::compileClass),
                new WrappedRule(Main::compileMethod0),
                new WrappedRule(Main::compileConstructor0),
                new WrappedRule(Main::compileDefinitionStatement0)
        )));
        return classSegment;
    }

    private static StripRule createWhitespaceRule() {
        return new StripRule(new EmptyRule());
    }

    private static Optional<String> compileDefinitionStatement0(String input) {
        if (input.endsWith(";")) {
            return Optional.of("int temp;\n");
        }
        return Optional.empty();
    }

    private static Optional<String> compileConstructor0(String input) {
        if (input.contains("(")) {
            return generateStructType("Temp")
                    .flatMap(type -> generateDefinition(new Node(type, "new")))
                    .map(definition -> generateMethod(definition, ""));
        }
        return Optional.empty();
    }

    private static Optional<String> compileMethod0(String input) {
        return compileMethod(input, "Temp");
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
            return new DivideRule(input1 -> new WrappedRule(Main::compileStatement).compile(input1), Main::processStatementChar, Main::merge).compile(inputContent).findValue().map(
                    outputContent -> generateMethod(definition, outputContent));
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

        return createTypeRule()
                .compile(inputType)
                .findValue().map(outputType -> new Node(outputType, oldName));
    }

    private static Node modifyDefinition(String structName, Node node) {
        String newName = node.name.equals("main")
                ? "__main__"
                : structName + "_" + node.name;

        return new Node(node.type, newName);
    }

    private static Optional<String> compileStatement(String input) {
        return createStatementRule().compile(input).findValue();
    }

    private static OrRule createStatementRule() {
        return new OrRule(List.of(
                createWhitespaceRule(),
                new StripRule(new WrappedRule(Main::compileIf)),
                new StripRule(new WrappedRule(Main::compileReturn)),
                new StripRule(new WrappedRule(Main::compileFor)),
                new WrappedRule(Main::compileInitialization),
                new SuffixRule(new InvocationRule(), ";"),
                new WrappedRule(Main::compileWhile)
        ));
    }

    private static Optional<String> compileIf(String stripped1) {
        return stripped1.startsWith("if ")
                ? Optional.of("\n\tif (1) {\n\t}")
                : Optional.empty();
    }

    private static Optional<String> compileReturn(String stripped1) {
        return stripped1.startsWith("return ")
                ? Optional.of("\n\treturn temp;")
                : Optional.empty();
    }

    private static Optional<String> compileFor(String stripped1) {
        return stripped1.startsWith("for ")
                ? Optional.of("\n\tfor(;;){\n\t}")
                : Optional.empty();
    }

    private static Optional<String> compileWhile(String stripped) {
        if (stripped.startsWith("while ")) return Optional.of("\n\twhile(1) {\n\t}\n");
        return Optional.empty();
    }

    private static Optional<String> compileInitialization(String stripped) {
        if (!stripped.endsWith(";")) return Optional.empty();

        String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
        int valueSeparator = withoutEnd.indexOf("=");
        if (valueSeparator < 0) return Optional.empty();

        String inputDefinition = withoutEnd.substring(0, valueSeparator).strip();
        String value = withoutEnd.substring(valueSeparator + "=".length());

        return parseDefinition(inputDefinition)
                .flatMap(Main::generateDefinition)
                .flatMap(outputDefinition -> {
                    return createValueRule().compile(value).mapValue(outputValue -> "\n\t" +
                                    outputDefinition +
                                    " = " + outputValue + ";")
                            .findValue();
                });
    }

    private static Rule createValueRule() {
        LazyRule value = new LazyRule();
        value.set(new OrRule(List.of(
                new WrappedRule(Main::compileConstructor),
                new TypeRule("invocation", new InvocationRule()),
                new Rule() {
                    @Override
                    public Result<String, CompileError> compile(String input1) {
                        return compile0(input1)
                                .<Result<String, CompileError>>map(Ok::new)
                                .orElseGet(() -> new Err<>(new CompileError("Invalid", input1)));
                    }

                    private Optional<String> compile0(String value1) {
                        return compileTernary(value1);
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileError> compile(String input1) {
                        return compile0(input1)
                                .<Result<String, CompileError>>map(Ok::new)
                                .orElseGet(() -> new Err<>(new CompileError("Invalid", input1)));
                    }

                    private Optional<String> compile0(String stripped) {
                        return compileSymbol(stripped);
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileError> compile(String input1) {
                        return compile0(input1)
                                .<Result<String, CompileError>>map(Ok::new)
                                .orElseGet(() -> new Err<>(new CompileError("Invalid", input1)));
                    }

                    private Optional<String> compile0(String stripped) {
                        return compileString(stripped);
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileError> compile(String input1) {
                        return compile0(input1)
                                .<Result<String, CompileError>>map(Ok::new)
                                .orElseGet(() -> new Err<>(new CompileError("Invalid", input1)));
                    }

                    private Optional<String> compile0(String stripped) {
                        return compileNumber(stripped);
                    }
                },
                new DataAccessRule(),
                new Rule() {
                    @Override
                    public Result<String, CompileError> compile(String input1) {
                        return compile0(input1)
                                .<Result<String, CompileError>>map(Ok::new)
                                .orElseGet(() -> new Err<>(new CompileError("Invalid", input1)));
                    }

                    private Optional<String> compile0(String value1) {
                        return compileMethodAccess(value1);
                    }
                },
                new InfixRule(value, "-", value),
                new InfixRule(value, "+", value)
        )));
        return value;
    }

    private static Optional<String> compileSymbol(String stripped) {
        if (isSymbol(stripped)) {
            return Optional.of(stripped);
        }
        return Optional.empty();
    }

    private static Optional<String> compileString(String stripped) {
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return Optional.of(stripped);
        }
        return Optional.empty();
    }

    private static Optional<String> compileNumber(String stripped) {
        if (isNumber(stripped)) {
            return Optional.of(stripped);
        }
        return Optional.empty();
    }

    private static Optional<String> compileMethodAccess(String value) {
        if (value.contains("::")) {
            int index = counter;
            counter++;
            return Optional.of("__lambda" + index + "__");
        }
        return Optional.empty();
    }

    private static Optional<String> compileTernary(String value) {
        if (value.contains("?")) return Optional.of("condition ? whenTrue : whenFalse");
        return Optional.empty();
    }

    private static Optional<String> compileConstructor(String input) {
        if (input.startsWith("new ")) return Optional.of("Temp()");
        return Optional.empty();
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

    private static OrRule createTypeRule() {
        return new OrRule(List.of(new WrappedRule(Main::compileSymbol0), new WrappedRule(Main::compileGeneric)));
    }

    private static Optional<String> compileSymbol0(String type) {
        String stripped = type.strip();
        if (!isSymbol(stripped)) return Optional.empty();

        if (RESERVED_KEYWORDS.contains(stripped)) return Optional.empty();

        return generateStructType(stripped);
    }

    private static Optional<String> compileGeneric(String type) {
        if (type.endsWith(">")) {
            return generateStructType("Temp");
        }
        return Optional.empty();
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
}

