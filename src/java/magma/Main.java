package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static magma.Lists.listEmpty;

public class Main {
    public sealed interface Option<T> permits Some, None {
        <R> Option<R> map(Function<T, R> mapper);

        T orElse(T other);

        T orElseGet(Supplier<T> supplier);

        <R> Option<R> flatMap_(Function<T, Option<R>> mapper);

        Option<T> or(Supplier<Option<T>> supplier);

        void ifPresent(Consumer<T> consumer);

        boolean isPresent();
    }

    public interface List<T> {
        List<T> addLast(T element);

        Iterator<T> iterate();

        boolean isEmpty();

        boolean contains(T element, BiFunction<T, T, Boolean> equator);

        Option<Integer> indexOf(T element);

        Option<T> get(int index);

        int size();

        List<T> subList(int startInclusive, int endExclusive);

        Option<T> findLast();

        List<T> addAll(List<T> elements);

        Option<Tuple<T, List<T>>> removeLast();

        List<T> mapLast(Function<T, T> mapper);

        Iterator<Tuple<Integer, T>> iterateWithIndices();

        List<T> sort(BiFunction<T, T, Integer> comparator);

        Iterator<T> iterateReversed();

        boolean equalsTo(List<T> other, BiFunction<T, T, Boolean> equator);

        Option<Tuple<List<T>, T>> popLast();
    }

    private interface Head<T> {
        Option<T> next();
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface String_ {
        String_ sliceTo(int index);

        String_ appendSlice(String slice);

        String_ toLowerCase();

        int length();

        char charAt(int index);

        String_ appendChar(char c);

        String_ appendOwned(String_ other);

        String toSlice();

        String_ strip();

        String_ repeat(int depth);

        boolean isBlank();

        boolean startsWithSlice(String slice);

        int indexOfSlice(String slice);

        String_ sliceFrom(int startInclusive);

        String_ sliceBetween(int startInclusive, int endExclusive);

        boolean endsWithSlice(String slice);

        boolean equalsToSlice(String slice);

        int indexOfOwned(String_ other);

        Option<Integer> lastIndexOfSlice(String slice);

        boolean isEmpty();

        boolean equalsTo(String_ other);
    }

    private interface Parameter extends Assignable {
        String_ generate();
    }

    private sealed interface Value extends Assignable {
    }

    private interface Node {
        String_ generate();
    }

    private interface Assignable extends Node {
    }

    private sealed interface Result<T, X> permits Ok, Err {
        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier);

        <R> Result<T, R> mapErr(Function<X, R> mapper);
    }

    private interface Type extends Node {
        boolean hasTypeParams();

        String_ stringify();

        boolean equalsTo(Type other);
    }

    private interface Error {
        String_ display();
    }

    private interface Context {
        String_ display();
    }

    private @interface External {
    }

    private interface Map_<K, V> {
        Iterator<V> values();

        Map_<K, V> put(K key, V value);

        boolean containsKey(K key);

        V get(K key);

        Iterator<K> keys();
    }

    private interface Frames {
        Option<Type> findTypeByName(String name);

        Option<StructType> findCurrentStructType();

        Iterator<Definition> streamDefined();

        Option<StructRef> findCurrentRef();

        Frames defineAll(List<Definition> definitions);

        Frames define(Definition definition);

        Frames exit();

        Frames enter();

        Frames withRef(StructRef ref);
    }

    private static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java");
    private static final Path TARGET = SOURCE.resolveSibling("main.c");
    private static List<List<String_>> generatedStatements = listEmpty();
    private static List<String_> generatedMethods = listEmpty();
    private static Map_<Generic, Tuple<StructType, String_>> structRegistry = Maps.empty();
    private static Map_<String_, Function<List<Type>, Result<String_, CompileError>>> expanding = Maps.empty();
    private static List<Generic> visitedExpansions = listEmpty();
    private static Frames frames = new ImmutableFrames();
    private static String_ functionName = Strings.empty();
    private static List<Tuple<List<String_>, List<Type>>> typeParamStack = listEmpty();
    private static int functionLocalCounter = 0;
    private static List<Type> typeStack = listEmpty();

    private static Option<ApplicationError> run() {
        return switch (readString_()) {
            case Err<String_, IOException>(var error) -> new Some<>(new ApplicationError(new ThrowableError(error)));
            case Ok<String_, IOException>(var input) -> switch (compileRoot(input)) {
                case Err<String_, CompileError>(var result) -> new Some<>(new ApplicationError(result));
                case Ok<String_, CompileError>(var output) -> writeTarget(output)
                        .map(ThrowableError::new)
                        .map(ApplicationError::new);
            };
        };
    }

    @External
    private static void printError(String slice) {
        System.err.println(slice);
    }

    public static void main() {
        run().ifPresent(error -> printError(error.display().toSlice()));
    }

