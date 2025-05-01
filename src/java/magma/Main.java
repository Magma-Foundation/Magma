package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    private sealed interface Option<T> permits Some, None {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isEmpty();

        T orElse(T other);

        Option<T> or(Supplier<Option<T>> other);

        T orElseGet(Supplier<T> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        Option<T> filter(Predicate<T> predicate);

        boolean isPresent();
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface List<T> {
        List<T> addLast(T element);

        Iterator<T> iterate();

        Option<Tuple<List<T>, T>> removeLast();

        boolean isEmpty();

        T get(int index);

        List<T> addFirst(T element);

        boolean contains(T element);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private @interface External {
    }

    private interface Splitter {
        Option<Tuple<String, String>> split(String input);
    }

    private interface Type extends Node {
        default String_ generateWithName(String name) {
            return this.generate().appendSlice(" ").appendSlice(name);
        }
    }

    private interface Parameter extends Node {
    }

    private interface Node {
        String_ generate();
    }

    private record String_(String value) {
        public String toSlice() {
            return this.value;
        }

        public String_ appendSlice(String slice) {
            return new String_(this.value + slice);
        }
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
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

    private record None<T>() implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other.get();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
            return new None<>();
        }

        @Override
        public boolean isPresent() {
            return false;
        }
    }

    private record Iterator<T>(Head<T> head) {
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        private <C> C fold(C initial, BiFunction<C, T, C> folder) {
            var current = initial;
            while (true) {
                C finalCurrent = current;
                var maybeNext = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (maybeNext.isEmpty()) {
                    return current;
                }
                else {
                    current = maybeNext.orElse(null);
                }
            }
        }

        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).fold(new Iterator<>(new EmptyHead<>()), Iterator::concat);
        }

        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        private Iterator<T> concat(Iterator<T> other) {
            return new Iterator<>(() -> this.head.next().or(other::next));
        }

        public Option<T> next() {
            return this.head.next();
        }
    }

    private static final class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        private RangeHead(int length) {
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

    @External
    private record JavaList<T>(java.util.List<T> elements) implements List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public List<T> addLast(T element) {
            var copy = new ArrayList<>(this.elements);
            copy.add(element);
            return new JavaList<>(copy);
        }

        @Override
        public Iterator<T> iterate() {
            return new Iterator<>(new RangeHead(this.elements.size())).map(this.elements::get);
        }

        @Override
        public Option<Tuple<List<T>, T>> removeLast() {
            if (this.elements.isEmpty()) {
                return new None<>();
            }

            var slice = this.elements.subList(0, this.elements.size() - 1);
            var last = this.elements.getLast();
            return new Some<>(new Tuple<>(new JavaList<>(new ArrayList<>(slice)), last));
        }

        @Override
        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        @Override
        public T get(int index) {
            return this.elements.get(index);
        }

        @Override
        public List<T> addFirst(T element) {
            var copy = this.copy();
            copy.addFirst(element);
            return new JavaList<>(copy);
        }

        @Override
        public boolean contains(T element) {
            return this.elements.contains(element);
        }

        private java.util.List<T> copy() {
            return new ArrayList<T>(this.elements);
        }
    }

    private static class Lists {
        public static <T> List<T> of(T... elements) {
            return new JavaList<>(Arrays.asList(elements));
        }

        public static <T> List<T> empty() {
            return new JavaList<>(new ArrayList<>());
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        public Joiner() {
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

    private record StructurePrototype(String type, String name, List<String> typeParams, List<String> variants) {
    }

    private record CompileState(
            List<String> structs,
            List<String> functions,
            Option<StructurePrototype> maybeStructureType
    ) {
        public CompileState() {
            this(Lists.empty(), Lists.empty(), new None<>());
        }

        private String generate() {
            return this.getJoin(this.structs) + this.getJoin(this.functions);
        }

        private String getJoin(List<String> lists) {
            return lists.iterate().collect(new Joiner()).orElse("");
        }

        public CompileState addStruct(String struct) {
            return new CompileState(this.structs.addLast(struct), this.functions, this.maybeStructureType);
        }

        public CompileState addFunction(String function) {
            return new CompileState(this.structs, this.functions.addLast(function), this.maybeStructureType);
        }

        public CompileState withStructType(StructurePrototype type) {
            return new CompileState(this.structs, this.functions, new Some<>(type));
        }

        public CompileState withoutStructType() {
            return new CompileState(this.structs, this.functions, new None<>());
        }
    }

    private record DivideState(String input, List<String> segments, StringBuilder buffer, int index, int depth) {
        public DivideState(String input) {
            this(input, new JavaList<>(), new StringBuilder(), 0, 0);
        }

        private Option<DivideState> popAndAppend() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }

        private Option<Tuple<Character, DivideState>> popAndAppendToTuple() {
            return this.pop().map(tuple -> {
                var c = tuple.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            });
        }

        private DivideState append(char c) {
            return new DivideState(this.input, this.segments, this.buffer.append(c), this.index, this.depth);
        }

        public Option<Tuple<Character, DivideState>> pop() {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<Character, DivideState>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            }
            else {
                return new None<>();
            }
        }

        private DivideState advance() {
            var withBuffer = this.buffer.isEmpty() ? this.segments : this.segments.addLast(this.buffer.toString());
            return new DivideState(this.input, withBuffer, new StringBuilder(), this.index, this.depth);
        }

        public DivideState exit() {
            return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth - 1);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth + 1);
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private record Tuple<A, B>(A left, B right) {
        public static <A, B, C> Function<Tuple<A, B>, Tuple<A, C>> mapRight(Function<B, C> mapper) {
            return tuple -> new Tuple<>(tuple.left, mapper.apply(tuple.right));
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> fromOptions(Option<T> option) {
            return new Iterator<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved = false;

        public SingleHead(T value) {
            this.value = value;
        }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            }

            this.retrieved = true;
            return new Some<>(this.value);
        }
    }

    private record InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter {
        @Override
        public Option<Tuple<String, String>> split(String input) {
            return this.apply(input).map(classIndex -> {
                var beforeKeyword = input.substring(0, classIndex);
                var afterKeyword = input.substring(classIndex + this.length());
                return new Tuple<>(beforeKeyword, afterKeyword);
            });
        }

        private int length() {
            return this.infix.length();
        }

        private Option<Integer> apply(String input) {
            return this.locator().apply(input, this.infix);
        }
    }

    private static class TypeSeparatorSplitter implements Splitter {
        @Override
        public Option<Tuple<String, String>> split(String input) {
            return divide(input, TypeSeparatorSplitter::fold).removeLast().flatMap(segments -> {
                var left = segments.left;
                if (left.isEmpty()) {
                    return new None<>();
                }

                var beforeType = left.iterate().collect(new Joiner(" ")).orElse("");
                var type = segments.right;
                return new Some<>(new Tuple<>(beforeType, type));
            });
        }

        private static DivideState fold(DivideState state, char c) {
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
    }

    private record Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter {
        public Definition(Type type, String name) {
            this(new None<>(), type, name);
        }

        public Definition mapName(Function<String, String> mapper) {
            return new Definition(this.maybeBeforeType, this.type, mapper.apply(this.name));
        }

        @Override
        public String_ generate() {
            return new String_(this.generate0());
        }

        private String generate0() {
            var beforeTypeString = this.maybeBeforeType.map(beforeType -> generatePlaceholder(beforeType) + " ").orElse("");
            return beforeTypeString + this.type.generateWithName(this.name).toSlice();
        }
    }

    private record Content(String input) implements Type, Parameter {
        @Override
        public String_ generate() {
            return new String_(this.generate0());
        }

        private String generate0() {
            return generatePlaceholder(this.input);
        }
    }

    private record Functional(List<Type> arguments, Type returns) implements Type {
        @Override
        public String_ generate() {
            return this.generateWithName("");
        }

        @Override
        public String_ generateWithName(String name) {
            var joinedArguments = this.arguments().iterate()
                    .map(type -> type.generate().toSlice())
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.returns.generate()
                    .appendSlice(" (*")
                    .appendSlice(name)
                    .appendSlice(")(")
                    .appendSlice(joinedArguments)
                    .appendSlice(")");
        }
    }

    private record Template(String base, List<Type> arguments) implements Type {
        @Override
        public String_ generate() {
            return new String_(this.generate0());
        }

        private String generate0() {
            var generatedTuple = this.arguments().iterate()
                    .map(type -> type.generate().toSlice())
                    .collect(new Joiner(", "))
                    .orElse("");

            return "template " + this.base() + "<" + generatedTuple + ">";
        }
    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return Lists.empty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.addLast(element);
        }
    }

    private record TypeParameter(String value) implements Type {
        @Override
        public String_ generate() {
            return new String_(this.generate0());
        }

        private String generate0() {
            return this.value;
        }
    }

    private record Ref(Type type) implements Type {
        @Override
        public String_ generate() {
            return new String_(this.generate0());
        }

        private String generate0() {
            return this.type.generate().toSlice() + "*";
        }
    }

    private record TupleType(List<Type> arguments) implements Type {
        @Override
        public String_ generate() {
            return new String_(this.generate0());
        }

        private String generate0() {
            return "(" + generateNodesAsValues(this.arguments) + ")";
        }
    }

    private record StructRef(String input) implements Type {
        @Override
        public String_ generate() {
            return new String_("struct ").appendSlice(this.input);
        }
    }

    private enum Primitive implements Type {
        Auto("auto"),
        I8("char"),
        I32("int");
        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String_ generate() {
            return new String_(this.generate0());
        }

        private String generate0() {
            return this.value;
        }
    }

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
        var state = new CompileState();
        var tuple = compileAll(state, input, Main::compileRootSegment)
                .orElse(new Tuple<>(state, ""));

        return tuple.right + tuple.left.generate();
    }

    private static Option<Tuple<CompileState, String>> compileAll(
            CompileState initial,
            String input,
            BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper
    ) {
        return all(initial, input, Main::foldStatementChar, mapper, Main::mergeStatements);
    }

    private static Option<Tuple<CompileState, String>> all(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        return parseAll(initial, input, folder, mapper).map(tuple -> new Tuple<>(tuple.left, generateAll(merger, tuple.right)));
    }

    private static StringBuilder mergeStatements(StringBuilder output, String right) {
        return output.append(right);
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

    private static Option<Tuple<CompileState, String>> compileRootSegment(CompileState state, String input) {
        return or(state, input, Lists.of(
                Main::whitespace,
                Main::compileNamespaced,
                structure("class", "class "),
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> structure(String type, String infix) {
        return (state, input) -> first(input, infix, (beforeKeyword, afterKeyword) -> {
            var slices = Arrays.stream(beforeKeyword.split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            if (slices.contains("@External")) {
                return new None<>();
            }

            return first(afterKeyword, "{", (beforeContent, withEnd) -> {
                return or(state, beforeContent, Lists.of(
                        (state0, beforeContent0) -> structureWithVariants(type, state0, beforeKeyword, beforeContent0, withEnd),
                        (state0, beforeContent0) -> structureWithoutVariants(type, state0, beforeKeyword, beforeContent0, Lists.empty(), withEnd)
                ));
            });
        });
    }

    private static Option<Tuple<CompileState, String>> structureWithVariants(String type, CompileState state, String beforeKeyword, String beforeContent, String withEnd) {
        return first(beforeContent, " permits ", (beforePermits, variantsString) -> {
            return parseValues(state, variantsString, Main::symbol).flatMap(params -> {
                return structureWithoutVariants(type, params.left, beforeKeyword, beforePermits, params.right, withEnd);
            });
        });
    }

    private static Option<Tuple<CompileState, String>> symbol(CompileState state, String value) {
        return new Some<>(new Tuple<>(state, value.strip()));
    }

    private static Option<Tuple<CompileState, String>> structureWithoutVariants(String type, CompileState state, String beforeKeyword, String beforeContent, List<String> variants, String withEnd) {
        return or(state, beforeContent, Lists.of(
                (instance, before) -> structureWithParams(type, instance, beforeKeyword, before, variants, withEnd),
                (instance, before) -> structureWithMaybeTypeParams(type, instance, beforeKeyword, before.strip(), "", variants, withEnd)
        ));
    }

    private static Option<Tuple<CompileState, String>> structureWithParams(String type, CompileState instance, String beforeKeyword, String beforeContent, List<String> variants, String withEnd) {
        return suffix(beforeContent.strip(), ")", withoutEnd -> first(withoutEnd, "(", (name, paramString) -> {
            return all(instance, paramString, Main::foldValueChar, (instance1, paramString1) -> parameter(instance1, paramString1).map(Tuple.mapRight(parameter -> parameter.generate().toSlice())), Main::mergeStatements).flatMap(params -> {
                return structureWithMaybeTypeParams(type, params.left, beforeKeyword, name, params.right, variants, withEnd);
            });
        }));
    }

    private static Option<Tuple<CompileState, Parameter>> parameter(CompileState instance, String paramString) {
        return Main.or(instance, paramString, Lists.of(
                wrap(Main::definition),
                wrap(Main::content)
        ));
    }

    private static Option<Tuple<CompileState, String>> structureWithMaybeTypeParams(
            String type,
            CompileState state,
            String beforeKeyword,
            String beforeParams,
            String params,
            List<String> variants,
            String withEnd
    ) {
        return or(state, beforeParams, Lists.of(
                (state0, beforeParams0) -> structureWithTypeParams(type, state0, beforeParams0, beforeKeyword, params, variants, withEnd),
                (state0, name) -> structureWithName(type, state0, beforeKeyword, name, Lists.empty(), params, variants, withEnd)
        ));
    }

    private static Option<Tuple<CompileState, String>> structureWithTypeParams(
            String type,
            CompileState state,
            String beforeParams0,
            String beforeKeyword,
            String params,
            List<String> variants,
            String withEnd
    ) {
        return suffix(beforeParams0.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (name, typeParamString) -> {
                return parseValues(state, typeParamString, Main::symbol).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> structureWithName(
            String type,
            CompileState state,
            String beforeKeyword,
            String name,
            List<String> typeParams,
            String params,
            List<String> variants,
            String withEnd
    ) {
        return suffix(withEnd.strip(), "}", content -> {
            return compileAll(state.withStructType(new StructurePrototype(type, name, typeParams, variants)), content, Main::structSegment).flatMap(tuple -> {
                return new Some<>(assembleStruct(type, tuple.left, beforeKeyword, name, typeParams, params, variants, tuple.right));
            }).map(tuple -> new Tuple<>(tuple.left.withoutStructType(), tuple.right));
        });
    }

    private static Tuple<CompileState, String> assembleStruct(
            String type,
            CompileState state,
            String beforeKeyword,
            String name,
            List<String> typeParams,
            String params,
            List<String> variants,
            String oldContent
    ) {
        if (!variants.isEmpty()) {
            var enumName = name + "Variant";
            var enumFields = variants.iterate()
                    .map(variant -> "\n\t" + variant)
                    .collect(new Joiner(","))
                    .orElse("");
            var generatedEnum = "enum " + enumName + " {" + enumFields + "\n};\n";

            var typeParamString = generateTypeParams(typeParams);
            var unionName = name + "Value" + typeParamString;
            var unionFields = variants.iterate()
                    .map(variant -> "\n\t" + variant + typeParamString + " " + variant.toLowerCase() + ";")
                    .collect(new Joiner(""))
                    .orElse("");
            var generateUnion = "union " + unionName + " {" + unionFields + "\n};\n";

            var compileState = state.addStruct(generatedEnum).addStruct(generateUnion);
            var newContent = "\n\t" + enumName + " _variant;"
                    + "\n\t" + unionName + " _value;"
                    + oldContent;

            return generateStruct(compileState, beforeKeyword, name, typeParamString, params, newContent);
        }

        if (type.equals("interface")) {
            var typeParamString = generateTypeParams(typeParams.addFirst("S"));
            var newContent = "\n\tS _super;" + oldContent;
            return generateStruct(state, beforeKeyword, name, typeParamString, params, newContent);
        }

        return generateStruct(state, beforeKeyword, name, generateTypeParams(typeParams), params, oldContent);
    }

    private static Tuple<CompileState, String> generateStruct(
            CompileState state,
            String beforeKeyword,
            String name,
            String typeParamString,
            String params,
            String content
    ) {
        var generatedStruct = generatePlaceholder(beforeKeyword.strip()) + "struct " + name + typeParamString + " {" + params + content + "\n};\n";
        return new Tuple<CompileState, String>(state.addStruct(generatedStruct), "");
    }

    private static String generateTypeParams(List<String> typeParams) {
        return typeParams.isEmpty() ? "" : "<" + typeParams.iterate().collect(new Joiner(", ")).orElse("") + ">";
    }

    private static <T> Option<Tuple<CompileState, T>> or(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple<CompileState, T>>>> actions
    ) {
        return actions.iterate()
                .map(action -> action.apply(state, input))
                .flatMap(Iterators::fromOptions)
                .next();
    }

    private static Option<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> structSegment(CompileState state, String input) {
        return or(state, input, Lists.of(
                Main::whitespace,
                Main::annotation,
                structure("record", "record "),
                structure("interface", "interface "),
                Main::method,
                Main::definitionStatement,
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        ));
    }

    private static Option<Tuple<CompileState, String>> annotation(CompileState state, String input) {
        return first(input, "@interface", (_, _) -> new Some<>(new Tuple<>(state, "")));
    }

    private static Option<Tuple<CompileState, String>> definitionStatement(CompileState state, String input) {
        return suffix(input.strip(), ";", withoutEnd -> definition(state, withoutEnd).map(Tuple.mapRight(definition -> definition.generate().toSlice())).map(value -> {
            var generated = "\n\t" + value.right + ";";
            return new Tuple<>(value.left, generated);
        }));
    }

    private static Option<Tuple<CompileState, Content>> content(CompileState state, String input) {
        return new Some<>(new Tuple<CompileState, Content>(state, new Content(input)));
    }

    private static Option<Tuple<CompileState, String>> whitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> method(CompileState state, String input) {
        return first(input, "(", (inputDefinition, withParams) -> {
            return first(withParams, ")", (paramsString, withBraces) -> {
                return compileMethodHeader(state, inputDefinition).flatMap(outputDefinition -> {
                    return parseValues(outputDefinition.left, paramsString, Main::parameter).flatMap(outputParams -> {
                        var params = outputParams.right
                                .iterate()
                                .map(Main::retainDefinition)
                                .flatMap(Iterators::fromOptions)
                                .collect(new ListCollector<>());

                        return or(outputParams.left, withBraces, Lists.of(
                                (state0, element) -> methodWithoutContent(state0, outputDefinition.right, params, element),
                                (state0, element) -> methodWithContent(state0, outputDefinition.right, params, element)));
                    });
                });
            });
        });
    }

    private static Option<Definition> retainDefinition(Parameter param) {
        if (param instanceof Definition definition) {
            return new Some<>(definition);
        }
        else {
            return new None<>();
        }
    }

    private static <T> Option<Tuple<CompileState, List<T>>> parseValues(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> compiler
    ) {
        return parseAll(state, input, Main::foldValueChar, compiler);
    }

    private static <T> Option<Tuple<CompileState, List<T>>> parseAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> mapper
    ) {
        return divide(input, folder)
                .iterate()
                .<Option<Tuple<CompileState, List<T>>>>fold(new Some<>(new Tuple<CompileState, List<T>>(initial, Lists.empty())),
                        (maybeCurrent, segment) -> maybeCurrent.flatMap(
                                state -> foldElement(state, segment, mapper)));
    }

    private static <T> Option<Tuple<CompileState, List<T>>> foldElement(
            Tuple<CompileState, List<T>> state,
            String segment,
            BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> mapper
    ) {
        var oldState = state.left;
        var oldCache = state.right;
        return mapper.apply(oldState, segment).map(result -> {
            var newState = result.left;
            var newElement = result.right;
            return new Tuple<>(newState, oldCache.addLast(newElement));
        });
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        DivideState current = new DivideState(input);
        while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var popped = maybePopped.orElse(null);
            var c = popped.left;
            var state = popped.right;
            current = foldSingleQuotes(state, c)
                    .or(() -> foldDoubleQuotes(state, c))
                    .orElseGet(() -> folder.apply(state, c));
        }
        return current.advance().segments;
    }

    private static Option<DivideState> foldDoubleQuotes(DivideState state, char c) {
        if (c != '\"') {
            return new None<>();
        }

        var appended = state.append(c);
        while (true) {
            var maybeTuple = appended.popAndAppendToTuple();
            if (maybeTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeTuple.orElse(null);
            var next = nextTuple.left;
            appended = nextTuple.right;

            if (next == '\\') {
                appended = appended.popAndAppend().orElse(appended);
            }
            if (next == '\"') {
                break;
            }
        }

        return new Some<>(appended);
    }

    private static Option<DivideState> foldSingleQuotes(DivideState state, char c) {
        if (c != '\'') {
            return new None<>();
        }

        return state.append(c).pop().map(maybeNextTuple -> {
            var nextChar = maybeNextTuple.left;
            var nextState = maybeNextTuple.right.append(nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState.popAndAppend().orElse(nextState)
                    : nextState;

            return withEscaped.popAndAppend().orElse(withEscaped);
        });
    }

    private static DivideState foldValueChar(DivideState state, char c) {
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

    private static <T extends Node> String generateNodesAsValues(List<T> params) {
        return params.iterate()
                .map(t -> t.generate().toSlice())
                .collect(new Joiner(", "))
                .orElse("");
    }

    private static String generateAll(BiFunction<StringBuilder, String, StringBuilder> merger, List<String> right) {
        return right.iterate().fold(new StringBuilder(), merger).toString();
    }

    private static Option<Tuple<CompileState, String>> methodWithoutContent(
            CompileState state,
            Definition definition,
            List<Definition> params,
            String content
    ) {
        if (!content.equals(";")) {
            return new None<>();
        }

        String generated;
        if (state.maybeStructureType.filter(value -> value.type.equals("interface") && value.variants.isEmpty()).isPresent()) {
            var returnType = definition.type;
            var name = definition.name;
            var argumentTypes = params.iterate()
                    .map(Definition::type)
                    .collect(new ListCollector<>())
                    .addFirst(new TypeParameter("S"));

            var functionalType = new Functional(argumentTypes, returnType);
            var definition0 = new Definition(functionalType, name);
            generated = "\n\t" + definition0.generate().toSlice() + ";";
        }
        else {
            generated = "";
        }

        return new Some<>(new Tuple<CompileState, String>(state, generated));
    }

    private static Option<Tuple<CompileState, String>> methodWithContent(CompileState state, Definition definition, List<Definition> params, String withBraces) {
        return prefix(withBraces.strip(), "{", withoutStart1 -> {
            return suffix(withoutStart1, "}", content -> {
                return compileAll(state, content, Main::functionSegment).flatMap(tuple -> {
                    var newParameters = state.maybeStructureType
                            .map(structType -> params.addFirst(new Definition(new StructRef(structType.name), "this")))
                            .orElse(params);
                    var paramStrings = generateNodesAsValues(newParameters);

                    var generated = definition
                            .mapName(name -> state.maybeStructureType.map(structureType -> name + "_" + structureType.name).orElse(name)).generate().toSlice() + "(" + paramStrings + "){" + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(state.addFunction(generated), ""));
                });
            });
        });
    }

    private static Option<Tuple<CompileState, Definition>> compileMethodHeader(CompileState state, String definition) {
        return or(state, definition, Lists.of(
                Main::definition,
                Main::constructor
        ));
    }

    private static Option<Tuple<CompileState, Definition>> constructor(CompileState state, String input) {
        return split(input.strip(), new InfixSplitter(" ", Main::lastIndexOfSlice), (_, name) -> state.maybeStructureType.flatMap(structureType -> {
            if (!structureType.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        }));
    }

    private static Option<Tuple<CompileState, String>> functionSegment(CompileState state, String input) {
        return or(state, input.strip(), Lists.of(
                Main::whitespace,
                (state0, input1) -> suffix(input1.strip(), ";", slice -> statementValue(state0, slice).map(Tuple.mapRight(slice0 -> "\n\t" + slice0 + ";"))),
                (state0a, input1) -> content(state0a, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        ));
    }

    private static Option<Tuple<CompileState, String>> statementValue(CompileState state, String input) {
        return or(state, input, Lists.of(Main::returns));
    }

    private static Option<Tuple<CompileState, String>> returns(CompileState state, String input) {
        return prefix(input.strip(), "return ", slice -> compileValue(state, slice).map(Tuple.mapRight(result -> "return " + result)));
    }

    private static Option<Tuple<CompileState, String>> compileValue(CompileState state, String input) {
        return or(state, input, Lists.of(
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        ));
    }

    private static Option<Tuple<CompileState, Definition>> definition(CompileState state, String input) {
        return split(input.strip(), new InfixSplitter(" ", Main::lastIndexOfSlice), (beforeName, name) -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            return Main.or(state, beforeName.strip(), Lists.of(
                    (instance, beforeName0) -> definitionWithTypeSeparator(instance, beforeName0, name),
                    (instance, beforeName0) -> definitionWithoutTypeSeparator(instance, beforeName0, name)
            ));
        });
    }

    private static boolean isSymbol(String value) {
        for (var i = 0; i < value.length(); i++) {
            var c = value.charAt(i);
            if (Character.isLetter(c) || c == '_') {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Option<Tuple<CompileState, Definition>> definitionWithoutTypeSeparator(CompileState state, String type, String name) {
        return type(state, type).flatMap(typeTuple -> {
            var definition = new Definition(new None<>(), typeTuple.right, name.strip());
            return new Some<>(new Tuple<>(typeTuple.left, definition));
        });
    }

    private static Option<Tuple<CompileState, Definition>> definitionWithTypeSeparator(CompileState state, String beforeName, String name) {
        return split(beforeName, new TypeSeparatorSplitter(), (beforeType, typeString) -> {
            return type(state, typeString).flatMap(typeTuple -> {
                return new Some<>(new Tuple<>(typeTuple.left, new Definition(new Some<>(beforeType), typeTuple.right, name.strip())));
            });
        });
    }

    private static Option<Tuple<CompileState, Type>> type(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                Main::primitive,
                Main::template,
                Main::typeParameter,
                Main::string,
                Main::structureType,
                wrap(Main::content)
        ));
    }

    private static Option<Tuple<CompileState, Type>> structureType(CompileState state, String input) {
        if (isSymbol(input)) {
            return new Some<>(new Tuple<>(state, new StructRef(input)));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, Type>> string(CompileState state, String input) {
        if (input.strip().equals("String")) {
            return new Some<>(new Tuple<>(state, new Ref(Primitive.I8)));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, Type>> typeParameter(CompileState state, String input) {
        if (state.maybeStructureType instanceof Some(var structureType)) {
            var stripped = input.strip();
            if (structureType.typeParams.contains(stripped)) {
                return new Some<>(new Tuple<>(state, new TypeParameter(stripped)));
            }
        }
        return new None<>();
    }

    private static <S, T extends S> BiFunction<CompileState, String, Option<Tuple<CompileState, S>>> wrap(BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> content) {
        return (state, input) -> content.apply(state, input).map(Tuple.mapRight(value -> value));
    }

    private static Option<Tuple<CompileState, Type>> primitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("boolean") || stripped.equals("int")) {
            return new Some<>(new Tuple<>(state, Primitive.I32));
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, Type>> template(CompileState state, String input) {
        return suffix(input.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (base, argumentsString) -> {
                return parseValues(state, argumentsString, Main::type).flatMap(argumentsTuple -> {
                    var arguments = argumentsTuple.right;

                    Type generated;
                    if (base.equals("Function")) {
                        generated = new Functional(Lists.of(arguments.get(0)), arguments.get(1));
                    }
                    else if (base.equals("Supplier")) {
                        generated = new Functional(Lists.empty(), arguments.get(0));
                    }
                    else if (base.equals("Tuple")) {
                        generated = new TupleType(arguments);
                    }
                    else {
                        generated = new Template(base, arguments);
                    }

                    return new Some<>(new Tuple<>(argumentsTuple.left, generated));
                });
            });
        });
    }

    private static Option<Integer> lastIndexOfSlice(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    }

    private static Option<Tuple<CompileState, String>> prefix(String input, String prefix, Function<String, Option<Tuple<CompileState, String>>> mapper) {
        if (!input.startsWith(prefix)) {
            return new None<>();
        }
        var slice = input.substring(prefix.length());
        return mapper.apply(slice);
    }

    private static <T> Option<T> suffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<>();
        }
        var slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }

    private static <T> Option<T> first(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return split(input, new InfixSplitter(infix, Main::firstIndexOfSlice), mapper);
    }

    private static <T> Option<T> split(String input, Splitter splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.split(input).flatMap(tuple -> {
            return mapper.apply(tuple.left, tuple.right);
        });
    }

    private static Option<Integer> firstIndexOfSlice(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    }
}
