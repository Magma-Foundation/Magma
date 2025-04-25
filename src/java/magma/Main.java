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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public sealed interface Type extends Node permits Functional, Generic, Primitive, Ref, Struct, TypeParam, Whitespace {
        String generate();

        default Type flatten() {
            return this;
        }

        String stringify();
    }

    private interface Definable {
        String generate();
    }

    public sealed interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        T orElse(T other);

        T orElseGet(Supplier<T> other);

        Option<T> filter(Predicate<T> predicate);

        void ifPresent(Consumer<T> consumer);
    }

    private interface FunctionSegment {
        String generate();
    }

    private interface StatementValue {
        String generate();
    }

    private interface Assignable extends Node {
    }

    private interface Value extends Assignable {
    }

    private interface Parameter extends StatementValue {
        String generate();

        Option<Definition> toDefinition();
    }

    private sealed interface Result<T, X> permits Ok, Err {
        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);
    }

    private interface Rule<T> extends Function<String, Result<T, CompileError>> {
    }

    private interface Context {
        String display();
    }

    private interface Node {
        String generate();
    }

    private interface Error {
        String display();
    }

    private interface RootSegment extends Node {
    }

    private interface StructSegment extends Node {
    }

    private enum Primitive implements Type {
        I8("char"),
        I32("int"),
        Boolean("int"),
        Auto("auto");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public String stringify() {
            return this.name();
        }
    }

    private enum Operator {
        Equals("=="),
        Add("+");

        private final String representation;

        Operator(String representation) {
            this.representation = representation;
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
        public Option<T> filter(Predicate<T> predicate) {
            return predicate.test(this.value) ? this : new None<>();
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
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
        public Option<T> filter(Predicate<T> predicate) {
            return this;
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
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

        private String generatedWithName() {
            if (this.type instanceof Functional functional) {
                return functional.generateWithName(this.name);
            }

            return this.type.generate() + " " + this.name;
        }

        private String generateBeforeType() {
            return this.maybeBeforeType
                    .filter(inner -> !inner.isEmpty())
                    .map(inner -> generatePlaceholder(inner) + " ")
                    .orElse("");
        }

        private String generateTypeParams() {
            if (this.typeParams.isEmpty()) {
                return "";
            }

            return "<" + String.join(", ", this.typeParams) + ">";
        }

        @Override
        public Option<Definition> toDefinition() {
            return new Some<>(this);
        }

        public Definition rename(String name) {
            return new Definition(this.maybeBeforeType, this.type, name, this.typeParams);
        }

        public Definition mapTypeParams(Function<List<String>, List<String>> mapper) {
            return new Definition(this.maybeBeforeType, this.type, this.name, mapper.apply(this.typeParams));
        }

        public Definition withType(Type type) {
            return new Definition(this.maybeBeforeType, type, this.name, this.typeParams);
        }

        @Override
        public String generate() {
            var joinedTypeParams = this.generateTypeParams();
            var beforeType = this.generateBeforeType();
            var generatedWithName = this.generatedWithName();
            return beforeType + generatedWithName + joinedTypeParams;
        }
    }

    public record Struct(String name, List<String> typeArgs, Map<String, Type> definitions) implements Type {
        @Override
        public String generate() {
            var typeParamString = this.generateTypeParams();
            return "struct " + this.name + typeParamString;
        }

        private String generateTypeParams() {
            if (this.typeArgs.isEmpty()) {
                return "";
            }
            var joined = String.join(", ", this.typeArgs);
            return "<" + joined + ">";
        }

        @Override
        public String stringify() {
            return this.name;
        }

        public Option<Type> find(String property) {
            if (this.definitions.containsKey(property)) {
                return new Some<>(this.definitions.get(property));
            }
            return new None<>();
        }

        @Override
        public String toString() {
            return "Struct[" +
                    "name=" + this.name + ", " +
                    "typeArgs=" + this.typeArgs + ", " +
                    "definitions=" + this.definitions + ']';
        }

    }

    private record Ref(Type type) implements Type {
        @Override
        public String generate() {
            return this.type.generate() + "*";
        }

        @Override
        public String stringify() {
            return this.type.stringify() + "_star";
        }
    }

    private record Functional(List<Type> paramTypes, Type returnType) implements Type {
        @Override
        public String generate() {
            return this.generateWithName("");
        }

        @Override
        public String stringify() {
            var joined = this.paramTypes.stream()
                    .map(Type::stringify)
                    .collect(Collectors.joining("_"));

            return "fn_" + this.returnType.stringify() + "_" + joined;
        }

        public String generateWithName(String name) {
            var joined = this.paramTypes.stream()
                    .map(Type::generate)
                    .collect(Collectors.joining(", "));

            return this.returnType.generate() + " (*" +
                    name +
                    ")(" + joined + ")";
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

        @Override
        public Type flatten() {
            switch (this.base) {
                case "Function" -> {
                    var param = this.args.getFirst();
                    var returns = this.args.get(1);
                    return new Functional(Collections.singletonList(param), returns);
                }
                case "Supplier" -> {
                    var returns = this.args.getFirst();
                    return new Functional(Collections.emptyList(), returns);
                }
                case "Predicate" -> {
                    var param = this.args.getFirst();
                    return new Functional(Collections.singletonList(param), Primitive.Boolean);
                }
            }

            return this;
        }

        @Override
        public String stringify() {
            var joined = this.args.stream()
                    .map(Type::stringify)
                    .collect(Collectors.joining("_"));

            return this.base + "_" + joined;
        }
    }

    private record ConstructorDefinition(String name) implements Definable {
        private Definition toDefinition() {
            return new DefinitionBuilder().withType(new StructBuilder().withName(this.name()).complete()).withName("new_" + this.name()).complete();
        }

        @Override
        public String generate() {
            return this.name;
        }
    }

    private record Statement(StatementValue content) implements FunctionSegment, StructSegment {
        @Override
        public String generate() {
            return "\n\t" + this.content.generate() + ";";
        }
    }

    private static final class Whitespace implements RootSegment, FunctionSegment, StructSegment, Parameter, Type, Value {
        @Override
        public String generate() {
            return "";
        }

        @Override
        public String stringify() {
            return "Whitespace";
        }

        @Override
        public Option<Definition> toDefinition() {
            return new None<>();
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

        @Override
        public String stringify() {
            return this.input;
        }
    }

    private record Construction(Type type, List<Value> values) implements Value {
        @Override
        public String generate() {
            var joined = this.values.stream()
                    .map(Value::generate)
                    .collect(Collectors.joining(", "));

            return "new " + this.type.generate() + "(" + joined + ")";
        }

        private Invocation toInvocation() {
            var typeAsString = this.type.stringify();
            return new Invocation(new Symbol("new_" + typeAsString), this.values);
        }
    }

    private record Invocation(Value caller, List<Value> arguments) implements Value, StatementValue {
        @Override
        public String generate() {
            var joined = this.arguments.stream()
                    .map(Value::generate)
                    .collect(Collectors.joining(", "));

            return this.caller.generate() + "(" + joined + ")";
        }
    }

    private record StructNode(String name, List<String> typeParams) {
    }

    private record Frame(StructNode node, Map<String, Type> definitions) {
        public Frame(StructNode node) {
            this(node, new HashMap<>());
        }
    }

    private static class Options {
        public static <T> Stream<T> toStream(Option<T> option) {
            return option.map(Stream::of).orElseGet(Stream::empty);
        }

        public static <T> Option<T> fromNative(Optional<T> optional) {
            return optional.<Option<T>>map(Some::new).orElseGet(None::new);
        }
    }

    private record Ternary(Value condition, Value whenTrue, Value whenFalse) implements Value {
        @Override
        public String generate() {
            return this.condition.generate() + " ? " + this.whenTrue.generate() + " : " + this.whenFalse.generate();
        }
    }

    private record Number(String value) implements Value {
        @Override
        public String generate() {
            return this.value;
        }
    }

    private record Operation(Value left, Operator operator, Value right) implements Value {
        @Override
        public String generate() {
            return this.left.generate() + " " + this.operator.representation + " " + this.right.generate();
        }
    }

    private record PostIncrement(Value value) implements StatementValue {
        @Override
        public String generate() {
            return this.value.generate() + "++";
        }
    }

    private record PostDecrement(Value value) implements StatementValue {
        @Override
        public String generate() {
            return this.value.generate() + "--";
        }
    }

    public static class StructBuilder {
        private String name;
        private List<String> typeArgs = Collections.emptyList();
        private Map<String, Type> definitions = Collections.emptyMap();

        public StructBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public StructBuilder withTypeArgs(List<String> typeArgs) {
            this.typeArgs = typeArgs;
            return this;
        }

        public StructBuilder withDefinitions(Map<String, Type> definitions) {
            this.definitions = definitions;
            return this;
        }

        public Struct complete() {
            return new Struct(this.name, this.typeArgs, this.definitions);
        }
    }

    private record StringValue(String value) implements Value {
        @Override
        public String generate() {
            return "\"" + this.value + "\"";
        }
    }

    public record FunctionStatement(Definable definition, List<Parameter> params,
                                    List<FunctionSegment> content) {
        String generate() {
            var outputParamStrings = this.params().stream()
                    .map(Parameter::generate)
                    .collect(Collectors.joining(", "));

            var outputContent = this.content()
                    .stream()
                    .map(FunctionSegment::generate)
                    .collect(Collectors.joining());

            return this.definition().generate() + "(" + outputParamStrings + "){" + outputContent + "\n}\n";
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier) {
            return supplier.get().mapValue(otherValue -> new Tuple<>(this.value, otherValue));
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(this.error);
        }
    }

    private record StringContext(String value) implements Context {
        @Override
        public String display() {
            return this.value;
        }
    }

    private record CompileError(String message, Context context, List<CompileError> errors) implements Error {
        private CompileError(String message, Context context) {
            this(message, context, new ArrayList<>());
        }

        @Override
        public String display() {
            return this.format(0);
        }

        private String format(int depth) {
            var copy = new ArrayList<CompileError>(this.errors);
            copy.sort(Comparator.comparingInt(CompileError::computeMaxDepth));

            var joined = copy.stream()
                    .map(error -> error.format(depth + 1))
                    .map(displayed -> "\n" + "\t".repeat(depth) + displayed)
                    .collect(Collectors.joining());

            return this.message + ": " + this.context.display() + joined;
        }

        private int computeMaxDepth() {
            return 1 + this.errors.stream()
                    .mapToInt(CompileError::computeMaxDepth)
                    .max()
                    .orElse(0);
        }
    }

    private record NodeContext(Node node) implements Context {
        @Override
        public String display() {
            return this.node.generate();
        }
    }

    private record ApplicationError(Error error) implements Error {
        @Override
        public String display() {
            return this.error.display();
        }
    }

    private record ThrowableError(Throwable throwable) implements Error {
        @Override
        public String display() {
            var writer = new StringWriter();
            this.throwable.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    private record Namespaced(List<String> segments) implements RootSegment {
        @Override
        public String generate() {
            return "";
        }
    }

    private record EnumValues(Map<String, List<Value>> map) implements StatementValue {
        private static String generateEntry(Map.Entry<String, List<Value>> entry) {
            return entry.getKey() + "(" + entry.getValue().stream()
                    .map(Node::generate)
                    .collect(Collectors.joining(", ")) + ")";
        }

        @Override
        public String generate() {
            return this.map.entrySet()
                    .stream()
                    .map(EnumValues::generateEntry)
                    .collect(Collectors.joining(", "));
        }
    }

    private static final List<String> typeParams = new ArrayList<>();
    private static final List<StatementValue> statements = new ArrayList<>();
    public static List<String> structs;
    public static int localCounter = 0;
    private static List<FunctionStatement> functions;
    private static List<Frame> frames;

    public static void main() {
        structs = new ArrayList<>();
        functions = new ArrayList<>();
        frames = new ArrayList<>();

        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var target = source.resolveSibling("Main.c");
        runWithTarget(source, target).ifPresent(error -> System.err.println(error.display()));
    }

    private static Option<ApplicationError> runWithTarget(Path source, Path target) {
        return switch (readString(source)) {
            case Err<String, IOException>(var error) -> new Some<>(new ApplicationError(new ThrowableError(error)));
            case Ok<String, IOException>(var input) -> switch (compileRoot(input)) {
                case Err<String, CompileError>(var error) -> new Some<>(new ApplicationError(error));
                case Ok<String, CompileError>(var output) ->
                        writeString(target, output).map(ThrowableError::new).map(ApplicationError::new);
            };
        };
    }

    private static Option<IOException> writeString(Path target, String compiled) {
        try {
            Files.writeString(target, compiled);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }

    private static Result<String, IOException> readString(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Result<String, CompileError> compileRoot(String input) {
        return parseAll(input, Main::foldStatementChar, Main::parseRootSegment)
                .mapValue(Main::joinStatements)
                .mapValue(output -> {
                    var joinedStructs = String.join("", structs);

                    var joinedFunctions = functions.stream()
                            .map(FunctionStatement::generate)
                            .collect(Collectors.joining(""));

                    return output + joinedStructs + joinedFunctions;
                });
    }

    private static String joinStatements(List<RootSegment> rootSegments) {
        return rootSegments.stream()
                .map(Node::generate)
                .collect(Collectors.joining());
    }

    private static Result<String, CompileError> compileStatements(String input, Function<String, Result<String, CompileError>> compiler) {
        return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements);
    }

    private static Result<String, CompileError> compileAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, Result<String, CompileError>> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        return parseAll(input, folder, compiler).mapValue(listOption -> generateAll(merger, listOption));
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

    private static <T> Result<List<T>, CompileError> parseAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, Result<T, CompileError>> compiler
    ) {
        var segments = divide(input, folder);

        Result<List<T>, CompileError> compiled = new Ok<>(new ArrayList<T>());
        for (var segment : segments) {
            compiled = compiled.and(() -> compiler.apply(segment)).mapValue(tuple -> {
                tuple.left.add(tuple.right);
                return tuple.left;
            });
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

            if (c == '\"') {
                current.append(c);

                while (true) {
                    if (i == input.length() - 1) {
                        break;
                    }

                    i++;
                    var next = input.charAt(i);
                    current.append(next);

                    if (next == '\\') {
                        i++;
                        current.append(input.charAt(i));
                    }

                    if (next == '\"') {
                        break;
                    }
                }

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

    private static Result<RootSegment, CompileError> parseRootSegment(String input) {
        return parseOr(input, List.of(
                createNamespacedRule("package "),
                createNamespacedRule("import "),
                input0 -> parseClass(input0).mapValue(value -> value)
        ));
    }

    private static Rule<RootSegment> createNamespacedRule(String prefix) {
        return input -> {
            var stripped = input.strip();
            if (!stripped.startsWith(prefix)) {
                return createPrefixErr(stripped, prefix);
            }

            var slice = stripped.substring(prefix.length()).strip();
            if (!slice.endsWith(";")) {
                return createSuffixErr(slice, ";");
            }

            var inner = slice.substring(0, slice.length() - ";".length());
            var array = inner.split(Pattern.quote("."));
            return new Ok<>(new Namespaced(Arrays.asList(array)));
        };
    }

    private static Err<RootSegment, CompileError> createPrefixErr(String stripped, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", new StringContext(stripped)));
    }

    private static Result<Whitespace, CompileError> parseClass(String stripped) {
        return structured(stripped, "class ");
    }

    private static Result<Whitespace, CompileError> structured(String stripped, String infix) {
        var classIndex = stripped.indexOf(infix);
        if (classIndex < 0) {
            return createInfixErr(stripped, infix);
        }

        var afterKeyword = stripped.substring(classIndex + infix.length());
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return createInfixErr(afterKeyword, "{");
        }

        var beforeContent = afterKeyword.substring(0, contentStart).strip();
        var withEnd = afterKeyword.substring(contentStart + "{".length());

        var permitsIndex = beforeContent.indexOf(" permits ");
        var withoutPermits = permitsIndex >= 0
                ? beforeContent.substring(0, permitsIndex).strip()
                : beforeContent;

        var implementsIndex = withoutPermits.indexOf(" implements ");
        var withoutImplements = implementsIndex >= 0
                ? withoutPermits.substring(0, implementsIndex).strip()
                : withoutPermits;

        var extendsIndex = withoutImplements.indexOf(" extends ");
        var withoutExtends = extendsIndex >= 0
                ? withoutImplements.substring(0, extendsIndex).strip()
                : withoutImplements;

        var paramStart = withoutExtends.indexOf("(");
        if (paramStart >= 0) {
            String withoutParameters = withoutExtends.substring(0, paramStart).strip();
            var withParamEnd = withoutExtends.substring(paramStart + "(".length()).strip();
            if (withParamEnd.endsWith(")")) {
                var inputParams = withParamEnd.substring(0, withParamEnd.length() - ")".length());
                return parseParameters(inputParams).flatMapValue(
                        params -> compileStructuredWithParams(withoutParameters, withEnd, params));
            }
        }

        return compileStructuredWithParams(withoutExtends, withEnd, new ArrayList<>());
    }

    private static <T> Result<T, CompileError> createInfixErr(String input, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", new StringContext(input)));
    }

    private static Result<Whitespace, CompileError> compileStructuredWithParams(String withoutParameters, String withEnd, List<Parameter> params) {
        var typeParamStart = withoutParameters.indexOf("<");
        if (typeParamStart >= 0) {
            String name = withoutParameters.substring(0, typeParamStart).strip();
            var withTypeParamEnd = withoutParameters.substring(typeParamStart + "<".length()).strip();
            if (withTypeParamEnd.endsWith(">")) {
                var typeParamsString = withTypeParamEnd.substring(0, withTypeParamEnd.length() - ">".length());
                return parseValues(typeParamsString, Ok::new).flatMapValue(actualTypeParams -> assembleStructured(name, withEnd, actualTypeParams, params));
            }
        }

        return assembleStructured(withoutParameters, withEnd, Collections.emptyList(), params);
    }

    private static Result<Whitespace, CompileError> assembleStructured(String name, String input, List<String> typeParams, List<Parameter> params) {
        if (!isSymbol(name)) {
            return new Err<>(new CompileError("Not a symbol", new StringContext(name)));
        }

        String withEnd = input.strip();
        if (!withEnd.endsWith("}")) {
            return new Err<>(new CompileError("Suffix '}' not present", new StringContext(withEnd)));
        }

        var content = withEnd.substring(0, withEnd.length() - "}".length());

        var typeParamString = typeParams.isEmpty() ? "" : "<" + String.join(", ", typeParams) + ">";

        var structNode = new StructNode(name, typeParams);
        frames.addLast(new Frame(structNode));

        return parseAll(content, Main::foldStatementChar, Main::parseClassSegment).mapValue(outputStatements -> {
            var copy = new ArrayList<StructSegment>(params.stream()
                    .map(Statement::new)
                    .toList());

            copy.addAll(outputStatements);
            var joined = copy.stream()
                    .map(Node::generate)
                    .collect(Collectors.joining());

            var generated = "struct " + name + typeParamString + " {" + joined + "\n};\n";
            frames.removeLast();
            structs.add(generated);
            return new Whitespace();
        });
    }

    private static boolean isSymbol(String input) {
        if (input.isEmpty() || input.equals("private")) {
            return false;
        }

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Result<StructSegment, CompileError> parseClassSegment(String input0) {
        return Main.parseOr(input0, List.of(
                structSegmentFrom(Main::whitespace),
                structSegmentFromStructured("enum "),
                structSegmentFromStructured("class "),
                structSegmentFromStructured("record "),
                structSegmentFromStructured("interface "),
                structSegmentFrom(Main::method),
                structSegmentFrom(Main::classStatement)
        ));
    }

    private static Result<Statement, CompileError> classStatement(String input) {
        return parseStatementWithoutBraces(input, Main::compileClassStatementValue);
    }

    private static <T extends StructSegment> Rule<StructSegment> structSegmentFrom(Rule<T> whitespace) {
        return input -> whitespace.apply(input).mapValue(value -> value);
    }

    private static Rule<StructSegment> structSegmentFromStructured(String infix) {
        return structSegmentFrom(input -> structured(input, infix));
    }

    private static Result<Whitespace, CompileError> whitespace(String input) {
        if (input.isBlank()) {
            return new Ok<>(new Whitespace());
        }
        else {
            return new Err<>(new CompileError("Not blank", new StringContext(input)));
        }
    }

    private static Result<Whitespace, CompileError> method(String input) {
        var paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return createInfixErr(input, "(");
        }

        var beforeParams = input.substring(0, paramStart).strip();
        var withParams = input.substring(paramStart + "(".length());


        var currentStruct = frames.getLast().node;
        var currentStructName = currentStruct.name;
        var currentStructTypeParams = currentStruct.typeParams;
        typeParams.addAll(currentStructTypeParams);

        var definableCompileErrorResult = parseOr(beforeParams, List.<Rule<Definable>>of(
                input0 -> parseDefinition(input0).mapValue(value -> value),
                input0 -> compileConstructorDefinition(input0).mapValue(value -> value)
        ));

        return definableCompileErrorResult.flatMapValue(header -> {
            var paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) {
                return createInfixErr(withParams, ")");
            }

            var paramStrings = withParams.substring(0, paramEnd).strip();
            var afterParams = withParams.substring(paramEnd + ")".length()).strip();
            if (afterParams.startsWith("{") && afterParams.endsWith("}")) {
                var content = afterParams.substring(1, afterParams.length() - 1);

                return parseParameters(paramStrings).flatMapValue(inputParams -> {
                    var paramDefinitions = inputParams.stream()
                            .map(Parameter::toDefinition)
                            .flatMap(Options::toStream)
                            .toList();

                    var params = inputParams
                            .stream()
                            .filter(node -> !(node instanceof Whitespace))
                            .toList();

                    defineAll(paramDefinitions);

                    var maybeOldStatements = parseStatements(content, Main::parseStatement);
                    typeParams.clear();

                    return maybeOldStatements.mapValue(oldStatements -> {
                        var list = statements.stream()
                                .map(Statement::new)
                                .toList();
                        statements.clear();
                        localCounter = 0;

                        ArrayList<FunctionSegment> newStatements;
                        if (header instanceof ConstructorDefinition(var name)) {
                            var copy = new ArrayList<FunctionSegment>(list);

                            copy.add(new Statement(new DefinitionBuilder()
                                    .withType(new StructBuilder().withName(name).withTypeArgs(currentStructTypeParams).complete())
                                    .withName("this")
                                    .complete()));

                            copy.addAll(oldStatements);
                            copy.add(new Statement(new Return(new Symbol("this"))));
                            newStatements = copy;
                        }
                        else {
                            newStatements = new ArrayList<>(list);
                            newStatements.addAll(oldStatements);
                        }


                        Definable newDefinition;
                        var outputParams = new ArrayList<Parameter>();
                        if (header instanceof Definition oldDefinition) {
                            outputParams.add(new DefinitionBuilder()
                                    .withType(new StructBuilder().withName(currentStructName).withTypeArgs(currentStructTypeParams).complete())
                                    .withName("this")
                                    .complete());

                            var paramTypes = paramDefinitions.stream()
                                    .map(definition -> definition.type)
                                    .toList();

                            var complete = new DefinitionBuilder()
                                    .withName(oldDefinition.name)
                                    .withType(new Functional(paramTypes, oldDefinition.type))
                                    .complete();
                            define(complete);

                            newDefinition = oldDefinition.rename(oldDefinition.name + "_" + currentStructName).mapTypeParams(typeParams1 -> {
                                ArrayList<String> copy = new ArrayList<>(currentStructTypeParams);
                                copy.addAll(typeParams1);
                                return copy;
                            });
                        }
                        else if (header instanceof ConstructorDefinition constructorDefinition) {
                            var definition = constructorDefinition.toDefinition();
                            newDefinition = definition;

                            define(definition);
                        }
                        else {
                            newDefinition = header;
                        }

                        outputParams.addAll(params);

                        var constructor = new FunctionStatement(newDefinition, outputParams, newStatements);
                        functions.add(constructor);
                        return new Whitespace();
                    });
                });
            }

            if (afterParams.equals(";")) {
                return new Ok<>(new Whitespace());
            }

            return new Err<>(new CompileError("Invalid body", new StringContext(afterParams)));
        });

    }

    private static Result<List<Parameter>, CompileError> parseParameters(String paramStrings) {
        return parseValues(paramStrings, Main::parseParameter);
    }

    private static void defineAll(List<Definition> definitions) {
        for (var definition : definitions) {
            define(definition);
        }
    }

    private static void define(Definition definition) {
        frames.getLast().definitions.put(definition.name, definition.type);
    }

    private static <T> Result<List<T>, CompileError> parseStatements(String content, Function<String, Result<T, CompileError>> compiler) {
        return parseAll(content, Main::foldStatementChar, compiler);
    }

    private static Result<ConstructorDefinition, CompileError> compileConstructorDefinition(String input) {
        return findConstructorDefinitionName(input).flatMapValue(name -> {
            if (frames.getLast().node.name.equals(name)) {
                return new Ok<>(new ConstructorDefinition(name));
            }
            return new Err<>(new CompileError("Constructor name didn't match", new StringContext(name)));
        });
    }

    private static Result<String, CompileError> findConstructorDefinitionName(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var name = stripped.substring(nameSeparator + " ".length());
            return new Ok<>(name);
        }

        if (isSymbol(stripped)) {
            return new Ok<>(stripped);
        }

        return createSymbolErr(stripped);
    }

    private static Result<FunctionSegment, CompileError> parseStatement(String input0) {
        return parseOr(input0, List.of(
                input -> whitespace(input).mapValue(value -> value),
                input -> parseStatementWithoutBraces(input, Main::parseStatementValue)
                        .mapValue(value -> value)
        ));
    }

    private static <T> Result<T, CompileError> parseOr(String input, List<Rule<T>> rules) {
        List<CompileError> errors = new ArrayList<>();
        for (var rule : rules) {
            var result = rule.apply(input);
            switch (result) {
                case Err<T, CompileError>(var error) -> errors.add(error);
                case Ok<T, CompileError>(var value) -> {
                    return new Ok<>(value);
                }
            }
        }

        return new Err<>(new CompileError(input, new StringContext("No valid rule present"), errors));
    }

    private static Result<StatementValue, CompileError> parseStatementValue(String input0) {
        return parseOr(input0, List.of(
                input -> parseReturn(input).mapValue(value -> value),
                input -> parsePostIncrement(input).mapValue(value -> value),
                input -> parsePostDecrement(input).mapValue(value -> value),
                input -> parseInvocation(input).mapValue(value -> value),
                input -> parseAssignment(input).mapValue(value -> value),
                input -> parseDefinition(input).mapValue(value -> value)
        ));
    }

    private static Result<PostDecrement, CompileError> parsePostDecrement(String input) {
        var stripped = input.strip();
        if (stripped.endsWith("--")) {
            var slice = stripped.substring(0, stripped.length() - "--".length());
            return parseValue(slice).mapValue(PostDecrement::new);
        }
        else {
            return createSuffixErr(stripped, "--");
        }
    }

    private static Result<PostIncrement, CompileError> parsePostIncrement(String input) {
        var stripped = input.strip();
        if (stripped.endsWith("++")) {
            return parseValue(stripped.substring(0, stripped.length() - "++".length())).mapValue(PostIncrement::new);
        }
        else {
            return createSuffixErr(stripped, "++");
        }
    }

    private static Result<Return, CompileError> parseReturn(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            return parseValue(stripped.substring("return ".length())).mapValue(Return::new);
        }

        return new Err<>(new CompileError("Prefix 'return " + "' not present", new StringContext(stripped)));
    }

    private static Result<Statement, CompileError> parseStatementWithoutBraces(
            String input,
            Function<String, Result<StatementValue, CompileError>> compiler
    ) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return compiler.apply(withoutEnd).mapValue(Statement::new);
        }
        else {
            return createSuffixErr(input, ";");
        }
    }

    private static <T> Result<T, CompileError> createSuffixErr(String input, String suffix) {
        return new Err<>(new CompileError("Suffix '" + suffix + "' not present", new StringContext(input)));
    }

    private static Result<StatementValue, CompileError> compileClassStatementValue(String input0) {
        return parseOr(input0, List.of(
                input -> parseAssignment(input).mapValue(value -> value),
                input -> parseDefinition(input).mapValue(value -> value),
                Main::parseEnumValues
        ));
    }

    private static Result<StatementValue, CompileError> parseEnumValues(String input) {
        var slices = input.split(Pattern.quote(","));
        var map = new HashMap<String, List<Value>>();
        for (var slice : slices) {
            var stripped = slice.strip();
            if (isSymbol(stripped)) {
                map.put(stripped, Collections.emptyList());
            }
            else {
                var result = Main.parseInvokable(stripped, Main::compileSymbol, Tuple::new);
                switch (result) {
                    case Err<Tuple<String, List<Value>>, CompileError>(var error) -> {
                        return new Err<>(error);
                    }
                    case Ok<Tuple<String, List<Value>>, CompileError>(var tuple) -> {
                        map.put(tuple.left, tuple.right);
                    }
                }
            }
        }
        return new Ok<>(new EnumValues(map));
    }

    private static Result<String, CompileError> compileSymbol(String s) {
        if (isSymbol(s)) {
            return new Ok<>(s);
        }
        else {
            return createSymbolErr(s);
        }
    }

    private static Result<Assignment, CompileError> parseAssignment(String input) {
        var valueSeparator = input.indexOf("=");
        if (valueSeparator < 0) {
            return createInfixErr(input, "=");
        }

        var inputDefinition = input.substring(0, valueSeparator);
        var value = input.substring(valueSeparator + "=".length());
        return parseValue(value).flatMapValue(parsedValue -> {
            var maybeAssignable = parseOr(inputDefinition, List.<Rule<Assignable>>of(
                    input0 -> parseDefinition(input0).mapValue(value0 -> value0),
                    input0 -> parseValue(input0).mapValue(value0 -> value0)
            ));

            return maybeAssignable.flatMapValue(destination -> {
                if (destination instanceof Definition definition) {
                    if (definition.type.equals(Primitive.Auto)) {
                        return resolveType(parsedValue).mapValue(resolved -> {
                            var withType = definition.withType(resolved);
                            return new Assignment(withType, parsedValue);
                        });
                    }
                }

                return new Ok<>(new Assignment(destination, parsedValue));
            });
        });
    }

    private static Result<Value, CompileError> parseValue(String input) {
        var stripped = input.strip();
        if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith("\"")) {
            var slice = stripped.substring(1, stripped.length() - 1);
            return new Ok<>(new StringValue(slice));
        }

        if (isSymbol(stripped)) {
            return new Ok<>(new Symbol(stripped));
        }

        if (isNumber(stripped)) {
            return new Ok<>(new Number(stripped));
        }

        if (stripped.startsWith("new ")) {
            var substring = stripped.substring("new ".length());
            return parseInvokable(substring, Main::parseType, Construction::new)
                    .mapValue(Construction::toInvocation);
        }

        var maybeInvocation = parseInvocation(stripped);
        if (maybeInvocation instanceof Ok(var invocation)) {
            return new Ok<>(invocation);
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parentString = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            if (isSymbol(property)) {
                return parseValue(parentString).flatMapValue(parent -> resolveType(parent).mapValue(type -> {
                    if (type instanceof Functional) {
                        return parent;
                    }

                    return new DataAccess(parent, property);
                }));
            }
        }

        var conditionSeparator = stripped.indexOf("?");
        if (conditionSeparator >= 0) {
            var conditionString = stripped.substring(0, conditionSeparator);
            var afterCondition = stripped.substring(conditionSeparator + "?".length());
            var actionSeparator = afterCondition.indexOf(":");
            if (actionSeparator >= 0) {
                var whenTrueString = afterCondition.substring(0, actionSeparator);
                var whenFalseString = afterCondition.substring(actionSeparator + ":".length());

                var maybeCondition = parseValue(conditionString);
                var maybeWhenTrue = parseValue(whenTrueString);
                var maybeWhenFalse = parseValue(whenFalseString);

                if (maybeCondition instanceof Ok(var condition)
                        && maybeWhenTrue instanceof Ok(var whenTrue)
                        && maybeWhenFalse instanceof Ok(var whenFalse)) {
                    return new Ok<>(new Ternary(condition, whenTrue, whenFalse));
                }
            }
        }

        var rules = Arrays.stream(Operator.values())
                .map(operator -> (Rule<Value>) input0 -> parseOperator(input0, operator).mapValue(value -> value))
                .toList();

        return parseOr(input, rules);
    }

    private static Result<Operation, CompileError> parseOperator(String input, Operator operator) {
        var operatorIndex = input.indexOf(operator.representation);
        if (operatorIndex < 0) {
            return createInfixErr(input, operator.representation);
        }

        var leftString = input.substring(0, operatorIndex);
        var rightString = input.substring(operatorIndex + operator.representation.length());

        return parseValue(leftString)
                .and(() -> parseValue(rightString))
                .mapValue(tuple -> new Operation(tuple.left, operator, tuple.right));
    }

    private static Result<Invocation, CompileError> parseInvocation(String stripped) {
        return parseInvokable(stripped, Main::parseValue, Invocation::new).flatMapValue(invocation -> {
            var caller = invocation.caller;
            if (!(caller instanceof DataAccess(var parent, var property))) {
                return new Ok<>(invocation);
            }

            return resolveType(parent).mapValue(resolved -> {
                Value newParent;
                if (parent instanceof Symbol || parent instanceof DataAccess) {
                    newParent = parent;
                }
                else {
                    var name = "local" + localCounter;
                    newParent = new Symbol(name);
                    localCounter++;

                    statements.add(new Assignment(new DefinitionBuilder()
                            .withType(resolved)
                            .withName(name)
                            .complete(), parent));
                }

                var arguments = new ArrayList<Value>();
                arguments.add(newParent);
                arguments.addAll(invocation.arguments
                        .stream()
                        .filter(argument -> !(argument instanceof Whitespace))
                        .toList());

                return new Invocation(new DataAccess(newParent, property), arguments);
            });

        });
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

    private static Result<Type, CompileError> resolveType(Value value) {
        if (value instanceof DataAccess(var parent, var property)) {
            return resolveType(parent).flatMapValue(parentType -> {
                var context = new NodeContext(parent);
                if (!(parentType instanceof Struct struct)) {
                    return new Err<>(new CompileError("Not a struct", context));
                }
                var maybeFound = struct.find(property);
                if (maybeFound instanceof Some(var found)) {
                    return new Ok<>(found);
                }
                return new Err<>(new CompileError("Property '" + property + "' not present", context));
            });
        }

        if (value instanceof Invocation(var base, var _)) {
            return resolveType(base).flatMapValue(resolved -> {
                if (resolved instanceof Functional functional) {
                    return new Ok<>(functional.returnType);
                }
                else {
                    return new Err<>(new CompileError("Type is not functional", new NodeContext(resolved)));
                }
            });
        }

        if (value instanceof Symbol(var name)) {
            if (name.equals("this")) {
                var definitions = frames.getLast().definitions;
                return new Ok<>(new StructBuilder()
                        .withName(name)
                        .withDefinitions(definitions)
                        .complete());
            }

            var maybeType = Options.fromNative(frames.stream()
                    .map(frame -> findNameInFrame(name, frame))
                    .flatMap(Options::toStream)
                    .findFirst());

            if (maybeType instanceof Some(var type)) {
                return new Ok<>(type);
            }
        }

        return new Err<>(new CompileError("Unknown value", new NodeContext(value)));
    }

    private static Option<Type> findNameInFrame(String name, Frame frame) {
        var definitions = frame.definitions;
        if (definitions.containsKey(name)) {
            return new Some<>(definitions.get(name));
        }
        else {
            return new None<>();
        }
    }

    private static <T, R> Result<R, CompileError> parseInvokable(
            String input,
            Function<String, Result<T, CompileError>> beforeArgsCaller,
            BiFunction<T, List<Value>, R> builder
    ) {
        var withoutPrefix = input.strip();
        if (!withoutPrefix.endsWith(")")) {
            return createSuffixErr(withoutPrefix, ")");
        }

        var withoutEnd = withoutPrefix.substring(0, withoutPrefix.length() - ")".length());

        var slices = divideAll(withoutEnd, Main::foldInvocationStart);

        var beforeLast = slices.subList(0, slices.size() - 1);
        var joined = String.join("", beforeLast).strip();
        if (!joined.endsWith("(")) {
            return createSuffixErr(joined, "(");
        }

        var beforeArgsStart = joined.substring(0, joined.length() - 1);
        var args = slices.getLast();

        return beforeArgsCaller.apply(beforeArgsStart).flatMapValue(outputBeforeArgs -> parseValues(args, Main::parseArgument).mapValue(values -> builder.apply(outputBeforeArgs, values)));
    }

    private static Result<Value, CompileError> parseArgument(String input) {
        return parseOr(input, List.of(
                input0 -> whitespace(input0).mapValue(arg -> arg),
                input0 -> parseValue(input0).mapValue(arg -> arg)
        ));
    }

    private static State foldInvocationStart(State state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            State advanced = appended.isLevel() ? appended.advance() : appended;
            return advanced.enter();
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static Result<Parameter, CompileError> parseParameter(String input) {
        return parseOr(input, List.of(
                input0 -> whitespace(input0).mapValue(result -> result),
                input0 -> parseDefinition(input0).mapValue(result -> result)
        ));
    }

    private static Result<Definition, CompileError> parseDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return createInfixErr(stripped, " ");
        }

        var beforeName = stripped.substring(0, nameSeparator).strip();
        var name = stripped.substring(nameSeparator + " ".length()).strip();
        if (!isSymbol(name)) {
            return createSymbolErr(name);
        }

        var withName = new DefinitionBuilder().withName(name);
        return switch (findTypeSeparator(beforeName)) {
            case None<Integer> _ -> parseAndFlattenType(beforeName).mapValue(type -> new DefinitionBuilder()
                    .withType(type)
                    .withName(name)
                    .complete());

            case Some<Integer>(var typeSeparator) -> {
                var beforeType = beforeName.substring(0, typeSeparator).strip();
                var inputType = beforeName.substring(typeSeparator + " ".length()).strip();
                yield getDefinitionCompileErrorResult(beforeType, inputType, withName);
            }
        };
    }

    private static Result<Definition, CompileError> getDefinitionCompileErrorResult(String beforeType, String inputType, DefinitionBuilder withName) {
        if (beforeType.endsWith(">")) {
            var withTypeParamStart = beforeType.substring(0, beforeType.length() - ">".length());

            var typeParamStart = withTypeParamStart.lastIndexOf("<");
            if (typeParamStart >= 0) {
                var withoutTypeParams = beforeType.substring(0, typeParamStart);
                var typeParamString = withTypeParamStart.substring(typeParamStart + "<".length());

                return parseValues(typeParamString, Ok::new).flatMapValue(actualTypeParams -> {
                    Main.typeParams.addAll(actualTypeParams);
                    var maybeOutputType = parseAndFlattenType(inputType);

                    return maybeOutputType.mapValue(outputType -> withName
                            .withBeforeType(withoutTypeParams)
                            .withTypeParams(actualTypeParams)
                            .withType(outputType)
                            .complete());
                });
            }
        }

        return parseAndFlattenType(inputType).mapValue(outputType -> withName
                .withBeforeType(beforeType)
                .withType(outputType)
                .complete());
    }

    private static <T> Result<T, CompileError> createSymbolErr(String name) {
        return new Err<>(new CompileError("Not a symbol", new StringContext(name)));
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

    private static Result<Type, CompileError> parseAndFlattenType(String input) {
        return parseType(input).mapValue(Type::flatten);
    }

    private static Result<Type, CompileError> parseType(String input) {
        var stripped = input.strip();
        switch (stripped) {
            case "var" -> {
                return new Ok<>(Primitive.Auto);
            }
            case "int" -> {
                return new Ok<>(Primitive.I32);
            }
            case "char" -> {
                return new Ok<>(Primitive.I8);
            }
            case "String" -> {
                return new Ok<>(new Ref(Primitive.I8));
            }
            case "boolean" -> {
                return new Ok<>(Primitive.Boolean);
            }
        }

        if (typeParams.contains(stripped)) {
            return new Ok<>(new TypeParam(stripped));
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                var base = withoutEnd.substring(0, argsStart).strip();
                var argsString = withoutEnd.substring(argsStart + "<".length()).strip();
                return parseValues(argsString, Main::parseTypeOrBlank).mapValue(args -> new Generic(base, args));
            }
        }

        if (isSymbol(stripped)) {
            return new Ok<>(new StructBuilder().withName(stripped).complete());
        }

        return new Err<>(new CompileError("No valid type present", new StringContext(input)));
    }

    private static Result<Type, CompileError> parseTypeOrBlank(String input) {
        return parseOr(input, List.of(
                input0 -> whitespace(input0).mapValue(type -> type),
                Main::parseAndFlattenType
        ));
    }

    private static <T> Result<List<T>, CompileError> parseValues(String args, Function<String, Result<T, CompileError>> compiler) {
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