    private static Option<IOException> writeTarget(String_ csq) {
        try {
            Files.writeString(Main.TARGET, csq.toSlice());
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }

    private static Result<String_, IOException> readString_() {
        try {
            return new Ok<>(Strings.from(Files.readString(Main.SOURCE)));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Result<String_, CompileError> compileRoot(String_ input) {
        return parseStatements(input, Main::compileRootSegment).mapValue(parsed -> {
            var compiled = generateAll(Main::mergeStatements, parsed);
            var joinedStructs = joinStructures();
            return compiled.appendOwned(joinedStructs).appendOwned(join(generatedMethods));
        });
    }

    private static String_ joinStructures() {
        return structRegistry.values()
                .map(Tuple::right)
                .collect(new Joiner())
                .orElse(Strings.empty());
    }

    private static String_ join(List<String_> list) {
        return list.iterate()
                .collect(new Joiner())
                .orElse(Strings.empty());
    }

    private static <T> Result<List<T>, CompileError> parseStatements(String_ input, Function<String_, Result<T, CompileError>> compiler) {
        return parseAll(input, Main::foldStatementChar, compiler);
    }

    private static String_ generateAll(BiFunction<String_, String_, String_> merger, List<String_> parsed) {
        return parsed.iterate().fold(Strings.empty(), merger);
    }

    private static <T> Result<List<T>, CompileError> parseAll(
            String_ input,
            BiFunction<State, Character, State> folder,
            Function<String_, Result<T, CompileError>> compiler
    ) {
        return divideAll(input, folder)
                .iterate()
                .map(compiler)
                .collect(new Exceptional<>(new ListCollector<>()));
    }

    private static String_ mergeStatements(String_ buffer, String_ element) {
        return buffer.appendOwned(element);
    }

    private static List<String_> divideAll(String_ input, BiFunction<State, Character, State> folder) {
        State state = State.fromInput(input);
        while (true) {
            var maybeNextTuple = state.pop();
            if (maybeNextTuple instanceof None<Tuple<Character, State>>) {
                break;
            }
            if (maybeNextTuple instanceof Some<Tuple<Character, State>>(var nextTuple)) {
                var next = nextTuple.left;
                var withoutNext = nextTuple.right;

                state = foldSingleQuotes(withoutNext, next)
                        .or(() -> foldDoubleQuotes(withoutNext, next))
                        .orElseGet(() -> folder.apply(withoutNext, next));
            }
        }

        return state.advance().segments;
    }

    private static Option<State> foldDoubleQuotes(State withoutNext, char c) {
        if (c != '\"') {
            return new None<>();
        }

        var current = withoutNext.append(c);
        while (true) {
            var maybeNext = current.popAndAppendToTuple();
            if (!(maybeNext instanceof Some(var next))) {
                break;
            }

            current = next.right;
            if (next.left == '"') {
                break;
            }
            if (next.left == '\\') {
                current = current.popAndAppend().orElse(current);
            }
        }

        return new Some<>(current);
    }

    private static Option<State> foldSingleQuotes(State state, char next) {
        if (next != '\'') {
            return new None<>();
        }

        var appended = state.append(next);
        return appended.popAndAppendToTuple()
                .flatMap_(maybeSlash -> maybeSlash.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(maybeSlash.right))
                .flatMap_(State::popAndAppend);
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

    private static Result<String_, CompileError> compileRootSegment(String_ input) {
        return orString(input, Lists.listFrom(
                type("?", input1 -> parseWhitespace(input1).mapValue(Whitespace::generate)),
                type("?", Main::compileNamespaced),
                type("?", Main::compileClass)
        ));
    }

    private static Result<String_, CompileError> compileNamespaced(String_ input) {
        if (input.strip().startsWithSlice("package ") || input.strip().startsWithSlice("import ")) {
            return new Ok<>(Strings.empty());
        }
        else {
            return new Err<>(new CompileError("Not namespaced", new StringContext(input.strip())));
        }
    }

    private static Result<String_, CompileError> compileClass(String_ stripped) {
        return compileStructure("class ", stripped, Strings.from("?"));
    }

    private static Result<String_, CompileError> compileStructure(String infix, String_ input, String_ type) {
        var classIndex = input.indexOfSlice(infix);
        if (classIndex >= 0) {
            var beforeInfix = input.sliceTo(classIndex).strip();
            if (beforeInfix.startsWithSlice("@External")) {
                return new Ok<>(Strings.empty());
            }

            var afterClass = input.sliceFrom(classIndex + infix.length());
            var contentStart = afterClass.indexOfSlice("{");
            if (contentStart >= 0) {
                var beforeContent = afterClass.sliceTo(contentStart).strip();

                var permitsIndex = beforeContent.indexOfSlice(" permits ");
                var withoutPermits = permitsIndex >= 0
                        ? beforeContent.sliceTo(permitsIndex).strip()
                        : beforeContent;

                var implementsIndex = withoutPermits.indexOfSlice(" implements ");
                var withoutImplements = implementsIndex >= 0
                        ? withoutPermits.sliceTo(implementsIndex)
                        : withoutPermits;

                var paramStart = withoutImplements.indexOfSlice("(");
                var withEnd = afterClass.sliceFrom(contentStart + "{".length()).strip();
                if (paramStart >= 0) {
                    String_ withoutParams = withoutImplements.sliceTo(paramStart).strip();
                    return compileStructureWithBeforeContent(withEnd, type, withoutParams);
                }
                else {
                    return compileStructureWithBeforeContent(withEnd, type, withoutImplements);
                }
            }
        }

        return new Err<>(new CompileError("Not a struct", new StringContext(input)));
    }

    private static Result<String_, CompileError> compileStructureWithBeforeContent(String_ input, String_ type, String_ beforeContent) {
        var stripped = input.strip();
        if (!stripped.endsWithSlice("}")) {
            return createSuffixErr(stripped, "}");
        }

        var content = stripped.sliceTo(stripped.length() - "}".length());

        var strippedBeforeContent = beforeContent.strip();
        if (strippedBeforeContent.endsWithSlice(">")) {
            var withoutEnd = strippedBeforeContent.sliceTo(strippedBeforeContent.length() - ">".length());
            var typeParamStart = withoutEnd.indexOfSlice("<");
            if (typeParamStart >= 0) {
                var name = withoutEnd.sliceTo(typeParamStart).strip();
                var substring = withoutEnd.sliceFrom(typeParamStart + "<".length());

                var typeParameters = divideAll(substring, (state, c) -> foldDelimiter(state, c, ','));
                return assembleStructure(typeParameters, name, content, type);
            }
        }

        return assembleStructure(listEmpty(), strippedBeforeContent, content, type);
    }

    private static State foldDelimiter(State state, char c, char delimiter) {
        if (c == delimiter) {
            return state.advance();
        }
        return state.append(c);
    }

    private static <T> Result<T, CompileError> createSuffixErr(String_ input, String suffix) {
        return new Err<>(new CompileError("Suffix '" + suffix + "' not present", new StringContext(input)));
    }

    private static Result<String_, CompileError> assembleStructure(List<String_> typeParams, String_ name, String_ content, String_ type) {
        if (typeParams.isEmpty()) {
            return generateStructureWithTypeParams(name, content, listEmpty(), type);
        }

        if (!isSymbol(name)) {
            return createSymbolErr(name);
        }

        expanding = expanding.put(name, typeArgs -> {
            return usingTypeParams(typeParams, typeArgs, () -> generateStructureWithTypeParams(name, content, typeArgs, type));
        });

        return new Ok<>(Strings.empty());
    }

    private static <T> T usingTypeParams(List<String_> typeParams, List<Type> typeArgs, Supplier<T> supplier) {
        typeParamStack = typeParamStack.addLast(new Tuple<>(typeParams, typeArgs));
        var generated = supplier.get();
        typeParamStack = typeParamStack.removeLast().map(Tuple::right).orElse(typeParamStack);
        return generated;
    }

    private static Result<String_, CompileError> generateStructureWithTypeParams(String_ name, String_ content, List<Type> typeArgs, String_ type) {
        return usingFrames(() -> {
            frames = frames.withRef(new StructRef(name));
            if (type.equalsToSlice("enum")) {
                frames = frames.define(new Definition(new Functional(listEmpty(), new Ref(Primitive.I8)), "name"));
            }

            return parseStatements(content, Main::compileClassSegment)
                    .flatMapValue(parsed -> generateStructure(name, typeArgs, parsed));
        });
    }

    private static Result<String_, CompileError> generateStructure(
            String_ name,
            List<Type> typeArgs,
            List<String_> definitions
    ) {
        var compiled = generateAll(Main::mergeStatements, definitions);
        var alias = createAlias(name, typeArgs);
        var generated = Strings.from("struct " + alias + " {" + compiled + "\n};\n");

        if (frames.findCurrentStructType() instanceof Some(var currentStructType)) {
            structRegistry = structRegistry.put(new Generic(name, typeArgs), new Tuple<>(currentStructType, generated));
            return new Ok<>(Strings.empty());
        }
        else {
            return new Err<>(new CompileError("We aren't in a struct?", new StringContext(name)));
        }
    }

    private static Result<String_, CompileError> compileClassSegment(String_ input0) {
        return orString(input0, Lists.listFrom(
                whitespace(),
                type("class", Main::compileClass),
                type("enum", stripped -> compileStructure("enum ", stripped, Strings.from("enum"))),
                type("record", stripped -> compileStructure("record ", stripped, Strings.from("?"))),
                type("interface", stripped -> compileStructure("interface ", stripped, Strings.from("?"))),
                type("method", Main::compileMethod),
                type("definition-statement", Main::compileDefinitionStatement)
        ));
    }

    private static Result<String_, CompileError> compileDefinitionStatement(String_ input) {
        var stripped = input.strip();
        if (stripped.endsWithSlice(";")) {
            var withoutEnd = stripped.sliceTo(stripped.length() - ";".length());
            return new Ok<>(Strings.from("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";"));
        }

        return new Err<>(new CompileError("Not a definition statement", new StringContext(input)));
    }

    private static Result<String_, CompileError> compileMethod(String_ input) {
        var paramStart = input.indexOfSlice("(");
        if (paramStart < 0) {
            return createInfixErr(input, "(");
        }

        var inputDefinition = input.sliceTo(paramStart);
        var withParams = input.sliceFrom(paramStart + "(".length());
        return parseMethodHeader(inputDefinition).flatMapValue(defined -> compileMethodWithDefinition(defined, withParams));
    }

    private static Result<String_, CompileError> compileMethodWithDefinition(Definition definition, String_ withParams) {
        if (!definition.typeParams.isEmpty()) {
            return new Ok<>(Strings.empty());
        }

        functionName = definition.name;
        functionLocalCounter = 0;

        var paramEnd = withParams.indexOfSlice(")");
        if (paramEnd < 0) {
            return createInfixErr(withParams, ")");
        }

        var params = withParams.sliceTo(paramEnd);
        var withoutParams = withParams.sliceFrom(paramEnd + ")".length());
        var withBraces = withoutParams.strip();

        return parseValues(params, Main::parseParameter).flatMapValue(rawParameters -> {
            var oldParameters = rawParameters.iterate()
                    .map(Main::requireDefinition)
                    .flatMap(Iterators::fromOption)
                    .collect(new ListCollector<>());

            if (!definition.annotations.contains("External", String::equals) && (withBraces.startsWithSlice("{") && withBraces.endsWithSlice("}"))) {
                var content = withBraces.sliceBetween(1, withBraces.length() - 1);
                return compileMethodWithParameters(definition, content, oldParameters);
            }
            else {
                frames = defineMethod(definition, oldParameters);
                return new Ok<>(Strings.empty());
            }
        });
    }

    private static Result<String_, CompileError> compileMethodWithParameters(Definition definition, String_ content, List<Definition> oldParameters) {
        var thisType = frames.findCurrentRef().orElse(new StructRef("this"));

        var newParams = Lists.<Definition>listEmpty()
                .addLast(new Definition(thisType, "this"))
                .addAll(oldParameters);

        return usingFrames(() -> {
            frames = frames.defineAll(oldParameters);
            return assembleMethod0(definition, newParams, content);
        }).mapValue(output -> {
            frames = defineMethod(definition, oldParameters);
            return output;
        });
    }

    private static <T> Result<T, CompileError> usingFrames(Supplier<Result<T, CompileError>> supplier) {
        frames = frames.enter();
        var assembled = supplier.get();
        frames = frames.exit();
        return assembled;
    }

    private static Frames defineMethod(Definition definition, List<Definition> oldParameters) {
        var paramTypes = oldParameters.iterate()
                .map(Definition::type)
                .collect(new ListCollector<>());
        var type = definition.type;
        var name = definition.name;
        return frames.define(new Definition(new Functional(paramTypes, type), name));
    }

    private static Result<String_, CompileError> assembleMethod0(Definition definition, List<Definition> params, String_ content) {
        return assembleMethod(definition, generateValueList(params), content);
    }

    private static Result<Definition, CompileError> parseMethodHeader(String_ inputDefinition) {
        return orString(inputDefinition, Lists.listFrom(
                type("definition", Main::parseDefinition),
                type("constructor", Main::parseConstructor)
        ));
    }

    private static Option<Definition> requireDefinition(Parameter parameter) {
        if (parameter instanceof Definition definition) {
            return new Some<>(definition);
        }
        else {
            return new None<Definition>();
        }
    }

    private static Result<Definition, CompileError> parseConstructor(String_ constructor) {
        var stripped = constructor.strip();
        if (isSymbol(stripped)) {
            return new Ok<>(new Definition(Primitive.Auto, stripped));
        }
        else {
            return new Err<>(new CompileError("Not a constructor", new StringContext(stripped)));
        }
    }

    private static <T> Result<T, CompileError> createInfixErr(String_ input, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", new StringContext(input)));
    }

    private static <T extends Node> String_ generateValueList(List<T> copy) {
        return generateValueList(copy, Node::generate);
    }

    private static <T extends Node> String_ generateValueList(List<T> copy, Function<T, String_> generate) {
        return copy.iterate()
                .map(generate)
                .collect(new Joiner(Strings.from(", ")))
                .orElse(Strings.empty());
    }

    private static Result<String_, CompileError> assembleMethod(Parameter definition, String_ outputParams, String_ content) {
        return parseStatementsWithLocals(content, input -> compileFunctionSegment(input, 1)).mapValue(parsed -> {
            var generated = Strings.from(definition.generate() + "(" + outputParams + "){" + generateAll(Main::mergeStatements, parsed) + "\n}\n");
            generatedMethods = generatedMethods.addLast(generated);
            return Strings.empty();
        });
    }

    private static Result<List<String_>, CompileError> parseStatementsWithLocals(String_ content, Function<String_, Result<String_, CompileError>> compiler) {
        generatedStatements = generatedStatements.addLast(listEmpty());
        var result = parseStatements(content, compiler).flatMapValue(parsed1 -> {
            var maybeElements = generatedStatements.findLast();
            if (maybeElements instanceof Some(var elements)) {
                return new Ok<>(Lists.<String_>listEmpty()
                        .addAll(elements)
                        .addAll(parsed1));
            }
            else {
                return new Err<>(new CompileError("No statement frames found", new StringContext(content)));
            }
        });

        generatedStatements = generatedStatements.removeLast().map(Tuple::right).orElse(generatedStatements);
        return result;
    }

    private static Result<Parameter, CompileError> parseParameter(String_ input) {
        var lists = Lists.<Function<String_, Result<Parameter, CompileError>>>listFrom(
                type("?", Main::parseWhitespace),
                type("?", Main::parseDefinition)
        );

        return orString(input, lists);
    }

    private static <R> Result<R, CompileError> orString(String_ input, List<Function<String_, Result<R, CompileError>>> lists) {
        return or(input, new StringContext(input), lists);
    }

    private static <T, R> Result<R, CompileError> or(T input, Context context, List<Function<T, Result<R, CompileError>>> lists) {
        return lists.iterate()
                .fold(new OrState<R>(), (tOrState, mapper) -> foldOr(input, tOrState, mapper))
                .toResult()
                .mapErr(errs -> new CompileError("No valid combinations present", context, errs));
    }

    private static <T, R> OrState<R> foldOr(T input, OrState<R> state, Function<T, Result<R, CompileError>> mapper) {
        if (state.maybeValue.isPresent()) {
            return state;
        }
        return mapper.apply(input).match(state::withValue, state::withError);
    }

    private static <B, T extends B> Function<String_, Result<B, CompileError>> type(String type, Function<String_, Result<T, CompileError>> parser) {
        return input0 -> parser.apply(input0)
                .<B>mapValue(value -> value)
                .mapErr(err -> {
                    var message = Strings.from("Unknown type '")
                            .appendSlice(type)
                            .appendSlice("'");

                    var context = new StringContext(input0);
                    return new CompileError(message.toSlice(), context, Lists.listFrom(err));
                });
    }

    private static Result<Whitespace, CompileError> parseWhitespace(String_ input) {
        if (input.isBlank()) {
            return new Ok<>(new Whitespace());
        }
        else {
            return new Err<>(new CompileError("Not blank", new StringContext(input)));
        }
    }

    private static Result<String_, CompileError> compileFunctionSegment(String_ input, int depth) {
        return orString(input, Lists.listFrom(
                whitespace(),
                type("block", input0 -> compileBlock(input0, depth)),
                type("statement", input0 -> compileStatement(input0, depth)),
                type("return ", Main::compileReturn)
        ));
    }

    private static Function<String_, Result<String_, CompileError>> whitespace() {
        return type("whitespace", input0 -> parseWhitespace(input0).mapValue(Whitespace::generate));
    }

    private static Result<String_, CompileError> compileStatement(String_ input, int depth) {
        var stripped = input.strip();
        if (!stripped.endsWithSlice(";")) {
            return createSuffixErr(stripped, ";");
        }

        var withoutEnd = stripped.sliceTo(stripped.length() - ";".length()).strip();
        return compileStatementValue(withoutEnd).mapValue(statementValue -> Strings.from("\n" + "\t".repeat(depth) + statementValue + ";"));
    }

    private static Result<String_, CompileError> compileBlock(String_ input, int depth) {
        String_ indent = Strings.from("\n").appendOwned(Strings.from("\t").repeat(depth));
        var stripped = input.strip();
        if (stripped.endsWithSlice("}")) {
            var withoutEnd = stripped.sliceTo(stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOfSlice("{");
            if (contentStart >= 0) {
                var beforeBlock = withoutEnd.sliceTo(contentStart);
                var content = withoutEnd.sliceFrom(contentStart + "{".length());
                return compileBeforeBlock(beforeBlock).flatMapValue(result -> parseStatementsWithLocals(content, input1 -> compileFunctionSegment(input1, depth + 1))
                        .mapValue(outputContent -> indent.appendOwned(result)
                                .appendSlice("{")
                                .appendOwned(join(outputContent))
                                .appendOwned(indent)
                                .appendSlice("}")));
            }
        }

        return new Err<>(new CompileError("Not a block", new StringContext(indent)));
    }

    private static Result<String_, CompileError> compileStatementValue(String_ input) {
        return orString(input, Lists.listFrom(
                whitespace(),
                type("break", Main::compileBreak),
                type("return ", Main::compileReturn),
                type("assignment", Main::compileAssignment),
                type("invokable", input0 -> parseInvokable(input0).mapValue(Invocation::generate))
        ));
    }

    private static Result<String_, CompileError> compileAssignment(String_ input) {
        var stripped = input.strip();
        var valueSeparator = stripped.indexOfSlice("=");
        if (valueSeparator < 0) {
            return createInfixErr(stripped, "=");
        }
        var assignableString_ = stripped.sliceTo(valueSeparator);
        var valueString_ = stripped.sliceFrom(valueSeparator + "=".length());

        return parseAssignable(assignableString_).and(() -> parseValue(valueString_)).flatMapValue(tuple -> {
            var assignable = tuple.left;
            var value = tuple.right;

            return resolve(value).flatMapValue(type -> {
                Assignable newAssignable;
                if (assignable instanceof Definition definition) {
                    newAssignable = new Definition(type, definition.name);
                }
                else {
                    newAssignable = assignable;
                }

                return new Ok<>(newAssignable.generate()
                        .appendSlice(" = ")
                        .appendOwned(value.generate()));
            });
        });
    }

    private static Result<String_, CompileError> compileReturn(String_ input) {
        var stripped = input.strip();
        if (stripped.startsWithSlice("return ")) {
            var slice = stripped.sliceFrom("return ".length());
            return compileValue(slice).mapValue(value -> Strings.from("return ").appendOwned(value));
        }

        return createPrefixErr(stripped, "return ");
    }

    private static <T> Result<T, CompileError> createPrefixErr(String_ input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", new StringContext(input)));
    }

    private static Result<String_, CompileError> compileBreak(String_ s) {
        var stripped = s.strip();
        if (stripped.equalsToSlice("break")) {
            return new Ok<>(Strings.from("break"));
        }
        else {
            return new Err<>(new CompileError("Not break", new StringContext(stripped)));
        }
    }

    private static Result<Type, CompileError> resolve(Value value) {
        return switch (value) {
            case BooleanValue _ -> new Ok<>(Primitive.Bool);
            case CharValue _ -> new Ok<>(Primitive.I8);
            case Invocation invocation -> resolveInvocation(invocation);
            case Not _ -> new Ok<>(Primitive.Bool);
            case Operation operation -> resolveOperation(operation);
            case String_Value _ -> new Ok<>(new Ref(Primitive.I8));
            case Symbol symbol -> resolveSymbol(symbol);
            case Whitespace _ -> new Ok<>(Primitive.Void);
            case DataAccess dataAccess -> resolveDataAccess(dataAccess);
        };
    }

    private static Result<Type, CompileError> resolveDataAccess(DataAccess access) {
        return resolve(access.parent)
                .flatMapValue(Main::resolveStructuredType)
                .flatMapValue(structType -> structType.findTypeAsResult(access.property.toSlice()))
                .mapErr(err -> new CompileError("Failed to resolve data access", new NodeContext(access.parent), Lists.listFrom(err)));
    }

    private static Result<StructType, CompileError> resolveStructuredType(Type type) {
        return Main.or(type, new NodeContext(type), Lists.listFrom(
                Main::resolveGenericType,
                Main::resolveStructRef,
                Main::resolveStructureType
        ));
    }

    private static Result<StructType, CompileError> resolveStructureType(Type type) {
        if (type instanceof StructType structType) {
            return new Ok<>(structType);
        }
        return new Err<>(new CompileError("Not a structure", new NodeContext(type)));
    }

    private static Result<StructType, CompileError> resolveStructRef(Type type1) {
        if (type1 instanceof StructRef ref) {
            return resolveStructByName(ref.name, listEmpty());
        }
        else {
            return new Err<>(new CompileError("Not a struct ref", new NodeContext(type1)));
        }
    }

    private static Result<StructType, CompileError> resolveGenericType(Type type) {
        if (type instanceof Generic generic) {
            return resolveStructByName(generic.base, generic.args);
        }
        else {
            return new Err<>(new CompileError("Not a generic type", new NodeContext(type)));
        }
    }

    private static Result<Type, CompileError> resolveOperation(Operation operation) {
        return operation.operator.type
                .<Result<Type, CompileError>>map(Ok::new)
                .orElseGet(() -> resolve(operation.left));
    }

    private static Result<Type, CompileError> resolveSymbol(Symbol symbol) {
        var value = symbol.value;
        if (value.equalsToSlice("this")) {
            return switch (frames.findCurrentStructType()) {
                case None<StructType> _ ->
                        new Err<>(new CompileError("No current struct type present", new StringContext(Strings.empty())));
                case Some<StructType>(var type) -> new Ok<>(type);
            };
        }

        return frames.findTypeByName(value.toSlice())
                .<Result<Type, CompileError>>map(Ok::new)
                .orElseGet(() -> createUndefinedError(value));
    }

    private static Result<Type, CompileError> createUndefinedError(String_ value) {
        var joinedNames = frames.streamDefined()
                .map(Definition::name)
                .collect(new Joiner(Strings.from(", ")))
                .orElse(Strings.empty());

        return new Err<>(new CompileError("Symbol not defined [" + joinedNames.toSlice() + "]", new StringContext(value)));
    }

    private static Option<Type> findSymbolInDefinitions(List<Definition> frame, String value) {
        return frame.iterate()
                .filter(definition -> definition.name.equalsToSlice(value))
                .map(definition -> definition.type)
                .next();
    }

    private static Result<Type, CompileError> resolveInvocation(Invocation invocation) {
        var caller = invocation.caller;
        return resolve(caller).flatMapValue(resolvedCaller -> {
            if (resolvedCaller instanceof Functional functional) {

                return new Ok<>(functional.returns);
            }

            return new Err<>(new CompileError("Not a functional type", new NodeContext(resolvedCaller)));
        });
    }

    private static Result<Assignable, CompileError> parseAssignable(String_ definition) {
        return orString(definition, Lists.listFrom(type("?", Main::parseDefinition), type("?", Main::parseValue)));
    }

    private static Result<String_, CompileError> compileBeforeBlock(String_ input) {
        return orString(input, Lists.listFrom(
                type("else", string -> parseElse(input, string)),
                type("if", string -> parseConditional(string, "if")),
                type("if", string -> parseConditional(string, "while"))
        ));
    }

    private static Result<String_, CompileError> parseElse(String_ input, String_ string) {
        if (input.strip().equalsToSlice("else")) {
            return new Ok<>(Strings.from("else "));
        }
        else {
            return new Err<>(new CompileError("Not an else", new StringContext(string)));
        }
    }

    private static Result<String_, CompileError> parseConditional(String_ stripped, String prefix) {
        if (stripped.startsWithSlice(prefix)) {
            var withoutPrefix = stripped.sliceFrom(prefix.length()).strip();
            if (withoutPrefix.startsWithSlice("(") && withoutPrefix.endsWithSlice(")")) {
                var condition = withoutPrefix.sliceBetween(1, withoutPrefix.length() - 1);
                return compileValue(condition)
                        .mapValue(result -> Strings.from(prefix)
                                .appendSlice(" (")
                                .appendOwned(result)
                                .appendSlice(")"));
            }
        }
        return new Err<>(new CompileError("Not a conditional", new StringContext(stripped)));
    }

    private static Result<String_, CompileError> compileValue(String_ input) {
        return parseValue(input).mapValue(Node::generate);
    }

    private static Result<Value, CompileError> parseValue(String_ input) {
        List<Function<String_, Result<Value, CompileError>>> rules = Lists.listFrom(
                type("whitespace", Main::parseWhitespace),
                type("boolean", Main::parseBoolean),
                type("switch", Main::parseSwitch),
                type("lambda", Main::parseLambda),
                type("invokable", Main::parseInvokable),
                type("symbol", Main::parseSymbol),
                type("number", Main::parseNumber),
                type("data-access", Main::parseDataAccess),
                type("string", Main::parseString_),
                type("char", Main::parseChar),
                type("not", Main::parseNot)
        );

        var operatorRules = Iterators.fromArray(Operator.values())
                .map(operator -> type(operator.name(), parseOperator(operator)))
                .collect(new ListCollector<>());

        return orString(input, rules.addAll(operatorRules));
    }

    private static Result<Value, CompileError> parseSwitch(String_ input) {
        var stripped = input.strip();
        if (stripped.startsWithSlice("switch")) {
            var name = generateName();
            return new Ok<>(new Symbol(name));
        }

        return createPrefixErr(stripped, "switch");
    }

    private static Function<String_, Result<Value, CompileError>> parseOperator(Operator operator) {
        return input -> {
            var representation = operator.representation;
            var stripped = input.strip();
            var operatorIndex = stripped.indexOfOwned(representation);
            if (operatorIndex < 0) {
                return createInfixErr(stripped, representation.toSlice());
            }

            var leftString_ = stripped.sliceTo(operatorIndex);
            var rightString_ = stripped.sliceFrom(operatorIndex + representation.length());
            return parseValue(leftString_)
                    .and(() -> parseValue(rightString_))
                    .mapValue(tuple -> new Operation(tuple.left, operator, tuple.right));
        };
    }

    private static Result<Value, CompileError> parseNot(String_ input) {
        var stripped = input.strip();
        if (stripped.startsWithSlice("!")) {
            return parseValue(input.sliceFrom(1)).mapValue(Not::new);
        }
        else {
            return createPrefixErr(stripped, "!");
        }
    }

    private static Result<Value, CompileError> parseChar(String_ input) {
        var stripped = input.strip();
        if (stripped.length() >= 2 && stripped.startsWithSlice("'") && stripped.endsWithSlice("'")) {
            return new Ok<>(new CharValue(stripped.sliceBetween(1, stripped.length() - 1)));
        }
        else {
            return new Err<>(new CompileError("Not a char", new StringContext(stripped)));
        }
    }

    private static Result<Value, CompileError> parseString_(String_ input) {
        var stripped = input.strip();
        if (stripped.length() >= 2 && stripped.startsWithSlice("\"") && stripped.endsWithSlice("\"")) {
            return new Ok<>(new String_Value(stripped.sliceBetween(1, stripped.length() - 1)));
        }
        else {
            return new Err<>(new CompileError("Not a string", new StringContext(input)));
        }
    }

    private static Result<DataAccess, CompileError> parseDataAccess(String_ input) {
        var stripped = input.strip();
        var separator = (int) stripped.lastIndexOfSlice(".").orElse(-1);
        if (separator < 0) {
            return createInfixErr(stripped, ".");
        }

        var parent = stripped.sliceTo(separator);
        var property = stripped.sliceFrom(separator + ".".length()).strip();
        if (!isSymbol(property)) {
            return createSymbolErr(property);
        }

        return parseValue(parent)
                .mapValue(parsed -> new DataAccess(parsed, property))
                .mapErr(err -> new CompileError("Invalid parent", new StringContext(input), Lists.listFrom(err)));
    }

    private static <T> Result<T, CompileError> createSymbolErr(String_ property) {
        return new Err<>(new CompileError("Not a symbol", new StringContext(property)));
    }

    private static Result<Value, CompileError> parseNumber(String_ input) {
        var stripped = input.strip();
        if (isNumber(stripped)) {
            return new Ok<>(new Symbol(stripped));
        }
        else {
            return new Err<>(new CompileError("Not a number", new StringContext(stripped)));
        }
    }

    private static Result<Value, CompileError> parseSymbol(String_ input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return new Ok<>(new Symbol(stripped));
        }
        return createSymbolErr(stripped);
    }

    private static Result<Value, CompileError> parseLambda(String_ input) {
        var stripped = input.strip();
        var arrowIndex = stripped.indexOfSlice("->");
        if (arrowIndex < 0) {
            return createInfixErr(stripped, "->");
        }

        var beforeArrow = stripped.sliceTo(arrowIndex).strip();
        var afterArrow = stripped.sliceFrom(arrowIndex + "->".length()).strip();
        if (isSymbol(beforeArrow)) {
            return assembleLambda(Lists.listFrom(beforeArrow), afterArrow);
        }

        if (!beforeArrow.startsWithSlice("(") || !beforeArrow.endsWithSlice(")")) {
            return new Err<>(new CompileError("No parentheses present", new StringContext(beforeArrow)));
        }

        var substring = beforeArrow.sliceBetween(1, beforeArrow.length() - 1);
        var stringList = divideAll(substring, (state, c) -> foldDelimiter(state, c, ','));

        var args = stringList.iterate()
                .map(String_::strip)
                .filter(value -> !value.isEmpty())
                .collect(new ListCollector<>());

        return assembleLambda(args, afterArrow);
    }

    private static Result<BooleanValue, CompileError> parseBoolean(String_ input) {
        var stripped = input.strip();
        if (stripped.equalsToSlice("false")) {
            return new Ok<>(BooleanValue.False);
        }
        if (stripped.equalsToSlice("true")) {
            return new Ok<>(BooleanValue.True);
        }
        return new Err<>(new CompileError("Not a boolean", new StringContext(input)));
    }

    private static Result<Invocation, CompileError> parseInvokable(String_ input) {
        var stripped = input.strip();
        if (!stripped.endsWithSlice(")")) {
            return createSuffixErr(stripped, ")");
        }

        var withoutEnd = stripped.sliceTo(stripped.length() - ")".length()).strip();
        var divisions = divideAll(withoutEnd, Main::foldInvokableStart);
        if (divisions.size() < 2) {
            return new Err<>(new CompileError("Insufficient divisions", new StringContext(withoutEnd)));
        }

        var joined = join(divisions.subList(0, divisions.size() - 1));
        var callerString_ = joined.sliceTo(joined.length() - ")".length());
        var arguments = divisions.findLast().orElse(null);

        if (callerString_.startsWithSlice("new ")) {
            String_ withoutPrefix = callerString_.sliceFrom("new ".length());
            return parseType(withoutPrefix).flatMapValue(type -> {
                var parsedCaller = new Symbol(Strings.from("new_").appendOwned(type.stringify()));
                return assembleInvokable(parsedCaller, arguments, listEmpty());
            });
        }

        return parseValue(callerString_)
                .flatMapValue(caller -> resolve(caller).flatMapValue(callerType -> parseInvokableWithCallerType(caller, callerType, arguments)))
                .mapErr(err -> new CompileError("Invalid caller", new StringContext(callerString_), Lists.listFrom(err)));
    }

    private static Result<Invocation, CompileError> parseInvokableWithCallerType(Value caller, Type callerType, String_ argumentsString) {
        if (callerType instanceof Functional functional) {
            return assembleInvokable(caller, argumentsString, functional.paramTypes);
        }

        if (!(callerType instanceof StructRef structRef)) {
            return new Err<>(new CompileError("Not an invokable type", new NodeContext(callerType)));
        }

        var baseName = structRef.name;
        return resolveStructByName(baseName, listEmpty()).flatMapValue(structType -> {
            var maybeConstructor = structType.findTypeAsOption("new");
            if (!(maybeConstructor instanceof Some(var constructor))) {
                return new Err<>(new CompileError("No constructor was found for type", new NodeContext(structType)));
            }

            if (constructor instanceof Functional functional) {
                return assembleInvokable(caller, argumentsString, functional.paramTypes);
            }
            else {
                return new Err<>(new CompileError("Constructor does not appear to a functional type", new NodeContext(constructor)));
            }
        });
    }

    private static Result<StructType, CompileError> resolveStructByName(String_ baseName, List<Type> args) {
        return orString(baseName, Lists.listFrom(
                Main::resolveCurrentStruct,
                baseName1 -> resolveDefinedStruct(new Generic(baseName1, args))
        ));
    }

    private static Result<StructType, CompileError> resolveDefinedStruct(Generic generic) {
        if (structRegistry.containsKey(generic)) {
            return new Ok<>(structRegistry.get(generic).left);
        }

        var joinedKeys = structRegistry.keys()
                .map(Generic::generate)
                .collect(new Joiner(Strings.from(", ")))
                .orElse(Strings.empty());

        return new Err<>(new CompileError("No struct exists within [" + joinedKeys + "]", new NodeContext(generic)));
    }

    private static Result<StructType, CompileError> resolveCurrentStruct(String_ baseName) {
        if (!(frames.findCurrentStructType() instanceof Some(var currentStructType))) {
            return new Err<>(new CompileError("Not in a structure", new StringContext(baseName)));
        }

        if (baseName.equalsTo(currentStructType.name)) {
            return new Ok<>(currentStructType);
        }

        return new Err<>(new CompileError("Current struct did not match '" + currentStructType.generate() + "'", new StringContext(baseName)));
    }

    private static Result<Invocation, CompileError> assembleInvokable(Value caller, String_ arguments, List<Type> expectedArgumentsTypes) {
        var divided = divideAll(arguments, Main::foldValueChar)
                .iterate()
                .map(String_::strip)
                .filter(value -> !value.isEmpty())
                .collect(new ListCollector<>());

        if (divided.size() == expectedArgumentsTypes.size()) {
            return divided.iterateWithIndices()
                    .map((Tuple<Integer, String_> input) -> assembleInvocationArgument(expectedArgumentsTypes, input))
                    .collect(new Exceptional<>(new ListCollector<>()))
                    .flatMapValue(collect -> assumeInvocation(caller, collect));
        }
        else {
            return new Err<>(new CompileError("Found '" + divided.size() + "' arguments, but '" + expectedArgumentsTypes.size() + "' were supplied", new NodeContext(caller)));
        }
    }

    private static Result<Invocation, CompileError> assumeInvocation(Value caller, List<Value> rawArguments) {
        var parsedArgs = rawArguments.iterate()
                .filter(value -> !(value instanceof Whitespace))
                .collect(new ListCollector<>());

        if (!(caller instanceof DataAccess(var parent, var property))) {
            return new Ok<>(new Invocation(caller, parsedArgs));
        }

        var name = generateName();

        if (parent instanceof Symbol || parent instanceof DataAccess) {
            return assembleInvocation(property, parent, parsedArgs);
        }

        return resolve(parent).flatMapValue(type -> {
            var statement = Strings.from("\n\t" + type.generate() + " " + name + " = " + parent.generate() + ";");
            generatedStatements = generatedStatements.mapLast(last -> last.addLast(statement));

            Value symbol = new Symbol(name);
            return assembleInvocation(property, symbol, parsedArgs);
        });
    }

    private static Result<Invocation, CompileError> assembleInvocation(String_ property, Value symbol, List<Value> parsedArgs) {
        var newArgs = Lists.<Value>listEmpty()
                .addLast(symbol)
                .addAll(parsedArgs);

        return new Ok<>(new Invocation(new DataAccess(symbol, property), newArgs));
    }

    private static Result<Value, CompileError> assembleInvocationArgument(List<Type> expectedArgumentsType, Tuple<Integer, String_> input) {
        var index = input.left;

        var argumentValue = input.right;
        return expectedArgumentsType.get(index)
                .map(argumentType -> usingTypeStack(argumentType, () -> parseValue(argumentValue)))
                .orElseGet(() -> new Err<>(new CompileError("Could not find expected argument", new StringContext(argumentValue))));
    }

    private static Result<Value, CompileError> usingTypeStack(Type found, Supplier<Result<Value, CompileError>> supplier) {
        typeStack = typeStack.addLast(found);
        Result<Value, CompileError> parsed = supplier.get();
        typeStack = typeStack.removeLast().map(Tuple::right).orElse(typeStack);
        return parsed;
    }

    private static Result<Value, CompileError> assembleLambda(List<String_> paramNames, String_ afterArrow) {
        return typeStack.findLast().<Result<Value, CompileError>>map(maybeExpectedType -> {
            if (!(maybeExpectedType instanceof Functional expectedType)) {
                return new Err<>(new CompileError("A lambda has to be a functional type", new NodeContext(maybeExpectedType)));
            }

            if (expectedType.paramTypes.size() != paramNames.size()) {
                return new Err<>(new CompileError("Insufficient parameters", new StringContext(afterArrow)));
            }

            var params = expectedType.paramTypes.iterate()
                    .zip(paramNames.iterate())
                    .map(tuple -> new Definition(tuple.left, tuple.right))
                    .collect(new ListCollector<>());

            return usingFrames(() -> {
                frames = frames.defineAll(params);

                if (afterArrow.startsWithSlice("{") && afterArrow.endsWithSlice("}")) {
                    var content = afterArrow.sliceBetween(1, afterArrow.length() - 1);
                    var name = generateName();
                    assembleMethod0(new Definition(expectedType.returns, name), params, content);
                    return new Ok<>(new Symbol(name));
                }

                return parseValue(afterArrow).flatMapValue(value -> {
                    var newValue = value.generate();
                    return resolve(value).mapValue(resolved -> {
                        var name = generateName();
                        assembleMethod0(new Definition(resolved, name), params, Strings.from("\n\treturn " + newValue + ";"));
                        return new Symbol(name);
                    });
                });
            });
        }).orElseGet(() -> new Err<>(new CompileError("No expected type was present for lambda", new StringContext(afterArrow))));
    }

    private static String_ generateName() {
        var name = functionName.appendSlice("_local").appendSlice(String.valueOf(functionLocalCounter));
        functionLocalCounter++;
        return name;
    }

    private static State foldInvokableStart(State state, Character c) {
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

    private static boolean isNumber(String_ input) {
        if (input.isEmpty()) {
            return false;
        }

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Result<String_, CompileError> compileDefinitionOrPlaceholder(String_ input) {
        return parseDefinition(input).mapValue(Definition::generate);
    }

    private static Result<Definition, CompileError> parseDefinition(String_ input) {
        var stripped = input.strip();
        return stripped.lastIndexOfSlice(" ").<Result<Definition, CompileError>>map(nameSeparator -> {
            var beforeName = stripped.sliceTo(nameSeparator);
            var name = stripped.sliceFrom(nameSeparator + " ".length());
            if (!isSymbol(name)) {
                return new Err<>(new CompileError("Not a symbol", new StringContext(name)));
            }

            var divisions = divideAll(beforeName, Main::foldByTypeSeparator);
            if (divisions.size() == 1) {
                return parseType(beforeName).mapValue(type -> new Definition(type, name));
            }

            return divisions.popLast().map(tuple -> {
                var joined = tuple.left.iterate()
                        .collect(new Joiner(" "))
                        .orElse(Strings.empty())
                        .strip();

                return parseDefinitionWithType(joined, tuple.right, name);
            }).orElseGet(() -> new Err<>(new CompileError("Insufficient divisions", new StringContext(beforeName))));
        }).orElseGet(() -> createInfixErr(stripped, " "));
    }

    private static Result<Definition, CompileError> parseDefinitionWithType(String_ beforeType, String_ type, String_ name) {
        if (beforeType.endsWithSlice(">")) {
            var withoutEnd = beforeType.sliceTo(beforeType.length() - ">".length());
            if (withoutEnd.lastIndexOfSlice("<") instanceof Some(var typeParamStart)) {
                var beforeTypeParams = withoutEnd.sliceTo(typeParamStart);
                var typeParamsString = withoutEnd.sliceFrom(typeParamStart + 1);
                var typeParams = divideAll(typeParamsString, Main::foldValueChar);
                return parseDefinitionWithTypeParams(beforeTypeParams, typeParams, type, name);
            }
        }

        return parseDefinitionWithTypeParams(beforeType, listEmpty(), type, name);
    }

    private static Result<Definition, CompileError> parseDefinitionWithTypeParams(String_ withoutTypeParams, List<String_> typeParams, String_ type, String_ name) {
        return usingTypeParams(typeParams, listEmpty(), () -> {
            return parseType(type).mapValue(type1 -> {
                return withoutTypeParams.lastIndexOfSlice("\n").map(annotationsSeparator -> {
                    var string = withoutTypeParams.sliceTo(annotationsSeparator);
                    var annotations = divideAll(string, (state, c) -> foldDelimiter(state, c, '\n'))
                            .iterate()
                            .map(slice -> slice.sliceFrom(1))
                            .map(String_::strip)
                            .map(String_::toSlice)
                            .collect(new ListCollector<>());

                    return new Definition(annotations, typeParams, type1, name);
                }).orElseGet(() -> {
                    return new Definition(listEmpty(), typeParams, type1, name);
                });
            });
        });
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

    private static Result<Type, CompileError> parseType(String_ input) {
        var stripped = input.strip();
        if (findTypeParamEntryInStack(stripped) instanceof Some(var typeParamEntry)) {
            var typeParam = typeParamEntry.left;
            var maybeTypeArgument = typeParamEntry.right;
            if (maybeTypeArgument instanceof Some(var found)) {
                return new Ok<>(found);
            }
            return new Ok<>(new TypeParam(typeParam));
        }

        switch (stripped.toSlice()) {
            case "int", "Integer", "boolean", "Boolean" -> {
                return new Ok<>(Primitive.I32);
            }
            case "char", "Character" -> {
                return new Ok<>(Primitive.I8);
            }
            case "void" -> {
                return new Ok<>(Primitive.Void);
            }
            case "var" -> {
                return new Ok<>(Primitive.Auto);
            }
        }

        if (stripped.equalsToSlice("String_")) {
            return new Ok<>(new Ref(Primitive.I8));
        }

        if (stripped.endsWithSlice(">")) {
            var withoutEnd = stripped.sliceTo(stripped.length() - ">".length());
            var index = withoutEnd.indexOfSlice("<");
            if (index >= 0) {
                var base = withoutEnd.sliceTo(index).strip();
                var substring = withoutEnd.sliceFrom(index + "<".length());
                return parseValues(substring, Main::parseType).flatMapValue(parsed -> {
                    if (base.equalsToSlice("Consumer")) {
                        var arg0 = parsed.get(0).orElse(null);
                        return new Ok<>(new Functional(Lists.listFrom(arg0), Primitive.Void));
                    }

                    if (base.equalsToSlice("Function")) {
                        var arg0 = parsed.get(0).orElse(null);
                        var returns = parsed.get(1).orElse(null);
                        return new Ok<>(new Functional(Lists.listFrom(arg0), returns));
                    }

                    if (base.equalsToSlice("BiFunction")) {
                        var arg0 = parsed.get(0).orElse(null);
                        var arg1 = parsed.get(1).orElse(null);
                        var returns = parsed.get(2).orElse(null);
                        return new Ok<>(new Functional(Lists.listFrom(arg0, arg1), returns));
                    }

                    var generic = new Generic(base, parsed);
                    if (!generic.hasTypeParams()) {
                        if (!visitedExpansions.contains(generic, Generic::equalsTo) && expanding.containsKey(base)) {
                            visitedExpansions = visitedExpansions.addLast(generic);
                            expanding.get(base).apply(parsed);
                        }
                    }

                    return new Ok<>(generic);
                });
            }
        }

        if (isSymbol(stripped)) {
            return new Ok<>(new StructRef(stripped));
        }

        return new Err<>(new CompileError("Not a valid type", new StringContext(input)));
    }

    private static Option<Tuple<String_, Option<Type>>> findTypeParamEntryInStack(String_ typeParamValue) {
        return typeParamStack.iterateReversed()
                .flatMap(element -> findTypeParamEntry(typeParamValue, element))
                .next();
    }

    private static Iterator<Tuple<String_, Option<Type>>> findTypeParamEntry(String_ typeParamValue, Tuple<List<String_>, List<Type>> element) {
        return element.left.iterateWithIndices()
                .filter(entry -> entry.right.equalsTo(typeParamValue))
                .map(entry -> new Tuple<>(entry.right, element.right.get(entry.left)));
    }

    private static String_ createAlias(String_ base, List<Type> parsed) {
        var other = parsed.iterate()
                .map(Type::stringify)
                .collect(new Joiner(Strings.from("_")))
                .orElse(Strings.empty());

        return base.appendSlice("_").appendOwned(other);
    }

    private static <T> Result<List<T>, CompileError> parseValues(String_ input, Function<String_, Result<T, CompileError>> compiler) {
        return parseAll(input, Main::foldValueChar, compiler);
    }

    private static State foldValueChar(State state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '-') {
            if (appended.peek() instanceof Some(var maybeArrow)) {
                if (maybeArrow == '>') {
                    return appended.popAndAppend().orElse(appended);
                }
            }
        }

        if (c == '<' || c == '(') {
            return appended.enter();
        }
        if (c == '>' || c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static boolean isSymbol(String_ input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return false;
        }

        for (var i = 0; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)) || c == '_') {
                continue;
            }
            return false;
        }
        return true;
    }

    private record ImmutableFrames(List<Frame> frames) implements Frames {
        public ImmutableFrames() {
            this(Lists.listEmpty());
        }

        @Override
        public Option<Type> findTypeByName(String name) {
            return this.streamReversedFrames()
                    .map(list -> list.findDefinitionInFrame(name))
                    .flatMap(Iterators::fromOption)
                    .next();
        }

        @Override
        public Option<StructType> findCurrentStructType() {
            return this.streamReversedFrames()
                    .map(Frame::toStructType)
                    .flatMap(Iterators::fromOption)
                    .next();
        }

        private Iterator<Frame> streamReversedFrames() {
            return this.frames.iterateReversed();
        }

        @Override
        public Iterator<Definition> streamDefined() {
            return this.frames.iterate()
                    .map(Frame::definitions)
                    .flatMap(List::iterate);
        }

        @Override
        public Option<StructRef> findCurrentRef() {
            return this.streamReversedFrames()
                    .map(frame -> frame.maybeRef)
                    .flatMap(Iterators::fromOption)
                    .next();
        }

        @Override
        public Frames defineAll(List<Definition> definitions) {
            return definitions.iterate().fold(this, Frames::define);
        }

        @Override
        public Frames define(Definition definition) {
            return new ImmutableFrames(this.frames.mapLast(last -> last.define(definition)));
        }

        @Override
        public Frames exit() {
            return new ImmutableFrames(this.frames.removeLast().map(Tuple::right).orElse(this.frames));
        }

        @Override
        public Frames enter() {
            return new ImmutableFrames(this.frames.addLast(new Frame()));
        }

        @Override
        public Frames withRef(StructRef ref) {
            return new ImmutableFrames(this.frames.mapLast(last -> last.withRef(ref)));
        }
    }

    private static class Strings {
        @External
        public static String_ from(String value) {
            return new JavaString(value);
        }

        public static String_ empty() {
            return from("");
        }

        @External
        private record JavaString(String value) implements String_ {
            @Override
            public String toString() {
                return this.value;
            }

            @Override
            public String_ appendSlice(String slice) {
                return new JavaString(this.value + slice);
            }

            @Override
            public String_ toLowerCase() {
                return new JavaString(this.value.strip());
            }

            @Override
            public int length() {
                return this.value.length();
            }

            @Override
            public char charAt(int index) {
                return this.value.charAt(index);
            }

            @Override
            public String_ appendChar(char c) {
                return new JavaString(this.value + c);
            }

            @Override
            public String_ appendOwned(String_ other) {
                return new JavaString(this.value + other.toSlice());
            }

            @Override
            public String toSlice() {
                return this.value;
            }

            @Override
            public String_ strip() {
                return new JavaString(this.value.strip());
            }

            @Override
            public String_ repeat(int depth) {
                return new JavaString(this.value.repeat(depth));
            }

            @Override
            public boolean isBlank() {
                return this.value.isBlank();
            }

            @Override
            public boolean startsWithSlice(String slice) {
                return this.value.startsWith(slice);
            }

            @Override
            public int indexOfSlice(String slice) {
                return this.value.indexOf(slice);
            }

            @Override
            public String_ sliceFrom(int startInclusive) {
                return new JavaString(this.value.substring(startInclusive));
            }

            @Override
            public String_ sliceBetween(int startInclusive, int endExclusive) {
                return new JavaString(this.value.substring(startInclusive, endExclusive));
            }

            @Override
            public boolean endsWithSlice(String slice) {
                return this.value.endsWith(slice);
            }

            @Override
            public boolean equalsToSlice(String slice) {
                return this.value.equals(slice);
            }

            @Override
            public int indexOfOwned(String_ other) {
                return this.value.indexOf(other.toSlice());
            }

            @Override
            public boolean isEmpty() {
                return this.value.isEmpty();
            }

            @Override
            public boolean equalsTo(String_ other) {
                return other.equalsToSlice(this.value);
            }

            @Override
            public Option<Integer> lastIndexOfSlice(String slice) {
                var index = this.value.lastIndexOf(slice);
                return index == -1
                        ? new None<>()
                        : new Some<>(index);
            }

            @Override
            public String_ sliceTo(int index) {
                return this.sliceBetween(0, index);
            }
        }
    }

    private enum Operator {
        Add("+", new None<>()),
        And("&&", new Some<>(Primitive.Bool)),
        Or("||", new Some<>(Primitive.Bool)),
        Equals("==", new Some<>(Primitive.Bool)),
        EqualsNot("!=", new Some<>(Primitive.Bool)),
        LessThanOrEquals("<=", new Some<>(Primitive.Bool)),
        LessThan("<", new Some<>(Primitive.Bool)),
        GreaterThanOrEquals(">=", new Some<>(Primitive.Bool)),
        GreaterThan(">", new Some<>(Primitive.Bool));

        private final String_ representation;
        private final Option<Type> type;

        Operator(String representation, Option<Type> type) {
            this.representation = Strings.from(representation);
            this.type = type;
        }
    }

    private enum BooleanValue implements Value {
        False("0"),
        True("1");

        private final String_ value;

        BooleanValue(String value) {
            this.value = Strings.from(value);
        }

        @Override
        public String_ generate() {
            return this.value;
        }
    }

    private enum Primitive implements Type {
        I32("int"),
        I8("char"),
        Void("void"),
        Auto("auto"),
        Bool("int");
        private final String_ value;

        Primitive(String value) {
            this.value = Strings.from(value);
        }

        @Override
        public String_ stringify() {
            return Strings.from(this.name()).toLowerCase();
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Primitive primitive && this == primitive;
        }

        @Override
        public String_ generate() {
            return this.value;
        }

        @Override
        public boolean hasTypeParams() {
            return false;
        }
    }

    public record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public <R> Option<R> flatMap_(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public Option<T> or(Supplier<Option<T>> supplier) {
            return this;
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }

        @Override
        public boolean isPresent() {
            return true;
        }
    }

    public record None<T>() implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public <R> Option<R> flatMap_(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> supplier) {
            return supplier.get();
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
        }

        @Override
        public boolean isPresent() {
            return false;
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T element;
        private boolean retrieved = false;

        public SingleHead(T element) {
            this.element = element;
        }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            }

            this.retrieved = true;
            return new Some<>(this.element);
        }
    }

    public record Iterator<T>(Head<T> head) {

        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                switch (optional) {
                    case None<R> _ -> {
                        return current;
                    }
                    case Some<R>(var nextState) -> current = nextState;
                }
            }
        }

        public Iterator<T> filter(Predicate<T> predicate) {
            return this.flatMap(element -> new Iterator<>(predicate.test(element) ? new SingleHead<>(element) : new EmptyHead<>()));
        }

        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).fold(new Iterator<>(new EmptyHead<>()), Iterator::concat);
        }

        private Iterator<T> concat(Iterator<T> other) {
            return new Iterator<>(() -> this.head.next().or(other::next));
        }

        public Option<T> next() {
            return this.head.next();
        }

        public <R> Iterator<Tuple<T, R>> zip(Iterator<R> iterator) {
            return this.map(value -> iterator.next().map(otherValue -> new Tuple<>(value, otherValue))).flatMap(Iterators::fromOption);
        }
    }

