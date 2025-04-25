package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {
    private interface Type {
        String generate();
    }

    private interface Definable {
        Definition toDefinition();
    }

    private sealed interface Optional<T> {
        <R> Optional<R> map(Function<T, R> mapper);

        T orElse(T other);

        T orElseGet(Supplier<T> other);

        Optional<T> or(Supplier<Optional<T>> other);

        <R> Optional<R> flatMap(Function<T, Optional<R>> mapper);

        Optional<T> filter(Predicate<T> predicate);

        boolean isPresent();
    }

    private interface FunctionSegment {
        String generate();
    }

    private interface StatementValue {
        String generate();
    }

    private interface Assignable {
        String generate();
    }

    private interface Value extends Assignable {
    }

    private enum Primitive implements Type {
        I8("char"),
        I32("int");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }
    }

    private record Some<T>(T value) implements Optional<T> {
        @Override
        public <R> Optional<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
        }

        @Override
        public Optional<T> or(Supplier<Optional<T>> other) {
            return this;
        }

        @Override
        public <R> Optional<R> flatMap(Function<T, Optional<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public Optional<T> filter(Predicate<T> predicate) {
            return predicate.test(this.value) ? this : new None<>();
        }

        @Override
        public boolean isPresent() {
            return true;
        }
    }

    private static final class None<T> implements Optional<T> {
        @Override
        public <R> Optional<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other.get();
        }

        @Override
        public Optional<T> or(Supplier<Optional<T>> other) {
            return other.get();
        }

        @Override
        public <R> Optional<R> flatMap(Function<T, Optional<R>> mapper) {
            return new None<>();
        }

        @Override
        public Optional<T> filter(Predicate<T> predicate) {
            return this;
        }

        @Override
        public boolean isPresent() {
            return false;
        }
    }

    private static class State {
        private final List<String> segments;
        private int depth;
        private StringBuilder buffer;

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.buffer = buffer;
            this.segments = segments;
            this.depth = depth;
        }

        private static State createDefault() {
            return new State(new ArrayList<>(), new StringBuilder(), 0);
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

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public State exit() {
            this.depth--;
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private record Definition(
            Optional<String> maybeBeforeType,
            Type type,
            String name
    ) implements Definable, StatementValue, Assignable {
        public Definition(Type type, String name) {
            this(new None<String>(), type, name);
        }

        @Override
        public String generate() {
            var beforeType = this.maybeBeforeType().map(inner -> inner + " ").orElse("");
            return beforeType + this.type().generate() + " " + this.name();
        }

        @Override
        public Definition toDefinition() {
            return this;
        }
    }

    private record Struct(String name) implements Type {
        @Override
        public String generate() {
            return "struct " + this.name();
        }
    }

    private record Ref(Type type) implements Type {
        @Override
        public String generate() {
            return this.type.generate() + "*";
        }
    }

    private record Content(String input) implements Type, FunctionSegment, StatementValue, Value {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }
    }

    private record Generic(String base, List<Type> args) implements Type {
        @Override
        public String generate() {
            var joinedArgs = this.args().stream()
                    .map(Type::generate)
                    .collect(Collectors.joining(", "));

            return this.base() + "<" + joinedArgs + ">";
        }
    }

    private record Constructor(String name) implements Definable {
        @Override
        public Definition toDefinition() {
            return new Definition(new Struct(this.name()), "new_" + this.name());
        }
    }

    private record Statement(StatementValue content) implements FunctionSegment {
        @Override
        public String generate() {
            return "\n\t" + this.content.generate() + ";";
        }
    }

    private static class Whitespace implements FunctionSegment {
        @Override
        public String generate() {
            return "";
        }
    }

    private record Assignment(Assignable assignable, Value value) implements StatementValue {
        @Override
        public String generate() {
            return this.assignable.generate() + " = " + this.value.generate();
        }
    }

    private record DataAccess(Value parent, String property) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "." + this.property;
        }
    }

    private record Symbol(String name) implements Value {
        @Override
        public String generate() {
            return this.name;
        }
    }

    public static List<String> structs;
    private static List<String> functions;
    private static Optional<String> currentStructName;

    public static void main() {
        structs = new ArrayList<>();
        functions = new ArrayList<>();
        currentStructName = new None<>();

        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("Main.c");
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        var output = compileStatements(input, Main::compileRootSegment);
        var joinedStructs = String.join("", structs);
        var joinedFunctions = String.join("", functions);
        return output + joinedStructs + joinedFunctions;
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements);
    }

    private static String compileAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, String> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        return generateAll(merger, parseAll(input, folder, compiler));
    }

    private static String generateAll(
            BiFunction<StringBuilder, String, StringBuilder> merger,
            List<String> compiled
    ) {
        var output = new StringBuilder();
        for (var segment : compiled) {
            output = merger.apply(output, segment);
        }

        return output.toString();
    }

    private static <T> List<T> parseAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, T> compiler
    ) {
        var segments = divide(input, folder);

        var compiled = new ArrayList<T>();
        for (var segment : segments) {
            compiled.add(compiler.apply(segment));
        }
        return compiled;
    }

    private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    }

    private static List<String> divide(String input, BiFunction<State, Character, State> folder) {
        return divideAll(input, folder);
    }

    private static List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
        var current = State.createDefault();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);

            if (c == '\'') {
                current.append(c);

                i++;
                var maybeSlash = input.charAt(i);
                current.append(maybeSlash);

                if (maybeSlash == '\\') {
                    i++;
                    current.append(input.charAt(i));
                }

                i++;
                current.append(input.charAt(i));
                continue;
            }

            current = folder.apply(current, c);
        }
        return current.advance().segments;
    }

    private static State foldStatementChar(State current, char c) {
        var appended = current.append(c);
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

    private static String compileRootSegment(String input) {
        var stripped = input.strip();

        if (stripped.startsWith("package ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped) + "\n");
    }

    private static Optional<String> compileClass(String stripped) {
        return compileStructured(stripped, "class ");
    }

    private static Optional<String> compileStructured(String stripped, String infix) {
        var classIndex = stripped.indexOf(infix);
        if (classIndex < 0) {
            return new None<>();
        }
        var afterKeyword = stripped.substring(classIndex + infix.length());
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }
        var beforeContent = afterKeyword.substring(0, contentStart).strip();

        var implementsIndex = beforeContent.indexOf(" implements ");
        var withoutImplements = implementsIndex >= 0
                ? beforeContent.substring(0, implementsIndex).strip()
                : beforeContent;

        var paramStart = withoutImplements.indexOf("(");
        var withoutParameters = paramStart >= 0
                ? withoutImplements.substring(0, paramStart).strip()
                : withoutImplements;

        var typeParamStart = withoutParameters.indexOf("<");
        var name = typeParamStart >= 0
                ? withoutParameters.substring(0, typeParamStart).strip()
                : withoutParameters;

        if (!isSymbol(name)) {
            return new None<>();
        }
        var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var content = withEnd.substring(0, withEnd.length() - "}".length());

        currentStructName = new Some<>(name);
        var generated = "struct " + name + " {" +
                compileStatements(content, Main::compileClassSegment) +
                "\n};\n";
        structs.add(generated);
        return new Some<>("");
    }

    private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String compileClassSegment(String input) {
        return compileWhitespace(input)
                .or(() -> compileClass(input))
                .or(() -> compileStructured(input, "enum "))
                .or(() -> compileStructured(input, "record "))
                .or(() -> compileStructured(input, "interface "))
                .or(() -> compileMethod(input))
                .or(() -> compileClassStatement(input))
                .orElseGet(() -> "\n\t" + generatePlaceholder(input.strip()));
    }

    private static Optional<String> compileWhitespace(String input) {
        return parseWhitespace(input).map(Whitespace::generate);
    }

    private static Optional<Whitespace> parseWhitespace(String input) {
        if (input.isBlank()) {
            return new Some<>(new Whitespace());
        }
        else {
            return new None<>();
        }
    }

    private static Optional<String> compileMethod(String input) {
        var paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return new None<>();
        }

        var beforeParams = input.substring(0, paramStart).strip();
        var withParams = input.substring(paramStart + "(".length());

        var maybeHeader = parseDefinition(beforeParams)
                .<Definable>map(value -> value)
                .or(() -> compileConstructorDefinition(beforeParams).map(value -> value));

        if (!(maybeHeader instanceof Some(var header))) {
            return new None<>();
        }

        var paramEnd = withParams.indexOf(")");
        if (paramEnd >= 0) {
            var inputParams = withParams.substring(0, paramEnd).strip();
            var afterParams = withParams.substring(paramEnd + ")".length()).strip();
            if (afterParams.startsWith("{") && afterParams.endsWith("}")) {
                var content = afterParams.substring(1, afterParams.length() - 1);
                var outputParams = compileValues(inputParams, Main::compileDefinitionOrPlaceholder);

                var oldStatements = parseStatements(content, Main::parseStatement);
                ArrayList<FunctionSegment> newStatements;
                if (header instanceof Constructor(var name)) {
                    var copy = new ArrayList<FunctionSegment>();
                    copy.add(new Statement(new Definition(new Struct(name), "this")));
                    copy.addAll(oldStatements);
                    newStatements = copy;
                }
                else {
                    newStatements = new ArrayList<>(oldStatements);
                }

                var outputContent = newStatements
                        .stream()
                        .map(FunctionSegment::generate)
                        .collect(Collectors.joining());

                var constructor = header.toDefinition().generate() + "(" + outputParams + "){" + outputContent + "\n}\n";
                functions.add(constructor);
                return new Some<>("");
            }
            if (afterParams.equals(";")) {
                return new Some<>("");
            }
        }

        return new None<>();
    }

    private static <T> List<T> parseStatements(String content, Function<String, T> compiler) {
        return parseAll(content, Main::foldStatementChar, compiler);
    }

    private static Optional<Constructor> compileConstructorDefinition(String input) {
        return findConstructorDefinitionName(input).flatMap(name -> {
            if (currentStructName.filter(structName -> structName.equals(name)).isPresent()) {
                return new Some<>(new Constructor(name));
            }
            return new None<>();
        });
    }

    private static Optional<String> findConstructorDefinitionName(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var name = stripped.substring(nameSeparator + " ".length());
            return new Some<>(name);
        }
        if (isSymbol(stripped)) {
            return new Some<>(stripped);
        }
        return new None<>();
    }

    private static FunctionSegment parseStatement(String input) {
        return parseWhitespace(input)
                .<FunctionSegment>map(value -> value)
                .or(() -> parseStatementWithoutBraces(input, Main::parseStatementValue).map(value -> value))
                .orElseGet(() -> new Content(input));
    }

    private static StatementValue parseStatementValue(String input) {
        return compileAssignment(input)
                .map(value -> value)
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input));
    }

    private static Optional<String> compileClassStatement(String input) {
        return compileStatement(input, Main::compileClassStatementValue);
    }

    private static Optional<String> compileStatement(String input, Function<String, StatementValue> compiler) {
        return parseStatementWithoutBraces(input, compiler).map(Statement::generate);
    }

    private static Optional<Statement> parseStatementWithoutBraces(String input, Function<String, StatementValue> compiler) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            var content = compiler.apply(withoutEnd);
            return new Some<>(new Statement(content));
        }
        else {
            return new None<>();
        }
    }

    private static StatementValue compileClassStatementValue(String input) {
        return compileAssignment(input)
                .map(value -> value)
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input));
    }

    private static Optional<StatementValue> compileAssignment(String input) {
        var valueSeparator = input.indexOf("=");
        if (valueSeparator >= 0) {
            var inputDefinition = input.substring(0, valueSeparator);
            var value = input.substring(valueSeparator + "=".length());

            var destination = parseDefinition(inputDefinition)
                    .<Assignable>map(result -> result)
                    .orElseGet(() -> parseValue(inputDefinition));

            return new Some<>(new Assignment(destination, parseValue(value)));
        }
        return new None<>();
    }

    private static Value parseValue(String input) {
        var stripped = input.strip();
        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            var value = parseValue(parent);
            return new DataAccess(value, property);
        }

        if (isSymbol(stripped)) {
            return new Symbol(stripped);
        }

        return new Content(stripped);
    }

    private static String compileDefinitionOrPlaceholder(String input) {
        return parseDefinition(input).map(Definition::generate).orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<Definition> parseDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }

        var beforeName = stripped.substring(0, nameSeparator).strip();
        var name = stripped.substring(nameSeparator + " ".length()).strip();
        if (!isSymbol(name)) {
            return new None<>();
        }

        var typeSeparator = beforeName.lastIndexOf(" ");
        if (typeSeparator < 0) {
            return parseType(beforeName).map(type -> new Definition(new None<String>(), type, name));
        }

        var beforeType = beforeName.substring(0, typeSeparator).strip();
        var inputType = beforeName.substring(typeSeparator + " ".length()).strip();
        return parseType(inputType).map(outputType -> new Definition(new Some<String>(generatePlaceholder(beforeType)), outputType, name));
    }

    private static Optional<Type> parseType(String input) {
        var stripped = input.strip();
        if (stripped.equals("private")) {
            return new None<>();
        }

        if (stripped.equals("int")) {
            return new Some<>(Primitive.I32);
        }

        if (stripped.equals("String")) {
            return new Some<>(new Ref(Primitive.I8));
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                var base = withoutEnd.substring(0, argsStart).strip();
                var argsString = withoutEnd.substring(argsStart + "<".length()).strip();
                var args = parseValues(argsString, input1 -> parseType(input1).orElseGet(() -> new Content(input1)));

                return new Some<>(new Generic(base, args));
            }
        }

        if (isSymbol(stripped)) {
            return new Some<>(new Struct(stripped));
        }

        return new None<>();
    }

    private static String compileValues(String args, Function<String, String> compiler) {
        return generateValues(parseValues(args, compiler));
    }

    private static String generateValues(List<String> values) {
        return generateAll(Main::mergeValues, values);
    }

    private static <T> List<T> parseValues(String args, Function<String, T> compiler) {
        return parseAll(args, Main::foldValueChar, compiler);
    }

    private static State foldValueChar(State state, char c) {
        if (c == ',') {
            return state.advance();
        }
        else {
            return state.append(c);
        }
    }

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) {
            return buffer.append(element);
        }
        return buffer.append(", ").append(element);
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
}
