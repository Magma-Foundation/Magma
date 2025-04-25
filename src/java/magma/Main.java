package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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
        String generate();
    }

    private sealed interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        T orElse(T other);

        T orElseGet(Supplier<T> other);

        Option<T> or(Supplier<Option<T>> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        Option<T> filter(Predicate<T> predicate);

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

    private interface Parameter {
        String generate();
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

    private record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
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
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
            return predicate.test(this.value) ? this : new None<>();
        }

        @Override
        public boolean isPresent() {
            return true;
        }
    }

    private static final class None<T> implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
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
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
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

    static final class Definition implements Definable, StatementValue, Assignable, Parameter {
        private final Option<String> maybeBeforeType;
        private final Type type;
        private final String name;
        private final List<String> typeParams;

        Definition(
                Option<String> maybeBeforeType,
                Type type, String name, List<String> typeParams) {
            this.maybeBeforeType = maybeBeforeType;
            this.type = type;
            this.name = name;
            this.typeParams = typeParams;
        }

        @Override
        public String generate() {
            String joinedTypeParams;
            if (this.typeParams.isEmpty()) {
                joinedTypeParams = "";
            }
            else {
                joinedTypeParams = "<" + String.join(", ", this.typeParams) + ">";
            }
            var beforeType = this.maybeBeforeType.map(inner -> inner + " ").orElse("");
            return beforeType + this.type.generate() + " " + this.name + joinedTypeParams;
        }

        public Definition rename(String name) {
            return new Definition(this.maybeBeforeType, this.type, name, this.typeParams);
        }

        public Definition mapTypeParams(Function<List<String>, List<String>> mapper) {
            return new Definition(this.maybeBeforeType, this.type, this.name, mapper.apply(this.typeParams));
        }
    }

    private record Struct(String name, List<String> typeParams) implements Type {
        private Struct(String name) {
            this(name, Collections.emptyList());
        }

        @Override
        public String generate() {
            var typeParamString = this.typeParams.isEmpty() ? "" : "<" + String.join(", ", this.typeParams) + ">";
            return "struct " + this.name + typeParamString;
        }
    }

    private record Ref(Type type) implements Type {
        @Override
        public String generate() {
            return this.type.generate() + "*";
        }
    }

    private record Content(String input) implements Type, FunctionSegment, StatementValue, Value, Parameter {
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

    private record ConstructorDefinition(String name) implements Definable {
        private Definition toDefinition() {
            return new DefinitionBuilder().withType(new Struct(this.name())).withName("new_" + this.name()).complete();
        }

        @Override
        public String generate() {
            return this.name;
        }
    }

    private record Statement(StatementValue content) implements FunctionSegment {
        @Override
        public String generate() {
            return "\n\t" + this.content.generate() + ";";
        }
    }

    private static class Whitespace implements FunctionSegment, Parameter {
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

    private record Return(Value value) implements StatementValue {
        @Override
        public String generate() {
            return "return " + this.value.generate();
        }
    }

    private static class DefinitionBuilder {
        private Option<String> maybeBeforeType = new None<String>();
        private Type type;
        private String name;
        private List<String> typeParams = new ArrayList<>();

        public DefinitionBuilder withBeforeType(String beforeType) {
            this.maybeBeforeType = new Some<>(beforeType);
            return this;
        }

        public DefinitionBuilder withType(Type type) {
            this.type = type;
            return this;
        }

        public DefinitionBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Definition complete() {
            return new Definition(this.maybeBeforeType, this.type, this.name, this.typeParams);
        }

        public DefinitionBuilder withMaybeBeforeType(Option<String> maybeBeforeType) {
            this.maybeBeforeType = maybeBeforeType;
            return this;
        }

        public DefinitionBuilder withTypeParams(List<String> typeParams) {
            this.typeParams = typeParams;
            return this;
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record TypeParam(String input) implements Type {
        @Override
        public String generate() {
            return this.input;
        }
    }

    private static final List<String> typeParams = new ArrayList<>();
    public static List<String> structs;
    private static List<String> functions;
    private static List<Tuple<String, List<String>>> stack;

    public static void main() {
        structs = new ArrayList<>();
        functions = new ArrayList<>();
        stack = new ArrayList<>();

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

    private static Option<String> compileClass(String stripped) {
        return compileStructured(stripped, "class ");
    }

    private static Option<String> compileStructured(String stripped, String infix) {
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
        var withEnd = afterKeyword.substring(contentStart + "{".length());

        var implementsIndex = beforeContent.indexOf(" implements ");
        var withoutImplements = implementsIndex >= 0
                ? beforeContent.substring(0, implementsIndex).strip()
                : beforeContent;

        var extendsIndex = withoutImplements.indexOf(" extends ");
        var withoutExtends = extendsIndex >= 0
                ? withoutImplements.substring(0, extendsIndex).strip()
                : withoutImplements;

        var paramStart = withoutExtends.indexOf("(");
        var withoutParameters = paramStart >= 0
                ? withoutExtends.substring(0, paramStart).strip()
                : withoutExtends;

        var typeParamStart = withoutParameters.indexOf("<");
        if (typeParamStart >= 0) {
            String name = withoutParameters.substring(0, typeParamStart).strip();
            var withTypeParamEnd = withoutParameters.substring(typeParamStart + "<".length()).strip();
            if (withTypeParamEnd.endsWith(">")) {
                var typeParamsString = withTypeParamEnd.substring(0, withTypeParamEnd.length() - ">".length());
                var typeParams = parseValues(typeParamsString, Function.identity());
                return assembleStructured(name, withEnd, typeParams);
            }
        }

        return assembleStructured(withoutParameters, withEnd, Collections.emptyList());
    }

    private static Option<String> assembleStructured(String name, String input, List<String> typeParams) {
        if (!isSymbol(name)) {
            return new None<>();
        }

        String withEnd = input.strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var content = withEnd.substring(0, withEnd.length() - "}".length());

        var typeParamString = typeParams.isEmpty() ? "" : "<" + String.join(", ", typeParams) + ">";

        stack.addLast(new Tuple<>(name, typeParams));
        var generated = "struct " + name + typeParamString + " {" +
                compileStatements(content, Main::compileClassSegment) +
                "\n};\n";

        stack.removeLast();
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

    private static Option<String> compileWhitespace(String input) {
        return parseWhitespace(input).map(Whitespace::generate);
    }

    private static Option<Whitespace> parseWhitespace(String input) {
        if (input.isBlank()) {
            return new Some<>(new Whitespace());
        }
        else {
            return new None<>();
        }
    }

    private static Option<String> compileMethod(String input) {
        var paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return new None<>();
        }

        var beforeParams = input.substring(0, paramStart).strip();
        var withParams = input.substring(paramStart + "(".length());


        var currentStruct = stack.getLast();
        var currentStructName = currentStruct.left;
        var currentStructTypeParams = currentStruct.right;
        typeParams.addAll(currentStructTypeParams);

        var maybeHeader = parseDefinition(beforeParams)
                .<Definable>map(value -> value)
                .or(() -> compileConstructorDefinition(beforeParams).map(value -> value));

        if (!(maybeHeader instanceof Some(var header))) {
            return new None<>();
        }

        var paramEnd = withParams.indexOf(")");
        if (paramEnd < 0) {
            return new None<>();
        }

        var paramStrings = withParams.substring(0, paramEnd).strip();
        var afterParams = withParams.substring(paramEnd + ")".length()).strip();
        if (afterParams.startsWith("{") && afterParams.endsWith("}")) {
            var content = afterParams.substring(1, afterParams.length() - 1);

            var inputParams = parseValues(paramStrings, Main::parseParameter);
            var oldStatements = parseStatements(content, Main::parseStatement);
            typeParams.clear();

            ArrayList<FunctionSegment> newStatements;
            if (header instanceof ConstructorDefinition(var name)) {
                var copy = new ArrayList<FunctionSegment>();
                copy.add(new Statement(new DefinitionBuilder()
                        .withType(new Struct(name, currentStructTypeParams))
                        .withName("this")
                        .complete()));

                copy.addAll(oldStatements);
                copy.add(new Statement(new Return(new Symbol("this"))));
                newStatements = copy;
            }
            else {
                newStatements = new ArrayList<>(oldStatements);
            }

            var outputContent = newStatements
                    .stream()
                    .map(FunctionSegment::generate)
                    .collect(Collectors.joining());

            Definable newDefinition;
            var outputParams = new ArrayList<Parameter>();
            if (header instanceof Definition oldDefinition) {
                outputParams.add(new DefinitionBuilder()
                        .withType(new Struct(currentStructName, currentStructTypeParams))
                        .withName("this")
                        .complete());

                newDefinition = oldDefinition.rename(oldDefinition.name + "_" + currentStructName).mapTypeParams(typeParams -> {
                    ArrayList<String> copy = new ArrayList<>(currentStructTypeParams);
                    copy.addAll(typeParams);
                    return copy;
                });
            }
            else if (header instanceof ConstructorDefinition constructorDefinition) {
                newDefinition = constructorDefinition.toDefinition();
            }
            else {
                newDefinition = header;
            }

            outputParams.addAll(inputParams
                    .stream()
                    .filter(node -> !(node instanceof Whitespace))
                    .toList());

            var outputParamStrings = outputParams.stream()
                    .map(Parameter::generate)
                    .collect(Collectors.joining(", "));

            var constructor = newDefinition.generate() + "(" + outputParamStrings + "){" + outputContent + "\n}\n";
            functions.add(constructor);
            return new Some<>("");
        }

        if (afterParams.equals(";")) {
            return new Some<>("");
        }

        return new None<>();
    }

    private static <T> List<T> parseStatements(String content, Function<String, T> compiler) {
        return parseAll(content, Main::foldStatementChar, compiler);
    }

    private static Option<ConstructorDefinition> compileConstructorDefinition(String input) {
        return findConstructorDefinitionName(input).flatMap(name -> {
            if (stack.getLast().equals(name)) {
                return new Some<>(new ConstructorDefinition(name));
            }
            return new None<>();
        });
    }

    private static Option<String> findConstructorDefinitionName(String input) {
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
        return compileReturn(input).<StatementValue>map(value -> value)
                .or(() -> compileAssignment(input).map(value -> value))
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input));
    }

    private static Option<Return> compileReturn(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            return new Some<>(new Return(parseValue(stripped.substring("return ".length()))));
        }

        return new None<>();
    }

    private static Option<String> compileClassStatement(String input) {
        return compileStatement(input, Main::compileClassStatementValue);
    }

    private static Option<String> compileStatement(String input, Function<String, StatementValue> compiler) {
        return parseStatementWithoutBraces(input, compiler).map(Statement::generate);
    }

    private static Option<Statement> parseStatementWithoutBraces(String input, Function<String, StatementValue> compiler) {
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

    private static Option<StatementValue> compileAssignment(String input) {
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
            if (isSymbol(property)) {
                var value = parseValue(parent);
                return new DataAccess(value, property);
            }
        }

        if (isSymbol(stripped)) {
            return new Symbol(stripped);
        }

        return new Content(stripped);
    }

    private static Parameter parseParameter(String input) {
        return parseWhitespace(input)
                .<Parameter>map(result -> result)
                .or(() -> parseDefinition(input).map(result -> result))
                .orElseGet(() -> new Content(input));
    }

    private static Option<Definition> parseDefinition(String input) {
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

        var withName = new DefinitionBuilder().withName(name);
        return switch (findTypeSeparator(beforeName)) {
            case None<Integer> _ -> parseType(beforeName).map(type -> new DefinitionBuilder()
                    .withType(type)
                    .withName(name)
                    .complete());
            case Some<Integer>(var typeSeparator) -> {
                var beforeType = beforeName.substring(0, typeSeparator).strip();
                var inputType = beforeName.substring(typeSeparator + " ".length()).strip();
                if (beforeType.endsWith(">")) {
                    var withTypeParamStart = beforeType.substring(0, beforeType.length() - ">".length());

                    var typeParamStart = withTypeParamStart.lastIndexOf("<");
                    if (typeParamStart >= 0) {
                        var withoutTypeParams = beforeType.substring(0, typeParamStart);
                        var typeParamString = withTypeParamStart.substring(typeParamStart + "<".length());

                        var typeParams = parseValues(typeParamString, Function.identity());
                        Main.typeParams.addAll(typeParams);
                        var maybeOutputType = parseType(inputType);

                        yield maybeOutputType.map(outputType -> withName
                                .withBeforeType(generatePlaceholder(withoutTypeParams))
                                .withTypeParams(typeParams)
                                .withType(outputType)
                                .complete());
                    }
                }
                yield parseType(inputType).map(outputType -> withName
                        .withBeforeType(beforeType)
                        .withType(outputType)
                        .complete());
            }
        };
    }

    private static Option<Integer> findTypeSeparator(String input) {
        var depth = 0;
        for (var i = input.length() - 1; i >= 0; i--) {
            var c = input.charAt(i);
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

    private static Option<Type> parseType(String input) {
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

        if (typeParams.contains(stripped)) {
            return new Some<>(new TypeParam(stripped));
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

    private static <T> List<T> parseValues(String args, Function<String, T> compiler) {
        return parseAll(args, Main::foldValueChar, compiler);
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

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
}