    public record Tuple<A, B>(A left, B right) {
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

    private record State(String_ input, List<String_> segments, String_ buffer, int depth, int index) {
        private static State fromInput(String_ input) {
            return new State(input, listEmpty(), Strings.empty(), 0, 0);
        }

        private Option<Tuple<Character, State>> pop() {
            if (this.index >= this.input.length()) {
                return new None<>();
            }

            var escaped = this.input.charAt(this.index);
            return new Some<>(new Tuple<Character, State>(escaped, new State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
        }

        private State append(char c) {
            return new State(this.input, this.segments, this.buffer.appendChar(c), this.depth, this.index);
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
            return new State(this.input, this.segments.addLast(this.buffer), Strings.empty(), this.depth, this.index);
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        public Option<State> popAndAppend() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }

        public Option<Character> peek() {
            if (this.index < this.input.length()) {
                return new Some<>(this.input.charAt(this.index));
            }
            else {
                return new None<>();
            }
        }
    }

    private record Joiner(String_ delimiter) implements Collector<String_, Option<String_>> {
        public Joiner() {
            this(Strings.empty());
        }

        public Joiner(String delimiter) {
            this(Strings.from(delimiter));
        }

        @Override
        public Option<String_> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String_> fold(Option<String_> current, String_ element) {
            return new Some<>(current.map(inner -> inner.appendOwned(this.delimiter).appendOwned(element)).orElse(element));
        }
    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return listEmpty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.addLast(element);
        }
    }

