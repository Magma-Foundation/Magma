package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    public interface Iterator<T> {
        <C> C collect(Collector<T, C> collector);

        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> map(Function<T, R> mapper);
    }

    public interface List<T> {
        Iterator<T> iter();

        List<T> add(T element);

        T get(int index);

        boolean contains(T element);
    }

    public interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        T orElse(T other);

        boolean isEmpty();

        boolean isPresent();

        T orElseGet(Supplier<T> other);

        Option<T> or(Supplier<Option<T>> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);
    }

    private interface Head<T> {
        Option<T> next();
    }

    record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }
    }

    private static class DivideState {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(Lists.empty(), new StringBuilder(), 0);
        }

        private DivideState exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private DivideState enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private DivideState advance() {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        public List<String> segments() {
            return this.segments;
        }
    }

    record Tuple<A, B>(A left, B right) {
    }

    record CompileState(List<String> imports, List<String> structs, List<Node> generics) {
        public CompileState() {
            this(Lists.empty(), Lists.empty(), Lists.empty());
        }

        public CompileState addStruct(String struct) {
            this.structs.add(struct);
            return this;
        }

        public CompileState addImport(String imports) {
            this.imports.add(imports);
            return this;
        }

        public CompileState addGeneric(Node node) {
            if (!this.generics.contains(node)) {
                this.generics.add(node);
            }
            return this;
        }
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    }

    public record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Option<R> option = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (option.isEmpty()) {
                    return current;
                }
                current = option.orElse(null);
            }
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }
    }

    public static final class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter >= this.length) {
                return new None<>();
            }

            int value = this.counter;
            this.counter++;
            return new Some<>(value);
        }
    }

    public record Node(Option<String> type, Map<String, String> strings, Map<String, List<Node>> nodeLists) {
        public Node() {
            this(new None<String>(), new HashMap<>(), new HashMap<>());
        }

        public Node(String type) {
            this(new Some<String>(type), new HashMap<>(), new HashMap<>());
        }

        public Node withString(String propertyKey, String propertyValue) {
            this.strings.put(propertyKey, propertyValue);
            return this;
        }

        public Node withNodeList(String propertyKey, List<Node> propertyValues) {
            this.nodeLists.put(propertyKey, propertyValues);
            return this;
        }

        public Option<List<Node>> findNodeList(String propertyKey) {
            if (this.nodeLists.containsKey(propertyKey)) {
                return new Some<>(this.nodeLists.get(propertyKey));
            }
            return new None<>();
        }

        public Option<String> findString(String propertyKey) {
            if (this.strings.containsKey(propertyKey)) {
                return new Some<>(this.strings.get(propertyKey));
            }
            else {
                return new None<>();
            }
        }

        public boolean is(String type) {
            return this.type.isPresent() && this.type.orElse(null).equals(type);
        }
    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return Lists.empty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.add(element);
        }
    }

    static class None<T> implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other.get();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }
    }

    public static void main(String[] args) {
        try {
            Path path = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(path);

            Path target = path.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        CompileState oldState = new CompileState();
        Tuple<CompileState, String> output = compileStatements(oldState, input, Main::compileRootSegment);
        CompileState newState = output.left;

        String joinedImports = join(newState.imports);
        String joinedStructs = join(newState.structs);
        String joinedGenerics = newState.generics.iter()
                .map(Main::generateGenericType)
                .map(generic -> "// " + generic + "\n")
                .collect(new Joiner())
                .orElse("");

        return joinedImports + joinedGenerics + joinedStructs + output.right;
    }

    private static String join(List<String> list) {
        return list.iter()
                .collect(new Joiner())
                .orElse("");
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler) {
        return compileAll(state, input, Main::foldStatementChar, compiler, Main::mergeStatements);
    }

    private static Tuple<CompileState, String> compileAll(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> divider, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        Tuple<CompileState, List<String>> compiled = parseAll(state, input, divider, compiler);
        return new Tuple<>(compiled.left, mergeAll(merger, compiled));
    }

    private static Tuple<CompileState, List<String>> parseAll(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> divider,
            BiFunction<CompileState, String, Tuple<CompileState, String>> compiler
    ) {
        return divide(input, divider).iter().fold(new Tuple<CompileState, List<String>>(state, Lists.empty()),
                (current, element) -> parseElement(compiler, current, element));
    }

    private static Tuple<CompileState, List<String>> parseElement(BiFunction<CompileState, String, Tuple<CompileState, String>> compiler, Tuple<CompileState, List<String>> current, String element) {
        CompileState currentState = current.left;
        List<String> currentCache = current.right;

        Tuple<CompileState, String> compiledTuple = compiler.apply(currentState, element);
        CompileState newState = compiledTuple.left;
        String compiled = compiledTuple.right;

        return new Tuple<>(newState, currentCache.add(compiled));
    }

    private static String mergeAll(BiFunction<StringBuilder, String, StringBuilder> merger, Tuple<CompileState, List<String>> fold) {
        return fold.right.iter().fold(new StringBuilder(), merger).toString();
    }

    private static StringBuilder mergeStatements(StringBuilder current, String statement) {
        return current.append(statement);
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        DivideState current = new DivideState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            current = folder.apply(current, c);
        }

        return current.advance().segments;
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
        DivideState appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        if (input.startsWith("package ")) {
            return new Tuple<>(state, "");
        }
        if (input.strip().startsWith("import ")) {
            return new Tuple<>(state.addImport("// #include <temp.h>\n"), "");
        }

        return compileClass(state, input).orElseGet(() -> compileContent(state, input));
    }

    private static Tuple<CompileState, String> compileContent(CompileState state, String input) {
        return new Tuple<>(state, generatePlaceholder(input));
    }

    private static Option<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        return compileStructured("class ", state, input);
    }

    private static Option<Tuple<CompileState, String>> compileStructured(String infix, CompileState state, String input) {
        int classIndex = input.indexOf(infix);
        if (classIndex < 0) {
            return new None<>();
        }

        String modifiers = input.substring(0, classIndex).strip();
        String afterKeyword = input.substring(classIndex + infix.length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }
        String beforeContent = afterKeyword.substring(0, contentStart).strip();

        int typeParamStart = beforeContent.indexOf("<");
        String name = typeParamStart >= 0
                ? beforeContent.substring(0, beforeContent.indexOf("<"))
                : beforeContent;

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        Tuple<CompileState, String> content = compileStatements(state, inputContent, Main::compileClassSegment);

        String format = "%s struct %s {%s\n};\n";
        String message = format.formatted(generatePlaceholder(modifiers), name, content.right);
        return new Some<>(new Tuple<CompileState, String>(content.left.addStruct(message), ""));
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileWhitespace(state, input)
                .or(() -> compileClass(state, input))
                .or(() -> compileStructured("interface ", state, input))
                .or(() -> compileMethod(state, input))
                .or(() -> compileDefinitionStatement(state, input))
                .orElseGet(() -> compileContent(state, input));
    }

    private static Option<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String definition = input.substring(0, paramStart);
            String withParams = input.substring(paramStart + "(".length());
            return compileDefinition(state, definition).flatMap(outputDefinition -> {
                int paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    String oldParams = withParams.substring(0, paramEnd);
                    Tuple<CompileState, String> newParams = compileValues(outputDefinition.left, oldParams, Main::compileParameter);

                    String oldBody = withParams.substring(paramEnd + ")".length());
                    String newBody = oldBody.equals(";") ? ";" : generatePlaceholder(oldBody);

                    String generated = "\n\t" + outputDefinition.right + "(" + newParams.right + ")" + newBody;
                    Tuple<CompileState, String> value = new Tuple<>(newParams.left, generated);
                    return new Some<>(value);
                }
                else {
                    return new None<>();
                }
            });
        }
        return new None<>();
    }

    private static Tuple<CompileState, String> compileValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler) {
        return compileAll(state, input, Main::compileValueChar, compiler, Main::mergeValues);
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static DivideState compileValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        DivideState appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple<CompileState, String> compileParameter(CompileState state, String element) {
        return compileWhitespace(state, element)
                .or(() -> compileDefinition(state, element))
                .orElseGet(() -> compileContent(state, element));
    }

    private static Option<Tuple<CompileState, String>> compileWhitespace(CompileState state, String element) {
        if (element.isBlank()) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return new None<>();
        }
        String inputDefinition = stripped.substring(0, stripped.length() - ";".length());
        return compileDefinition(state, inputDefinition).map(outputDefinition -> {
            return new Tuple<>(outputDefinition.left, "\n\t" + outputDefinition.right + ";");
        });
    }

    private static Option<Tuple<CompileState, String>> compileDefinition(CompileState state, String definition) {
        String withoutEnd = definition.strip();
        int nameSeparator = withoutEnd.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }
        String beforeName = withoutEnd.substring(0, nameSeparator).strip();
        String name = withoutEnd.substring(nameSeparator + " ".length());

        return findTypeSeparator(beforeName).map(typeSeparator -> {
            String beforeType = beforeName.substring(0, typeSeparator).strip();
            String typeString = beforeName.substring(typeSeparator + " ".length());

            Tuple<CompileState, Node> value = parseType(state, typeString);
            CompileState withType = value.left;
            Node type = value.right;

            if (type.is("functional")) {
                return new Some<>(new Tuple<CompileState, String>(withType, generateFunctional(type.withString("name", name))));
            }

            Tuple<CompileState, String> typeTuple = new Tuple<>(withType, generateType(type).orElse(""));
            String outputBeforeName = generatePlaceholder(beforeType) + " " + typeTuple.right;
            return generateDefinition(typeTuple.left, outputBeforeName, name);
        }).orElseGet(() -> {
            Tuple<CompileState, Node> value = parseType(state, beforeName);
            CompileState withType = value.left;
            Node type = value.right;
            if (type.is("functional")) {
                return new Some<>(new Tuple<CompileState, String>(withType, generateFunctional(type.withString("name", name))));
            }
            Tuple<CompileState, String> generated = new Tuple<>(withType, generateType(type).orElse(""));
            return generateDefinition(generated.left, generated.right, name);
        });
    }

    private static Tuple<CompileState, Node> parseType(CompileState state, String input) {
        return parseGenericType(state, input)
                .or(() -> parsePrimitiveType(state, input))
                .orElseGet(() -> parseContent(state, input));
    }

    private static Option<Tuple<CompileState, Node>> parsePrimitiveType(CompileState state, String input) {
        if (input.strip().equals("int")) {
            return new Some<>(new Tuple<CompileState, Node>(state, new Node("primitive").withString("value", "int")));
        }
        else {
            return new None<>();
        }
    }

    private static Tuple<CompileState, Node> parseContent(CompileState state, String input) {
        return new Tuple<>(state, new Node("content").withString("value", generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, Node>> parseGenericType(CompileState state, String input) {
        String stripped = input.strip();
        if (!stripped.endsWith(">")) {
            return new None<>();
        }

        String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
        int argumentsStart = withoutEnd.indexOf("<");
        if (argumentsStart < 0) {
            return new None<>();
        }

        String base = withoutEnd.substring(0, argumentsStart).strip();
        String params = withoutEnd.substring(argumentsStart + "<".length());
        Tuple<CompileState, List<String>> compiled = parseAll(state, params, Main::compileValueChar, (state1, input1) -> {
            Tuple<CompileState, Node> value = parseType(state1, input1);
            return new Tuple<>(value.left, generateType(value.right).orElse(""));
        });

        CompileState newState = compiled.left;
        List<String> newValues = compiled.right;

        Tuple<CompileState, Node> value = modifyToFunctionalType("BiFunction", base, newState, () -> Lists.of(newValues.get(0), newValues.get(1)), () -> newValues.get(2))
                .or(() -> modifyToFunctionalType("Function", base, newState, () -> Lists.of(newValues.get(0)), () -> newValues.get(1)))
                .or(() -> modifyToFunctionalType("Supplier", base, newState, Lists::empty, () -> newValues.get(0)))
                .orElseGet(() -> {
                    String newTypes = mergeAll(Main::mergeValues, compiled);
                    Node node = new Node("generic").withString("base", base).withString("arguments", newTypes);
                    CompileState withGeneric = newState.addGeneric(node);
                    return new Tuple<>(withGeneric, node);
                });

        return new Some<>(value);
    }

    private static Option<Tuple<CompileState, Node>> modifyToFunctionalType(String expectedBase, String actualBase, CompileState state, Supplier<List<String>> arguments, Supplier<String> returns) {
        if (!actualBase.equals(expectedBase)) {
            return new None<>();
        }

        final List<Node> argumentNodes = arguments.get().iter()
                .map(argument -> new Node().withString("argument", argument))
                .collect(new ListCollector<>());

        Node node = new Node("functional")
                .withString("returns", returns.get())
                .withNodeList("arguments", argumentNodes);

        return new Some<>(new Tuple<CompileState, Node>(state, node));
    }

    private static Option<String> generateType(Node node) {
        String generated;
        if (node.is("functional")) {
            generated = generateFunctional(node);
        }
        else if (node.is("generic")) {
            generated = generateGenericType(node);
        }
        else if (node.is("primitive")) {
            generated = node.findString("value").orElse("");
        }
        else {
            generated = generateContent(node);
        }
        return new Some<>(generated);
    }

    private static String generateContent(Node node) {
        return node.findString("value").orElse("");
    }

    private static String generateGenericType(Node node) {
        String base = node.findString("base").orElse("");
        String arguments = node.findString("arguments").orElse("");
        return base + "<" + arguments + ">";
    }

    private static String generateFunctional(Node node) {
        String arguments = node.findNodeList("arguments")
                .orElse(Lists.empty())
                .iter()
                .map(argument -> argument.findString("argument").orElse(""))
                .collect(new Joiner(", "))
                .orElse("");

        String returns = node.findString("returns").orElse("");
        String name = node.findString("name").orElse("");
        return "%s(*%s)(%s)".formatted(returns, name, arguments);
    }

    private static Option<Integer> findTypeSeparator(String input) {
        int depth = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            if (c == ' ' && depth == 0) {
                return new Some<>(i);
            }
            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> generateDefinition(
            CompileState state,
            String beforeName,
            String name
    ) {
        String generated = beforeName + " " + name;
        return new Some<>(new Tuple<CompileState, String>(state, generated));
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
