package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    private interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private interface Option<T> {
        void ifPresent(Consumer<T> consumer);

        T orElseGet(Supplier<T> supplier);

        Option<T> or(Supplier<Option<T>> other);

        boolean isPresent();

        <R> Option<R> map(Function<T, R> mapper);
    }

    private interface Error {
        String display();
    }

    private @interface Actual {
    }

    private record IOError(IOException exception) implements Error {
        @Override
        public String display() {
            var writer = new StringWriter();
            this.exception.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    record None<T>() implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }
    }

    private static class DivideState {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        public DivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        private DivideState enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private DivideState exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private DivideState advance() {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public List<String> segments() {
            return this.segments;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }
    }

    private record CompileState(List<String> functions, int counter) {
        public CompileState() {
            this(new ArrayList<>(), 0);
        }

        public CompileState addFunction(String generated) {
            this.functions.add(generated);
            return this;
        }

        public Tuple<String, CompileState> createName(String category) {
            var name = category + this.counter;
            var next = new CompileState(this.functions, this.counter + 1);
            return new Tuple<>(name, next);
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record Definition(List<String> newAnnotations, List<String> newModifiers, String type, String name) {
        private String generate() {
            String annotationsStrings;
            if (this.newAnnotations().isEmpty()) {
                annotationsStrings = "";
            }
            else {
                annotationsStrings = this.newAnnotations().stream().map(value -> "@" + value).collect(Collectors.joining("\n")) + "\n";
            }

            var modifiersString = this.newModifiers().isEmpty() ? "" : String.join(" ", this.newModifiers()) + " ";
            return annotationsStrings + modifiersString + this.type() + " " + this.name();
        }
    }

    public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java");
    public static final Path TARGET = SOURCE.resolveSibling("main.c");

    public static void main() {
        readSource().match(input -> {
            var output = compile(input);
            return writeTarget(output);
        }, Some::new).ifPresent(error -> System.err.println(error.display()));
    }

    @Actual
    private static Option<IOError> writeTarget(String output) {
        try {
            Files.writeString(TARGET, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new IOError(e));
        }
    }

    @Actual
    private static Result<String, IOError> readSource() {
        try {
            return new Ok<>(Files.readString(SOURCE));
        } catch (IOException e) {
            return new Err<>(new IOError(e));
        }
    }

    private static String compile(String input) {
        var state = new CompileState();

        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var left = withoutEnd.substring(0, contentStart);
                var right = withoutEnd.substring(contentStart + "{".length());
                var result = compileRoot(right, state);
                var joined = String.join("", result.left.functions);
                return generatePlaceholder(left) + "{\n};\n" + joined + result.right;
            }
        }

        return generatePlaceholder(stripped);
    }

    private static Tuple<CompileState, String> compileRoot(String input, CompileState state) {
        return compileStatements(state, input, Main::compileClassSegment);
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(state, input, Main::foldStatementChar, mapper, Main::mergeStatements);
    }

    private static Tuple<CompileState, String> compileAll(CompileState initial, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<StringBuilder, String, StringBuilder> merger) {
        var segments = divideAll(input, folder);

        var current = initial;
        var output = new StringBuilder();
        for (var segment : segments) {
            var mapped = mapper.apply(current, segment);
            current = mapped.left;
            output = merger.apply(output, mapped.right);
        }

        return new Tuple<>(current, output.toString());
    }

    private static StringBuilder mergeStatements(StringBuilder output, String mapped) {
        return output.append(mapped);
    }

    private static List<String> divideAll(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        else if (c == '{' || c == '(') {
            return appended.enter();
        }
        else if (c == '}' || c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutContentEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutContentEnd.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = withoutContentEnd.substring(0, contentStart).strip();
                var right = withoutContentEnd.substring(contentStart + "{".length());

                if (beforeContent.endsWith(")")) {
                    var withoutParamEnd = beforeContent.substring(0, beforeContent.length() - ")".length());
                    var paramStart = withoutParamEnd.indexOf("(");
                    if (paramStart >= 0) {
                        var definitionString = withoutParamEnd.substring(0, paramStart);
                        var params = withoutParamEnd.substring(paramStart + "(".length());

                        if (parseDefinition(state, definitionString) instanceof Some(var definitionTuple)) {
                            var definition = definitionTuple.right;
                            var header = definition.generate() + "(" + generatePlaceholder(params) + ")";
                            if (definition.newModifiers.contains("expect")) {
                                return new Tuple<>(definitionTuple.left, header + ";\n");
                            }

                            var statementsTuple = compileStatements(definitionTuple.left, right, Main::compileFunctionSegment);
                            var generated = header + "{" + statementsTuple.right + "\n}\n";
                            return new Tuple<>(statementsTuple.left.addFunction(generated), "");
                        }
                    }
                }
            }
        }

        return new Tuple<>(state, generatePlaceholder(stripped) + "\n");
    }

    private static Tuple<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return new Tuple<>(state, "");
        }

        if (stripped.endsWith(";")) {
            var slice = stripped.substring(0, stripped.length() - ";".length());
            var s = compileFunctionStatementValue(slice, state);
            return new Tuple<>(s.left, "\n\t" + s.right + ";");
        }
        return new Tuple<>(state, generatePlaceholder(stripped));
    }

    private static Tuple<CompileState, String> compileFunctionStatementValue(String input, CompileState state) {
        return compileReturn(state, input)
                .or(() -> compileInvocation(state, input))
                .or(() -> compileAssignment(state, input))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> compileReturn(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            var right = stripped.substring("return ".length());
            if (compileValue(state, right) instanceof Some(var other)) {
                return new Some<>(new Tuple<>(other.left, "return " + other.right));
            }
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> compileAssignment(CompileState state, String input) {
        var valueSeparator = input.indexOf("=");
        if (valueSeparator < 0) {
            return new None<>();
        }

        var left = input.substring(0, valueSeparator);
        var right = input.substring(valueSeparator + "=".length());
        var definitionTuple = compileDefinitionOrPlaceholder(state, left);
        var valueTuple = compileValueOrPlaceholder(definitionTuple.left, right);
        return new Some<>(new Tuple<>(valueTuple.left, definitionTuple.right + " = " + valueTuple.right));
    }

    private static Tuple<CompileState, String> compileDefinitionOrPlaceholder(CompileState state, String input) {
        return compileDefinition(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> compileDefinition(CompileState state, String input) {
        return parseDefinition(state, input).map(tuple -> new Tuple<>(tuple.left(), tuple.right().generate()));
    }

    private static Option<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        var stripped = input.strip();
        var valueSeparator = stripped.lastIndexOf(" ");
        if (valueSeparator >= 0) {
            var beforeName = stripped.substring(0, valueSeparator);
            var name = stripped.substring(valueSeparator + " ".length()).strip();
            var annotationSeparator = beforeName.lastIndexOf("\n");
            if (annotationSeparator < 0) {
                return definitionWithAnnotations(state, Collections.emptyList(), beforeName, name);
            }

            var annotationsArray = beforeName.substring(0, annotationSeparator).strip().split(Pattern.quote("\n"));
            var annotations = Arrays.stream(annotationsArray)
                    .map(String::strip)
                    .map(slice -> slice.isEmpty() ? "" : slice.substring(1))
                    .toList();

            var beforeName0 = beforeName.substring(annotationSeparator + "\n".length());
            return definitionWithAnnotations(state, annotations, beforeName0, name);
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, Definition>> definitionWithAnnotations(CompileState state, List<String> annotations, String type, String name) {
        var typeResult = compileType(state, type);

        var newAnnotations = new ArrayList<String>();
        var newModifiers = new ArrayList<String>();
        for (var annotation : annotations) {
            if (annotation.equals("Actual")) {
                newModifiers.add("expect");
            }
            else {
                newAnnotations.add(annotation);
            }
        }

        return new Some<>(new Tuple<>(typeResult.left, new Definition(newAnnotations, newModifiers, typeResult.right, name)));
    }

    private static Tuple<CompileState, String> compileType(CompileState state, String input) {
        var stripped = input.strip();

        if (stripped.equals("var")) {
            return new Tuple<>(state, "auto");
        }

        if (isSymbol(stripped)) {
            return new Tuple<>(state, "struct " + stripped);
        }

        return new Tuple<>(state, generatePlaceholder(stripped));
    }

    private static Option<Tuple<CompileState, String>> compileInvocation(CompileState state, String stripped) {
        if (stripped.endsWith(")")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ")".length());

            var divisions = divideAll(withoutEnd, Main::foldInvocationStart);
            if (divisions.size() >= 2) {
                var joined = String.join("", divisions.subList(0, divisions.size() - 1));
                var caller = joined.substring(0, joined.length() - ")".length());
                var arguments = divisions.getLast();

                if (compileValue(state, caller) instanceof Some(var callerTuple)) {
                    var argumentsTuple = compileAll(callerTuple.left, arguments, Main::foldValueChar, Main::compileValueOrPlaceholder, Main::mergeValues);
                    var generated = callerTuple.right + "(" + argumentsTuple.right + ")";
                    return new Some<>(new Tuple<>(argumentsTuple.left, generated));
                }
            }
        }

        return new None<>();
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }
        return state.append(c);
    }

    private static Tuple<CompileState, String> compileValueOrPlaceholder(CompileState state, String input) {
        return compileValue(state, input).orElseGet(() -> {
            return new Tuple<>(state, generatePlaceholder(input));
        });
    }

    private static Option<Tuple<CompileState, String>> compileValue(CompileState state, String input) {
        var stripped = input.strip();
        var arrowIndex = stripped.indexOf("->");
        if (arrowIndex >= 0) {
            var beforeArrow = stripped.substring(0, arrowIndex).strip();
            var afterArrow = stripped.substring(arrowIndex + "->".length());
            if (isSymbol(beforeArrow)) {
                var withBraces = afterArrow.strip();
                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    var content = withBraces.substring(1, withBraces.length() - 1);
                    var result = compileStatements(state, content, Main::compileFunctionSegment);
                    return assembleLambda(result.left, beforeArrow, result.right);
                }
                else {
                    if (compileValue(state, afterArrow) instanceof Some(var valueTuple)) {
                        return assembleLambda(valueTuple.left, beforeArrow, "\n\treturn " + valueTuple.right + ";");
                    }
                }
            }
        }

        var maybeInvocation = compileInvocation(state, stripped);
        if (maybeInvocation.isPresent()) {
            return maybeInvocation;
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var child = stripped.substring(separator + ".".length());
            if (isSymbol(child)) {
                var tuple = compileValueOrPlaceholder(state, parent);
                var compileStateStringTuple = new Tuple<CompileState, String>(tuple.left, tuple.right + "." + child);
                return new Some<>(compileStateStringTuple);
            }
        }

        if (isSymbol(stripped)) {
            var compileStateStringTuple = new Tuple<CompileState, String>(state, stripped);
            return new Some<>(compileStateStringTuple);
        }

        var functionSeparator = stripped.indexOf("::");
        if (functionSeparator >= 0) {
            var left = stripped.substring(0, functionSeparator);
            var right = stripped.substring(functionSeparator + "::".length()).strip();
            var leftTuple = compileType(state, left);
            return new Some<>(new Tuple<>(leftTuple.left, leftTuple.right + "::" + right));
        }

        return new None<>();
    }

    private static Some<Tuple<CompileState, String>> assembleLambda(CompileState state, String beforeArrow, String content) {
        var nameTuple = state.createName("lambda");
        var name = nameTuple.left;
        return new Some<>(new Tuple<CompileState, String>(nameTuple.right.addFunction("auto " + name + "(auto " + beforeArrow + "){" + content + "\n}\n"), name));
    }

    private static boolean isSymbol(String input) {
        var stripped = input.strip();
        for (var i = 0; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static DivideState foldInvocationStart(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            var entered = appended.enter();
            if (appended.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
}