    private record Definition(
            List<String> annotations,
            List<String_> typeParams,
            Type type,
            String_ name
    ) implements Parameter {
        public Definition(Type type, String name) {
            this(Lists.listEmpty(), Lists.listEmpty(), type, Strings.from(name));
        }

        public Definition(Type type, String_ name) {
            this(Lists.listEmpty(), Lists.listEmpty(), type, name);
        }

        @Override
        public String_ generate() {
            return this.type.generate().appendSlice(" ").appendOwned(this.name);
        }

        public boolean equalsTo(Definition other) {
            return this.type.equalsTo(other.type) && this.name.equalsTo(other.name);
        }
    }

    private static final class Whitespace implements Parameter, Value {
        @Override
        public String_ generate() {
            return Strings.empty();
        }

    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    private record String_Value(String_ value) implements Value {
        @Override
        public String_ generate() {
            return Strings.from("\"")
                    .appendOwned(this.value)
                    .appendSlice("\"");
        }
    }

    private record Symbol(String_ value) implements Value {
        @Override
        public String_ generate() {
            return this.value;
        }
    }

    private record Invocation(Value caller, List<Value> args) implements Value {
        @Override
        public String_ generate() {
            return this.caller.generate()
                    .appendSlice("(")
                    .appendOwned(generateValueList(this.args))
                    .appendSlice(")");
        }
    }

