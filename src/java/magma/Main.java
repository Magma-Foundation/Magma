package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static magma.Lists.listEmpty;
import static magma.Lists.listFromArray;

public class Main {
    public interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isPresent();

        T orElse(T other);

        boolean isEmpty();

        T orElseGet(Supplier<T> supplier);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        Option<T> or(Supplier<Option<T>> supplier);
    }

    public interface List<T> {
        List<T> add(T element);

        Iterator<T> iter();

        boolean isEmpty();

        boolean contains(T element);

        Option<Integer> indexOf(T element);

        T get(int index);

        int size();

        List<T> subList(int startInclusive, int endExclusive);

        T last();
    }

    private interface Head<T> {
        Option<T> next();
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Defined {
        String generate();
    }

    public record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public boolean isPresent() {
            return true;
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
        public T orElseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public Option<T> or(Supplier<Option<T>> supplier) {
            return this;
        }
    }

    public record None<T>() implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public boolean isPresent() {
            return false;
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
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> supplier) {
            return supplier.get();
        }
    }

    public record Iterator<T>(Head<T> head) {
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        public <C> C collect(Collector<T, C> collector) {
            return this.foldRight(collector.createInitial(), collector::fold);
        }

        private <R> R foldRight(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (optional.isPresent()) {
                    current = optional.orElse(null);
                }
                else {
                    return current;
                }
            }
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    public static class RangeHead implements Head<Integer> {
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

            var value = this.counter;
            this.counter++;
            return new Some<>(value);
        }
    }

    private record State(String input, List<String> segments, String buffer, int depth, int index) {
        private static State fromInput(String input) {
            return new State(input, listEmpty(), "", 0, 0);
        }

        private Option<Tuple<Character, State>> popAndAppendToTuple() {
            return this.pop().map(tuple -> {
                var poppedChar = tuple.left;
                var poppedState = tuple.right;
                var appended = poppedState.append(poppedChar);
                return new Tuple<>(poppedChar, appended);
            });
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private State enter() {
            return new State(this.input, this.segments, this.buffer, this.depth + 1, this.index);
        }

        private State exit() {
            return new State(this.input, this.segments, this.buffer, this.depth - 1, this.index);
        }

        private State advance() {
            return new State(this.input, this.segments.add(this.buffer), "", this.depth, this.index);
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private Option<Tuple<Character, State>> pop() {
            if (this.index >= this.input.length()) {
                return new None<>();
            }

            var escaped = this.input.charAt(this.index);
            return new Some<>(new Tuple<Character, State>(escaped, new State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
        }

        private State append(char c) {
            return new State(this.input, this.segments, this.buffer + c, this.depth, this.index);
        }

        public Option<State> popAndAppend() {
            return this.popAndAppendToTuple().map(Tuple::right);
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

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return listEmpty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.add(element);
        }
    }

    private record Definition(Option<String> beforeType, String type, String name) implements Defined {
        @Override
        public String generate() {
            var joined = this.beforeType().map(Main::generatePlaceholder).map(inner -> inner + " ").orElse("");
            return joined + " " + this.type() + " " + this.name();
        }
    }

    private record Content(String input) implements Defined {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }
    }

    private class Whitespace implements Defined {
        @Override
        public String generate() {
            return "";
        }
    }

    public static final Map<String, Function<List<String>, Option<String>>> expandables = new HashMap<>();
    private static final List<String> methods = listEmpty();
    private static final List<String> structs = listEmpty();
    private static String functionName = "";
    private static List<String> typeParameters = listEmpty();
    private static List<Tuple<String, List<String>>> expansions = listEmpty();
    private static List<String> typeArguments = listEmpty();
    private static int functionLocalCounter = 0;

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        var compiled = compileStatements(input, Main::compileRootSegment);
        var joinedExpansions = expansions.iter()
                .map(tuple -> {
                    if (expandables.containsKey(tuple.left)) {
                        var expandable = expandables.get(tuple.left);
                        return expandable.apply(tuple.right).orElse("");
                    }
                    else {
                        return "// " + tuple.left + "<" + join(tuple.right, ", ") + ">\n";
                    }
                })
                .collect(new Joiner())
                .orElse("");

        return compiled + join(structs) + joinedExpansions + join(methods);
    }

    private static String join(List<String> list) {
        return join(list, "");
    }

    private static String join(List<String> list, String delimiter) {
        return list.iter().collect(new Joiner(delimiter)).orElse("");
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements);
    }

    private static String compileAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, String> compiler,
            BiFunction<String, String, String> merger
    ) {
        return generateAll(merger, parseAll(input, folder, compiler));
    }

    private static String generateAll(BiFunction<String, String, String> merger, List<String> parsed) {
        return parsed.iter()
                .foldRight("", merger);
    }

    private static List<String> parseAll(String input, BiFunction<State, Character, State> folder, Function<String, String> compiler) {
        return divideAll(input, folder)
                .iter()
                .map(compiler)
                .collect(new ListCollector<>());
    }

    private static String mergeStatements(String buffer, String element) {
        return buffer + element;
    }

    private static List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
        State state = State.fromInput(input);
        while (true) {
            var maybeNextTuple = state.pop();
            if (maybeNextTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeNextTuple.orElse(null);
            var next = nextTuple.left;
            var withoutNext = nextTuple.right;

            state = foldSingleQuotes(withoutNext, next)
                    .orElseGet(() -> folder.apply(withoutNext, next));
        }

        return state.advance().segments;
    }

    private static Option<State> foldSingleQuotes(State state, char next) {
        if (next != '\'') {
            return new None<>();
        }

        var appended = state.append(next);
        return appended.popAndAppendToTuple()
                .flatMap(maybeSlash -> maybeSlash.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(maybeSlash.right))
                .flatMap(State::popAndAppend);
    }

    private static State foldStatementChar(State state, char c) {
        var appended = state.append(c);

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

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Option<String> compileClass(String stripped) {
        return compileStructure(stripped, "class ");
    }

    private static Option<String> compileStructure(String input, String infix) {
        var classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            var beforeClass = input.substring(0, classIndex).strip();
            var afterClass = input.substring(classIndex + infix.length());
            var contentStart = afterClass.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = afterClass.substring(0, contentStart).strip();

                var paramStart = beforeContent.indexOf("(");
                var withEnd = afterClass.substring(contentStart + "{".length()).strip();
                if (paramStart >= 0) {
                    String withoutParams = beforeContent.substring(0, paramStart).strip();
                    return getString(withoutParams, beforeClass, withEnd);
                }
                else {
                    return getString(beforeContent, beforeClass, withEnd);
                }
            }
        }
        return new None<>();
    }

    private static Option<String> getString(String beforeContent, String beforeClass, String withEnd) {
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var content = withEnd.substring(0, withEnd.length() - "}".length());

        var strippedBeforeContent = beforeContent.strip();
        if (strippedBeforeContent.endsWith(">")) {
            var withoutEnd = strippedBeforeContent.substring(0, strippedBeforeContent.length() - ">".length());
            var typeParamStart = withoutEnd.indexOf("<");
            if (typeParamStart >= 0) {
                var name = withoutEnd.substring(0, typeParamStart).strip();
                var substring = withoutEnd.substring(typeParamStart + "<".length());
                var typeParameters = listFromArray(substring.split(Pattern.quote(",")));
                return assembleStructure(typeParameters, name, beforeClass, content);
            }
        }

        return assembleStructure(listEmpty(), strippedBeforeContent, beforeClass, content);
    }

    private static Option<String> assembleStructure(List<String> typeParams, String name, String beforeClass, String content) {
        if (!typeParams.isEmpty()) {
            expandables.put(name, typeArgs -> {
                typeParameters = typeParams;
                typeArguments = typeArgs;

                var newName = name + "<" + join(typeArgs, ", ") + ">";
                return generateStructure(newName, beforeClass, content);
            });

            return new Some<>("");
        }

        return generateStructure(name, beforeClass, content);
    }

    private static Option<String> generateStructure(String name, String beforeClass, String content) {
        var generated = generatePlaceholder(beforeClass) + "struct " + name + " {" + compileStatements(content, Main::compileClassSegment) + "\n};\n";
        structs.add(generated);
        return new Some<>("");
    }

    private static String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        return compileStructure(stripped, "record ")
                .or(() -> compileStructure(stripped, "interface "))
                .or(() -> compileClass(stripped))
                .or(() -> compileMethod(stripped))
                .or(() -> compileDefinitionStatement(stripped))
                .orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Option<String> compileDefinitionStatement(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return new Some<>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
        }

        return new None<>();
    }

    private static Option<String> compileMethod(String stripped) {
        var paramStart = stripped.indexOf("(");
        if (paramStart < 0) {
            return new None<>();
        }

        var inputDefinition = stripped.substring(0, paramStart);
        var defined = parseDefinitionOrPlaceholder(inputDefinition);
        if (defined instanceof Definition definition) {
            functionName = definition.name;
        }

        var outputDefinition = defined.generate();

        var afterParams = stripped.substring(paramStart + "(".length());
        var paramEnd = afterParams.indexOf(")");
        if (paramEnd < 0) {
            return new None<>();
        }

        var params = afterParams.substring(0, paramEnd);
        var withoutParams = afterParams.substring(paramEnd + ")".length());
        var withBraces = withoutParams.strip();

        if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
            return new Some<>("");
        }

        var content = withBraces.substring(1, withBraces.length() - 1);
        var outputParams = generateValues(parseValues(params, Main::compileParameter));

        return assembleMethod(outputDefinition, outputParams, content);
    }

    private static Some<String> assembleMethod(String definition, String outputParams, String content) {
        var generated = definition + "(" + outputParams + "){" + compileStatements(content, Main::compileFunctionSegment) + "\n}\n";
        methods.add(generated);
        return new Some<>("");
    }

    private static String compileParameter(String input) {
        if (input.isBlank()) {
            return "";
        }
        return compileDefinitionOrPlaceholder(input);
    }

    private static String compileFunctionSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length()).strip();
            if (withoutEnd.startsWith("return ")) {
                var value = withoutEnd.substring("return ".length());
                return "\n\treturn " + compileValue(value) + ";";
            }
        }

        return "\n\t" + generatePlaceholder(stripped);
    }

    private static String compileValue(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return stripped;
        }

        if (stripped.endsWith(")")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ")".length()).strip();

            var divisions = divideAll(withoutEnd, new BiFunction<State, Character, State>() {
                @Override
                public State apply(State state, Character c) {
                    var appended = state.append(c);
                    if (c == '(') {
                        var maybeAdvanced = appended.isLevel() ? appended.advance() : appended;
                        return maybeAdvanced.enter();
                    }
                    if (c == ')') {
                        return appended.exit();
                    }
                    return appended;
                }
            });

            if (divisions.size() >= 2) {
                var joined = join(divisions.subList(0, divisions.size() - 1), "");
                var caller = joined.substring(0, joined.length() - ")".length());
                var arguments = divisions.last();

                String compiled;
                if (caller.startsWith("new ")) {
                    compiled = compileType(caller.substring("new ".length()));
                }
                else {
                    compiled = compileValue(caller);
                }

                return compiled + "(" + compileValues(arguments) + ")";
            }
        }

        if (isSymbol(stripped)) {
            return stripped;
        }

        if (isNumber(stripped)) {
            return stripped;
        }

        var arrowIndex = stripped.indexOf("->");
        if (arrowIndex >= 0) {
            var beforeArrow = stripped.substring(0, arrowIndex).strip();
            var afterArrow = stripped.substring(arrowIndex + "->".length()).strip();
            if (afterArrow.startsWith("{") && afterArrow.endsWith("}")) {
                var content = afterArrow.substring(1, afterArrow.length() - 1);

                var name = functionName + "_local" + functionLocalCounter;
                functionLocalCounter++;

                assembleMethod("auto " + name, "auto " + beforeArrow, content);
                return name;
            }
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var value = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            return compileValue(value) + "." + property;
        }

        return generatePlaceholder(stripped);
    }

    private static boolean isNumber(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String compileValues(String input) {
        return generateValues(parseValues(input, Main::compileValue));
    }

    private static String compileDefinitionOrPlaceholder(String input) {
        return parseDefinitionOrPlaceholder(input).generate();
    }

    private static Defined parseDefinitionOrPlaceholder(String input) {
        return parseDefinition(input).<Defined>map(value -> value)
                .orElseGet(() -> new Content(input));
    }

    private static Option<Definition> parseDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }

        var beforeName = stripped.substring(0, nameSeparator);
        var name = stripped.substring(nameSeparator + " ".length());
        if (!isSymbol(name)) {
            return new None<>();
        }

        var divisions = divideAll(beforeName, Main::foldByTypeSeparator);
        if (divisions.size() == 1) {
            return new Some<>(new Definition(new None<>(), compileType(beforeName), name));
        }

        var beforeType = join(divisions.subList(0, divisions.size() - 1), " ");
        var type = divisions.last();

        return new Some<>(new Definition(new Some<>(beforeType), compileType(type), name));
    }

    private static State foldByTypeSeparator(State state, char c) {
        if (c == ' ' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    }

    private static String compileType(String input) {
        var stripped = input.strip();
        var maybeTypeParamIndex = typeParameters.indexOf(stripped);
        if (maybeTypeParamIndex.isPresent()) {
            var typeParamIndex = maybeTypeParamIndex.orElse(null);
            return typeArguments.get(typeParamIndex);
        }

        switch (stripped) {
            case "int" -> {
                return "int";
            }
            case "Character" -> {
                return "char";
            }
            case "void" -> {
                return "void";
            }
            case "String" -> {
                return "char*";
            }
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var index = withoutEnd.indexOf("<");
            if (index >= 0) {
                var base = withoutEnd.substring(0, index).strip();
                var substring = withoutEnd.substring(index + "<".length());
                var parsed = parseValues(substring, Main::compileType);

                if (!expansions.contains(new Tuple<>(base, parsed))) {
                    expansions = expansions.add(new Tuple<>(base, parsed));
                }

                return base + "<" + generateValues(parsed) + ">";
            }
        }

        if (isSymbol(stripped)) {
            return "struct " + stripped;
        }

        return generatePlaceholder(stripped);
    }

    private static String generateValues(List<String> values) {
        return generateAll(Main::mergeValues, values);
    }

    private static List<String> parseValues(String input, Function<String, String> compiler) {
        return parseAll(input, Main::foldValueChar, compiler);
    }

    private static String mergeValues(String builder, String element) {
        if (builder.isEmpty()) {
            return builder + element;
        }

        return builder + ", " + element;
    }

    private static State foldValueChar(State state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }
        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    }

    private static boolean isSymbol(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return false;
        }

        for (var i = 0; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
