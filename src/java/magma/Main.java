package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    private interface Iterator<T> {
        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <C> C collect(Collector<T, C> collector);

        <R> Iterator<R> map(Function<T, R> mapper);

        <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

        Iterator<T> concat(Iterator<T> other);

        Optional<T> next();

        boolean anyMatch(Predicate<T> predicate);

        <R> Iterator<Tuple<T, R>> zip(Iterator<R> other);

        boolean allMatch(Predicate<T> predicate);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface List<T> {
        List<T> addLast(T element);

        Iterator<T> iterate();

        boolean contains(T element, BiFunction<T, T, Boolean> equator);

        boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator);
    }

    private interface Head<T> {
        Optional<T> next();
    }

    private interface Type {
        String stringify();

        String generate();

        boolean equalsTo(Type other);

        Type strip();
    }

    private @interface Actual {
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Optional<T> next() {
            return Optional.empty();
        }
    }

    private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).<Iterator<R>>fold(new HeadedIterator<R>(new EmptyHead<R>()), Iterator::concat);
        }

        @Override
        public Iterator<T> concat(Iterator<T> other) {
            return new HeadedIterator<>(() -> this.head.next().or(other::next));
        }

        @Override
        public Optional<T> next() {
            return this.head.next();
        }

        @Override
        public boolean anyMatch(Predicate<T> predicate) {
            return this.fold(false, (aBoolean, t) -> aBoolean || predicate.test(t));
        }

        @Override
        public <R> Iterator<Tuple<T, R>> zip(Iterator<R> other) {
            return new HeadedIterator<>(() -> {
                return this.head.next().flatMap(nextValue -> {
                    return other.next().map(otherValue -> {
                        return new Tuple<>(nextValue, otherValue);
                    });
                });
            });
        }

        @Override
        public boolean allMatch(Predicate<T> predicate) {
            return this.fold(true, (aBoolean, t) -> aBoolean && predicate.test(t));
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (optional.isPresent()) {
                    current = optional.get();
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }
    }

    private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        public RangeHead(int length) {
            this.length = length;
            this.counter = 0;
        }

        @Override
        public Optional<Integer> next() {
            if (this.counter >= this.length) {
                return Optional.empty();
            }
            var value = this.counter;
            this.counter++;
            return Optional.of(value);
        }
    }

    private static class StandardLibrary {
        @Actual
        private static <T> T[] allocate(int length) {
            return (T[]) new Object[length];
        }
    }

    private static class Lists {
        public static <T> List<T> of(T... elements) {
            return new ArrayList<>(elements, elements.length);
        }
    }

    private static class DivideState {
        private List<String> segments;
        private String buffer;
        private int depth;

        private DivideState(List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(new ArrayList<String>(), "", 0);
        }

        private DivideState advance() {
            this.segments = this.segments.addLast(this.buffer);
            this.buffer = "";
            return this;
        }

        private DivideState append(char c) {
            this.buffer = this.buffer + c;
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            this.depth++;
            return this;
        }

        public DivideState exit() {
            this.depth--;
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private record CompileState(
            List<String> structs,
            List<String> functions, Map<String, Function<List<Type>, Optional<CompileState>>> expandables,
            List<ObjectType> expansions, List<String> typeParams) {
        public CompileState() {
            this(new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new ArrayList<>(), new ArrayList<>());
        }

        private Optional<CompileState> expand(ObjectType expansion) {
            if (this.expansions.contains(expansion, ObjectType::equalsTo)) {
                return Optional.empty();
            }

            return this.addExpansion(expansion)
                    .findExpandable(expansion.name)
                    .flatMap(expandable -> expandable.apply(expansion.arguments));
        }

        private CompileState addExpansion(ObjectType type) {
            return new CompileState(this.structs, this.functions, this.expandables, this.expansions.addLast(type), this.typeParams);
        }

        public CompileState addStruct(String struct) {
            return new CompileState(this.structs.addLast(struct), this.functions, this.expandables, this.expansions, this.typeParams);
        }

        public CompileState addExpandable(String name, Function<List<Type>, Optional<CompileState>> expandable) {
            this.expandables.put(name, expandable);
            return this;
        }

        public Optional<Function<List<Type>, Optional<CompileState>>> findExpandable(String name) {
            if (this.expandables.containsKey(name)) {
                return Optional.of(this.expandables.get(name));
            }
            return Optional.empty();
        }

        public CompileState addTypeParameters(List<String> typeParams) {
            return new CompileState(this.structs, this.functions, this.expandables, this.expansions, typeParams);
        }

        public CompileState addFunction(String function) {
            return new CompileState(this.structs, this.functions.addLast(function), this.expandables, this.expansions, this.typeParams);
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record Joiner(String delimiter) implements Collector<String, Optional<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Optional<String> createInitial() {
            return Optional.empty();
        }

        @Override
        public Optional<String> fold(Optional<String> maybeCurrent, String element) {
            return Optional.of(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> fromOptional(Optional<T> optional) {
            return new HeadedIterator<>(optional.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved = false;

        public SingleHead(T value) {
            this.value = value;
        }

        @Override
        public Optional<T> next() {
            if (this.retrieved) {
                return Optional.empty();
            }
            this.retrieved = true;
            return Optional.of(this.value);
        }
    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return new ArrayList<>();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.addLast(element);
        }
    }

    private record Ref(Type type) implements Type {
        @Override
        public String stringify() {
            return this.type.stringify() + "_ref";
        }

        @Override
        public String generate() {
            return this.type.generate() + "*";
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Ref ref && this.type.equalsTo(ref.type);
        }

        @Override
        public Type strip() {
            return new Ref(this.type.strip());
        }
    }

    private record ObjectType(String name, List<Type> arguments) implements Type {
        @Override
        public String stringify() {
            return this.generate();
        }

        @Override
        public String generate() {
            return "struct " + this.name + this.joinArguments();
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof ObjectType objectType
                    && this.name.equals(objectType.name)
                    && this.arguments.equalsTo(objectType.arguments, Type::equalsTo);
        }

        @Override
        public Type strip() {
            var newArguments = this.arguments.iterate()
                    .map(Type::strip)
                    .collect(new ListCollector<>());

            return new ObjectType(this.name, newArguments);
        }

        private String joinArguments() {
            return this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");
        }
    }

    private record Placeholder(String value) implements Type {
        @Override
        public String stringify() {
            return generatePlaceholder(this.value);
        }

        @Override
        public String generate() {
            return generatePlaceholder(this.value);
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Placeholder placeholder && this.value.equals(placeholder.value);
        }

        @Override
        public Type strip() {
            return this;
        }
    }

    private record Definition(List<String> annotations, String afterAnnotations, Type type, String name,
                              List<String> typeParams) {
        private String generate() {
            return generatePlaceholder(this.afterAnnotations()) + " " + this.type().generate() + " " + this.name();
        }

        public Definition mapType(Function<Type, Type> mapper) {
            return new Definition(this.annotations, this.afterAnnotations, mapper.apply(this.type), this.name, this.typeParams);
        }
    }

    private static final class ArrayList<T> implements List<T> {
        public static final int DEFAULT_SIZE = 10;
        private int size;
        private T[] array;

        private ArrayList(T[] internal, int size) {
            this.array = internal;
            this.size = size;
        }

        public ArrayList() {
            this(StandardLibrary.allocate(DEFAULT_SIZE), 0);
        }

        @Override
        public String toString() {
            var joined = this.iterate()
                    .map(Objects::toString)
                    .collect(new Joiner(", "))
                    .orElse("");

            return "[" + joined + "]";
        }

        @Override
        public List<T> addLast(T element) {
            return this.set(this.size, element);
        }

        private List<T> set(int index, T element) {
            while (!(index < this.array.length)) {
                var copy = StandardLibrary.<T>allocate(this.array.length * 2);
                System.arraycopy(this.array, 0, copy, 0, this.array.length);
                this.array = copy;
            }

            this.array[index] = element;
            if (index >= this.size) {
                this.size = index + 1;
            }
            return this;
        }

        @Override
        public Iterator<T> iterate() {
            return new HeadedIterator<>(new RangeHead(this.size))
                    .map(index -> this.array[index]);
        }

        @Override
        public boolean contains(T otherElement, BiFunction<T, T, Boolean> equator) {
            return this.iterate().anyMatch(thisElement -> equator.apply(thisElement, otherElement));
        }

        @Override
        public boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator) {
            return this.iterate().zip(others.iterate()).allMatch(tuple -> {
                return equator.apply(tuple.left(), tuple.right());
            });
        }
    }

    private record TypeParam(String input) implements Type {

        @Override
        public String stringify() {
            return this.input;
        }

        @Override
        public String generate() {
            return "template " + this.input;
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof TypeParam param && this.input.equals(param.input);
        }

        @Override
        public Type strip() {
            return Primitive.Void;
        }
    }

    public static void main() {
        try {
            var root = Paths.get(".", "src", "java", "magma");
            var source = root.resolve("main.java");
            var target = root.resolve("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang.exe", target.toAbsolutePath().toString(), "-o", "main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var joinedStructs = compiled.left.structs
                .iterate()
                .collect(new Joiner())
                .orElse("");

        return joinedStructs + compiled.right + "\nint main(){\n\treturn 0;\n}\n";
    }

    private static Tuple<CompileState, String> compileStatements(CompileState initial, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::merge);
    }

    private static Tuple<CompileState, String> compileAll(CompileState initial, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<String, String, String> merger) {
        var tuple = parseAll(initial, input, folder, mapper);
        return new Tuple<>(tuple.left, generateAll(tuple.right, merger));
    }

    private static <T> Tuple<CompileState, List<T>> parseAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
    ) {
        var segments = divide(input, folder);

        var tuple = new Tuple<>(initial, (List<T>) new ArrayList<T>());
        var folded = segments.iterate().fold(tuple, (tuple0, element) -> {
            var mapped = mapper.apply(tuple0.left, element);
            return new Tuple<>(mapped.left, tuple0.right.addLast(mapped.right));
        });

        return new Tuple<CompileState, List<T>>(folded.left, tuple.right);
    }

    private static String generateAll(List<String> elements, BiFunction<String, String, String> merger) {
        return elements.iterate().fold("", merger);
    }

    private static String merge(String buffer, String element) {
        return buffer + element;
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
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
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return createStructureRule("class ").apply(state, input)
                .orElseGet(() -> new Tuple<>(state, "\n\t" + generatePlaceholder(stripped.strip())));
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureRule(String infix) {
        return (state, input) -> compileFirst(input, infix, (beforeKeyword, afterKeyword) -> {
            return compileFirst(afterKeyword, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content1 -> {
                    return compileOr(state, beforeContent, Lists.of(
                            createStructureWithTypeParamsRule(beforeKeyword, content1),
                            createStructureWithoutTypeParamsRule(beforeKeyword, content1)
                    ));
                });
            });
        });
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithTypeParamsRule(String beforeKeyword, String content) {
        return (state, input) -> {
            return compileSuffix(input.strip(), ">", withoutEnd -> {
                return compileFirst(withoutEnd, "<", (name, typeParameters) -> {
                    var typeParams = divide(typeParameters, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return Optional.of(new Tuple<>(state.addExpandable(name, (typeArguments) -> {
                        return assembleStructure(state, beforeKeyword, name, typeParams, typeArguments, content);
                    }), ""));
                });
            });
        };
    }

    private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',') {
            return state.advance();
        }
        return state.append(c);
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithoutTypeParamsRule(String beforeKeyword, String content) {
        return (state, name) -> {
            return assembleStructure(state, beforeKeyword, name, new ArrayList<String>(), new ArrayList<Type>(), content).map(newState -> {
                return new Tuple<>(newState, "");
            });
        };
    }

    private static Optional<CompileState> assembleStructure(
            CompileState state,
            String beforeStruct,
            String name,
            List<String> typeParams,
            List<Type> typeArguments,
            String content
    ) {
        return compileSymbol(name.strip(), strippedName -> {
            var statementsTuple = compileStatements(state, content, Main::compileClassSegment);
            var generated = generatePlaceholder(beforeStruct.strip()) + new ObjectType(strippedName, typeArguments).generate() + " {" + statementsTuple.right + "\n};\n";
            var added = statementsTuple.left.addStruct(generated);
            return Optional.of(added);
        });
    }

    private static <T> Optional<T> compileSymbol(String input, Function<String, Optional<T>> mapper) {
        if (!isSymbol(input)) {
            return Optional.empty();
        }

        return mapper.apply(input);
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

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                createStructureRule("class "),
                createStructureRule("interface "),
                Main::compileDefinitionStatement,
                Main::compileMethod
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (definitionString, withParams) -> {
            return compileFirst(withParams, ")", (params, oldContent) -> {
                return parseDefinition(state, definitionString).flatMap(definitionTuple -> {
                    var definitionState = definitionTuple.left;
                    var definition = definitionTuple.right;

                    Definition newDefinition;
                    String newContent;
                    if (definition.annotations.contains("Actual", String::equals) || oldContent.equals(";")) {
                        newDefinition = definition.mapType(Type::strip);
                        newContent = ";";
                    }
                    else {
                        newContent = ";" + generatePlaceholder(oldContent);
                        newDefinition = definition;
                    }

                    var generatedHeader = "\n\t" + newDefinition.generate() + "(" + generatePlaceholder(params) + ")";
                    var generated = generatedHeader + newContent;
                    return Optional.of(new Tuple<>(definitionState.addFunction(generated), ""));
                });
            });
        });
    }

    private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input.strip())));
    }

    private static <T> Optional<Tuple<CompileState, T>> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, T>>>> rules
    ) {
        return rules.iterate()
                .map(rule -> rule.apply(state, input))
                .flatMap(Iterators::fromOptional)
                .next();
    }

    private static Optional<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd)
                    .map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))
                    .map(tuple -> new Tuple<>(tuple.left, "\n\t" + tuple.right + ";"));
        });
    }

    private static Optional<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return compileInfix(input.strip(), " ", Main::findLast, (beforeName, rawName) -> {
            return compileInfix(beforeName.strip(), " ", Main::findLast, (beforeType, type) -> {
                var strippedBeforeType = beforeType.strip();
                return compileInfix(strippedBeforeType, "\n", Main::findLast, (annotationsString, afterAnnotations) -> {
                    var annotations = divide(annotationsString, foldWithDelimiter('\n'))
                            .iterate()
                            .map(slice -> slice.substring(1))
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return assembleDefinition(state, annotations, afterAnnotations.strip(), rawName, type);
                }).or(() -> {
                    return assembleDefinition(state, new ArrayList<>(), strippedBeforeType, rawName, type);
                });
            });
        });
    }

    private static Optional<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, List<String> annotations, String afterAnnotations, String rawName, String type) {
        return compileSuffix(afterAnnotations.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (beforeTypeParams, typeParams) -> {
                var typeParamsTuple = Main.parseValues(state, typeParams, (state1, s1) -> new Tuple<>(state1, s1.strip()));
                return getCompileStateDefinitionTuple(typeParamsTuple.left, annotations, beforeTypeParams.strip(), typeParamsTuple.right, rawName, type);
            });
        }).or(() -> {
            return getCompileStateDefinitionTuple(state, annotations, afterAnnotations, new ArrayList<>(), rawName, type);
        });
    }

    private static Optional<Tuple<CompileState, Definition>> getCompileStateDefinitionTuple(CompileState state, List<String> annotations, String afterAnnotations, List<String> typeParams, String rawName, String type) {
        return compileSymbol(rawName.strip(), name -> {
            var typeTuple = parseType(state.addTypeParameters(typeParams), type);
            return Optional.of(new Tuple<>(typeTuple.left, new Definition(annotations, afterAnnotations, typeTuple.right, name, typeParams)));
        });
    }

    private static BiFunction<DivideState, Character, DivideState> foldWithDelimiter(char delimiter) {
        return (state, c) -> {
            if (c == delimiter) {
                return state.advance();
            }
            return state.append(c);
        };
    }

    private static Tuple<CompileState, Type> parseType(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                typed(Main::parsePrimitive),
                typed(Main::parseTemplate),
                typed(Main::parseArray),
                typed(Main::parseTypeParam)
        )).orElseGet(() -> new Tuple<>(state, new Placeholder(input.strip())));
    }

    private static Optional<Tuple<CompileState, Type>> parseTypeParam(CompileState state, String input) {
        var stripped = input.strip();
        if (state.typeParams.contains(stripped, String::equals)) {
            return Optional.of(new Tuple<>(state, new TypeParam(stripped)));
        }

        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, Type>> parseArray(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("[]")) {
            var slice = input.substring(0, stripped.length() - "[]".length());
            var childTuple = parseType(state, slice);
            return Optional.of(new Tuple<>(childTuple.left, new Ref(childTuple.right)));
        }

        return Optional.empty();
    }

    private static <T extends R, R> BiFunction<CompileState, String, Optional<Tuple<CompileState, R>>> typed(BiFunction<CompileState, String, Optional<Tuple<CompileState, T>>> rule) {
        return (state, input) -> rule.apply(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right));
    }

    private static Optional<Tuple<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("String")) {
            return Optional.of(new Tuple<>(state, new Ref(Primitive.Char)));
        }
        if (stripped.equals("int")) {
            return Optional.of(new Tuple<>(state, Primitive.Int));
        }
        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, ObjectType>> parseTemplate(CompileState oldState, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = parseValues(oldState, argumentsString, Main::parseType);

                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right;

                var expansion = new ObjectType(base, arguments);
                var withExpansion = argumentsState.expand(expansion).orElse(argumentsState);
                return Optional.of(new Tuple<>(withExpansion, expansion));
            });
        });
    }

    private static <T> Tuple<CompileState, List<T>> parseValues(CompileState oldState, String argumentsString, BiFunction<CompileState, String, Tuple<CompileState, T>> mapper) {
        return parseAll(oldState, argumentsString, Main::foldValueChar, mapper);
    }

    private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }

        return Optional.empty();
    }

    private static <T> Optional<T> compileFirst(String stripped, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(stripped, infix, Main::findFirst, mapper);
    }

    private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Optional<Integer>> locate, BiFunction<String, String, Optional<T>> mapper) {
        return locate.apply(input, infix).flatMap(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return mapper.apply(left, right);
        });
    }

    private static Optional<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/* ", "content-start")
                .replace("*/", "content-end");

        return "/* " + replaced + " */";
    }

    private enum Primitive implements Type {
        Char("char"),
        Int("int"),
        Void("void");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String stringify() {
            return this.value;
        }

        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public boolean equalsTo(Type other) {
            return this == other;
        }

        @Override
        public Type strip() {
            return this;
        }
    }
}