    private record DataAccess(Value parent, String_ property) implements Value {
        @Override
        public String_ generate() {
            return this.parent.generate()
                    .appendSlice(".")
                    .appendOwned(this.property);
        }
    }

    private record Operation(Value left, Operator operator, Value right) implements Value {
        @Override
        public String_ generate() {
            return this.left.generate()
                    .appendSlice(" ")
                    .appendOwned(this.operator.representation)
                    .appendSlice(" ")
                    .appendOwned(this.right.generate());
        }
    }

    public static class Iterators {
        public static <T> Iterator<T> fromArray(T[] array) {
            return new Iterator<>(new RangeHead(array.length)).map(index -> array[index]);
        }

        public static <T> Iterator<T> fromOption(Option<T> option) {
            return new Iterator<>(switch (option) {
                case None<T> _ -> new EmptyHead<T>();
                case Some<T>(var value) -> new SingleHead<>(value);
            });
        }
    }

    private record CharValue(String_ slice) implements Value {
        @Override
        public String_ generate() {
            return Strings.from("'")
                    .appendOwned(this.slice)
                    .appendSlice("'");
        }
    }

    private record Not(Value value) implements Value {
        @Override
        public String_ generate() {
            return Strings.from("!").appendOwned(this.value.generate());
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }

        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier) {
            return supplier.get().mapValue(otherValue -> new Tuple<>(this.value, otherValue));
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }

        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(this.error));
        }
    }

    private record StructType(String_ name, List<Definition> definitions) implements Type {
        private Result<Type, CompileError> findTypeAsResult(String propertyKey) {
            if (this.findTypeAsOption(propertyKey) instanceof Some(var propertyValue)) {
                return new Ok<>(propertyValue);
            }

            return new Err<>(new CompileError("Undefined property on struct type '" + this.generate() + "'", new StringContext(Strings.from(propertyKey))));
        }

        @Override
        public String_ stringify() {
            return this.name;
        }

        @Override
        public boolean equalsTo(Type other) {
            if (other instanceof StructType structType) {
                return this.name.equalsTo(structType.name) && this.definitions.equalsTo(structType.definitions, Definition::equalsTo);
            }

            return false;
        }

        @Override
        public String_ generate() {
            var joinedDefinitions = this.definitions.iterate()
                    .map(Definition::generate)
                    .collect(new Joiner())
                    .orElse(Strings.empty());

            return Strings.from("struct ")
                    .appendOwned(this.name)
                    .appendSlice(" {")
                    .appendOwned(joinedDefinitions)
                    .appendSlice("}");
        }

        public Option<Type> findTypeAsOption(String name) {
            return findSymbolInDefinitions(this.definitions, name);
        }

        @Override
        public boolean hasTypeParams() {
            return this.definitions.iterate()
                    .map(definition -> definition.type)
                    .filter(type -> type instanceof TypeParam)
                    .next()
                    .isPresent();
        }
    }

    private record StructRef(String_ name) implements Type {
        public StructRef(String name) {
            this(Strings.from(name));
        }

        @Override
        public String_ generate() {
            return Strings.from("struct ").appendOwned(this.name);
        }

        @Override
        public String_ stringify() {
            return this.name;
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof StructRef ref && this.name.equalsTo(ref.name);
        }

        @Override
        public boolean hasTypeParams() {
            return false;
        }
    }

    private record Ref(Type type) implements Type {
        @Override
        public String_ stringify() {
            return this.type.stringify().appendSlice("_star");
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Ref ref && this.type.equalsTo(ref.type);
        }

        @Override
        public String_ generate() {
            return this.type.generate().appendSlice("*");
        }

        @Override
        public boolean hasTypeParams() {
            return this.type.hasTypeParams();
        }
    }

    private record Functional(List<Type> paramTypes, Type returns) implements Type {
        @Override
        public String_ generate() {
            var generated = generateValueList(this.paramTypes());
            return this.returns.generate().appendSlice(" (*)(").appendOwned(generated).appendSlice(")");
        }

        @Override
        public String_ stringify() {
            return Strings.from("_Func_")
                    .appendOwned(generateValueList(this.paramTypes))
                    .appendSlice("_")
                    .appendOwned(this.returns.stringify())
                    .appendSlice("_");
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Functional functional
                    && this.returns.equalsTo(functional.returns)
                    && this.paramTypes.equalsTo(functional.paramTypes, Type::equalsTo);
        }

        @Override
        public boolean hasTypeParams() {
            return this.returns.hasTypeParams() || this.paramTypes.iterate().collect(new AnyMatch<>(Type::hasTypeParams));
        }
    }

    private static class Max implements Collector<Integer, Option<Integer>> {
        @Override
        public Option<Integer> createInitial() {
            return new None<>();
        }

        @Override
        public Option<Integer> fold(Option<Integer> current, Integer element) {
            return new Some<>(current.map(inner -> inner > element ? inner : element).orElse(element));
        }
    }

    private record CompileError(String message, Context context, List<CompileError> errors) implements Error {
        public CompileError(String message, Context context) {
            this(message, context, listEmpty());
        }

        @Override
        public String_ display() {
            return this.format(0);
        }

        private String_ format(int depth) {
            return Strings.from(this.message)
                    .appendSlice(": ")
                    .appendOwned(this.context.display())
                    .appendOwned(this.joinErrors(depth));
        }

        private String_ joinErrors(int depth) {
            return this.errors
                    .sort((error, error2) -> error.computeMaxDepth() - error2.computeMaxDepth())
                    .iterate()
                    .map(error -> error.format(depth + 1))
                    .map(display -> Strings.from("\n").appendOwned(Strings.from("\t").repeat(depth)).appendOwned(display))
                    .collect(new Joiner())
                    .orElse(Strings.empty());
        }

        private int computeMaxDepth() {
            return 1 + this.errors.iterate()
                    .map(CompileError::computeMaxDepth)
                    .collect(new Max())
                    .orElse(0);
        }
    }

    private record Exceptional<T, C, X>(Collector<T, C> collector) implements Collector<Result<T, X>, Result<C, X>> {
        @Override
        public Result<C, X> createInitial() {
            return new Ok<>(this.collector.createInitial());
        }

        @Override
        public Result<C, X> fold(Result<C, X> current, Result<T, X> element) {
            return current.flatMapValue(currentValue -> element.mapValue(elementValue -> this.collector.fold(currentValue, elementValue)));
        }
    }

    private record OrState<T>(Option<T> maybeValue, List<CompileError> errors) {
        public OrState() {
            this(new None<>(), listEmpty());
        }

        public OrState<T> withValue(T value) {
            return new OrState<>(new Some<>(value), this.errors);
        }

        public OrState<T> withError(CompileError error) {
            return new OrState<>(this.maybeValue, this.errors.addLast(error));
        }

        public Result<T, List<CompileError>> toResult() {
            return switch (this.maybeValue) {
                case None<T> _ -> new Err<>(this.errors);
                case Some<T>(var value) -> new Ok<>(value);
            };
        }
    }

    private record ThrowableError(Throwable throwable) implements Error {
        @Override
        public String_ display() {
            var writer = new StringWriter();
            this.throwable.printStackTrace(new PrintWriter(writer));
            return Strings.from(writer.toString());
        }
    }

    private record ApplicationError(Error error) implements Error {
        @Override
        public String_ display() {
            return this.error.display();
        }
    }

    private record StringContext(String_ input) implements Context {
        @Override
        public String_ display() {
            return this.input;
        }
    }

    private record NodeContext(Node node) implements Context {
        @Override
        public String_ display() {
            return this.node.generate();
        }
    }

    private record Frame(Option<StructRef> maybeRef, List<Definition> definitions) {
        public Frame(List<Definition> definitions) {
            this(new None<>(), definitions);
        }

        public Frame() {
            this(Lists.listEmpty());
        }

        private Option<Type> findDefinitionInFrame(String name) {
            return findSymbolInDefinitions(this.definitions, name);
        }

        public Option<StructType> toStructType() {
            return this.maybeRef.map(ref -> new StructType(ref.name, this.definitions));
        }

        public Frame define(Definition definition) {
            return new Frame(this.maybeRef, this.definitions.addLast(definition));
        }

        public Frame withRef(StructRef ref) {
            return new Frame(new Some<>(ref), this.definitions);
        }
    }

    private static class Maps {
        public static <K, V> Map_<K, V> empty() {
            return new JavaMap<>();
        }

        @External
        private record JavaMap<K, V>(Map<K, V> map) implements Map_<K, V> {
            public JavaMap() {
                this(new HashMap<>());
            }

            @Override
            public Iterator<V> values() {
                return new Lists.JavaList<>(new ArrayList<>(this.map.values())).iterate();
            }

            @Override
            public Map_<K, V> put(K key, V value) {
                this.map.put(key, value);
                return this;
            }

            @Override
            public boolean containsKey(K key) {
                return this.map.containsKey(key);
            }

            @Override
            public V get(K key) {
                return this.map.get(key);
            }

            @Override
            public Iterator<K> keys() {
                return new Lists.JavaList<>(new ArrayList<>(this.map.keySet())).iterate();
            }
        }
    }

    private record AnyMatch<T>(Predicate<T> predicate) implements Collector<T, Boolean> {
        @Override
        public Boolean createInitial() {
            return false;
        }

        @Override
        public Boolean fold(Boolean current, T element) {
            return current || this.predicate.test(element);
        }
    }

    private record TypeParam(String_ value) implements Type {
        @Override
        public String_ stringify() {
            return this.value;
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof TypeParam param && this.value.equalsTo(param.value);
        }

        @Override
        public String_ generate() {
            return Strings.from("typeparam ").appendOwned(this.value);
        }

        @Override
        public boolean hasTypeParams() {
            return true;
        }
    }

    private record Generic(String_ base, List<Type> args) implements Type {
        @Override
        public String_ stringify() {
            return this.base.appendSlice("_").appendOwned(this.args.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .orElse(Strings.empty()));
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Generic generic
                    && this.base.equalsTo(generic.base)
                    && this.args.equalsTo(generic.args, Type::equalsTo);
        }

        @Override
        public String_ generate() {
            var string = this.args
                    .iterate()
                    .map(Node::generate)
                    .collect(new Joiner(", "))
                    .orElse(Strings.empty());

            return this.base.appendSlice("<").appendOwned(string).appendSlice(">");
        }

        @Override
        public boolean hasTypeParams() {
            return this.args.iterate().collect(new AnyMatch<>(Type::hasTypeParams));
        }
    }
}
