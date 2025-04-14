package magma;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public sealed interface Option<T> permits Some, None {
        <R> Option<R> map(Function<T, R> mapper);

        T orElse(T other);

        boolean isPresent();

        Option<T> or(Supplier<Option<T>> other);

        T orElseGet(Supplier<T> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);
    }

    public interface List_<T> {
        T get(int index);

        int size();

        List_<T> add(T element);

        boolean isEmpty();

        Tuple<T, List_<T>> pop();

        Iterator<T> iter();

        List_<T> addAll(List_<T> elements);

        List_<T> sort(BiFunction<T, T, Integer> comparator);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface DivideState {
        DivideState popAndAppend();

        DivideState advance();

        DivideState append(char c);

        List_<String> segments();

        boolean isLevel();

        DivideState enter();

        DivideState exit();

        boolean isShallow();

        boolean hasNext();

        Tuple<Character, DivideState> pop();
    }

    private interface Head<T> {
        Option<T> next();
    }

    public interface String_ {
        char[] asCharArray();
    }

    private static class MutableDivideState implements DivideState {
        private final List_<Character> queue;
        private final List_<String> segments;
        private final StringBuilder buffer;
        private final int depth;

        private MutableDivideState(List_<Character> queue, List_<String> segments, StringBuilder buffer, int depth) {
            this.queue = queue;
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public MutableDivideState(List_<Character> queue) {
            this(queue, Lists.emptyList(), new StringBuilder(), 0);
        }

        @Override
        public DivideState advance() {
            return new MutableDivideState(this.queue, this.segments.add(this.buffer.toString()), new StringBuilder(), this.depth);
        }

        @Override
        public DivideState append(char c) {
            return new MutableDivideState(this.queue, this.segments, this.buffer.append(c), this.depth);
        }

        @Override
        public boolean isLevel() {
            return this.depth == 0;
        }

        @Override
        public DivideState enter() {
            return new MutableDivideState(this.queue, this.segments, this.buffer, this.depth + 1);
        }

        @Override
        public DivideState exit() {
            return new MutableDivideState(this.queue, this.segments, this.buffer, this.depth - 1);
        }

        @Override
        public boolean isShallow() {
            return this.depth == 1;
        }

        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override
        public Tuple<Character, DivideState> pop() {
            Tuple<Character, List_<Character>> popped = this.queue.pop();
            return new Tuple<>(popped.left, new MutableDivideState(popped.right, this.segments, this.buffer, this.depth));
        }

        @Override
        public DivideState popAndAppend() {
            Tuple<Character, DivideState> popped = this.pop();
            return popped.right.append(popped.left);
        }

        @Override
        public List_<String> segments() {
            return this.segments;
        }
    }

    private static final class Node {
        private final String_ beforeType;
        private final String_ type;
        private final String_ name;

        private Node(String_ beforeType, String_ type, String_ name) {
            this.beforeType = beforeType;
            this.type = type;
            this.name = name;
        }
    }

    public record Tuple<A, B>(A left, B right) {
        public static <A, B> boolean equalsTo(Tuple<A, B> first, Tuple<A, B> second, BiFunction<A, A, Boolean> firstComparator, BiFunction<B, B, Boolean> secondComparator) {
            return firstComparator.apply(first.left, second.left) && secondComparator.apply(first.right, second.right);
        }
    }

    private static class ListCollector<T> implements Collector<T, List_<T>> {
        @Override
        public List_<T> createInitial() {
            return Lists.emptyList();
        }

        @Override
        public List_<T> fold(List_<T> current, T element) {
            return current.add(element);
        }
    }

    public static final class None<T> implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public boolean isPresent() {
            return false;
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
        public boolean isPresent() {
            return true;
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
    }

    public record Iterator<T>(Head<T> head) {
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        public Iterator<T> filter(Predicate<T> predicate) {
            return this.flatMap(element -> new Iterator<>(predicate.test(element)
                    ? new SingleHead<>(element)
                    : new EmptyHead<T>()));
        }

        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Option<R> withNext = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (withNext.isPresent()) {
                    current = withNext.orElse(null);
                }
                else {
                    return current;
                }
            }
        }

        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> flatMap) {
            return this.map(flatMap).fold(Iterators.empty(), Iterator::concat);
        }

        public Iterator<T> concat(Iterator<T> other) {
            return new Iterator<>(() -> this.head.next().or(other::next));
        }

        public Option<T> next() {
            return this.head.next();
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> fromOption(Option<T> optional) {
            return new Iterator<>(optional.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
        }

        public static <T> Iterator<T> empty() {
            return new Iterator<>(new EmptyHead<>());
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

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    public static class RangeHead implements Head<Integer> {
        private final int length;
        private int index;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.index >= this.length) {
                return new None<>();
            }

            int value = this.index;
            this.index++;
            return new Some<>(value);

        }
    }

    private static final Map<String, Function<List_<String>, Option<String>>> expanding = new HashMap<>();
    private static final Map<String, String> structs = new HashMap<>();
    private static List_<String> dependencies = Lists.emptyList();
    private static Map<String, List_<String>> structDependencies = new HashMap<>();
    private static List_<Tuple<String, List_<String>>> toExpand = Lists.emptyList();
    private static List_<Tuple<String, List_<String>>> hasExpand = Lists.emptyList();

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        List_<String> compiled = compileStatementsToList(input, Main::compileRootSegment);
        expand(compiled);

        List_<String> orderedStructs = orderStructs();
        List_<String> collected = orderedStructs.iter()
                .map(struct -> structs.getOrDefault(struct, generatePlaceholder(struct)))
                .collect(new ListCollector<>());

        compiled.addAll(collected);

        return mergeStatements(compiled);
    }

    private static void expand(List_<String> compiled) {
        while (!toExpand.isEmpty()) {
            Tuple<Tuple<String, List_<String>>, List_<Tuple<String, List_<String>>>> popped = toExpand.pop();
            Tuple<String, List_<String>> entry = popped.left;
            toExpand = popped.right;
            hasExpand = hasExpand.add(entry);

            if (expanding.containsKey(entry.left)) {
                Option<String> expanded = expanding.get(entry.left).apply(entry.right);
                compiled.add(expanded.orElse(""));
            }
            else {
                compiled.add(generatePlaceholder(stringify(entry.left, entry.right)));
            }
        }
    }

    private static List_<String> orderStructs() {
        List_<String> orderedStructs = Lists.emptyList();
        while (!structDependencies.isEmpty()) {
            List_<String> toPrune = Lists.emptyList();
            for (Map.Entry<String, List_<String>> entry : structDependencies.entrySet()) {
                List_<String> dependencies = entry.getValue();
                if (dependencies.isEmpty()) {
                    toPrune.add(entry.getKey());
                }
            }

            if (toPrune.isEmpty()) {
                return Lists.fromNativeList(new ArrayList<>(structDependencies.entrySet()))
                        .iter()
                        .filter(entry -> !Lists.contains(orderedStructs, entry.getKey(), String::equals))
                        .collect(new ListCollector<>())
                        .sort((first, second) -> first.getValue().size() - second.getValue().size()).iter()
                        .map(Map.Entry::getKey)
                        .collect(new ListCollector<>());
            }

            orderedStructs.addAll(toPrune);
            structDependencies = structDependencies.entrySet().stream()
                    .filter(entry -> !Lists.contains(toPrune, entry.getKey(), String::equals))
                    .map(entry -> new Tuple<>(entry.getKey(), entry.getValue()))
                    .map(entry -> removeDependencies(entry, toPrune))
                    .collect(Collectors.toMap(Tuple::left, Tuple::right));
        }

        return orderedStructs;
    }

    private static Tuple<String, List_<String>> removeDependencies(Tuple<String, List_<String>> entry, List_<String> toPrune) {
        List_<String> newDependencies = entry.right.iter()
                .filter(dependency -> !Lists.contains(toPrune, dependency, String::equals))
                .collect(new ListCollector<>());

        return new Tuple<>(entry.left, newDependencies);
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        List_<String> compiled = compileStatementsToList(input, compiler);
        return mergeStatements(compiled);
    }

    private static String mergeStatements(List_<String> compiled) {
        return compiled.iter()
                .fold(new StringBuilder(), (buffer, segment) -> buffer.append(segment))
                .toString();
    }

    private static List_<String> compileStatementsToList(String input, Function<String, String> compiler) {
        List_<String> segments = divideStatements(input);
        return compileAll(segments, compiler);
    }

    private static List_<String> compileAll(List_<String> segments, Function<String, String> compiler) {
        return segments.iter()
                .map(compiler)
                .collect(new ListCollector<>());
    }

    private static String compileRootSegment(String input) {
        Option<String> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isPresent()) {
            return maybeWhitespace.orElse(null);
        }

        if (input.strip().startsWith("package ")) {
            return "";
        }

        if (input.strip().startsWith("import ")) {
            return "#include <temp.h>\n";
        }

        return compileClass(input.strip())
                .orElseGet(() -> generatePlaceholder(input.strip()));
    }

    private static Option<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return new Some<>("");
        }
        return new None<>();
    }

    private static Option<String> compileClass(String stripped) {
        return compileToStruct(stripped, "class ");
    }

    private static Option<String> compileToStruct(String stripped, String infix) {
        return compileInfix(stripped, infix, (_, right) -> {
            return compileInfix(right, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd, "}", content -> {
                    return compileInfix(beforeContent, " permits ", (left, permits) -> {
                        List_<String> permitted = Lists.fromNativeList(Arrays.stream(permits.split(" "))
                                .map(String::strip)
                                .filter(value -> !value.isEmpty())
                                .toList());

                        return getStringOption(content, left, permitted);
                    }).or(() -> {
                        return getStringOption(content, beforeContent, Lists.emptyList());
                    });
                });
            });
        });
    }

    private static Option<String> getStringOption(String content, String beforeContent1, List_<String> permits) {
        String withoutImplements = compileInfix(beforeContent1, " implements ", (left, _) -> new Some<>(left))
                .orElse(beforeContent1).strip();

        return compileInfix(withoutImplements, "(", (nameWithoutParams, withParamEnd) -> {
            return compileSuffix(withParamEnd.strip(), ")", paramString -> {
                List_<String> params = divide(paramString, Main::divideValueChar);
                String stripped1 = nameWithoutParams.strip();
                return getString(content, stripped1, params, permits);
            });
        }).or(() -> {
            String stripped1 = withoutImplements.strip();
            return getString(content, stripped1, Lists.emptyList(), permits);
        });
    }

    private static Option<String> getString(String s, String withoutParams, List_<String> params, List_<String> permits) {
        int typeParamStart = withoutParams.indexOf("<");
        if (typeParamStart >= 0) {
            String name = withoutParams.substring(0, typeParamStart).strip();
            List_<String> typeParams = Lists.fromNativeList(Arrays.stream(withoutParams.substring(typeParamStart + 1, withoutParams.length() - 1).strip().split(","))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList());

            if (!isSymbol(name)) {
                return new None<>();
            }

            expanding.put(name, argsInternal -> {
                String newName = stringify(name, argsInternal);
                String generated = generateStruct(newName, typeParams, argsInternal, params, s, permits);
                structs.put(newName, generated);
                return new Some<>("");
            });
        }
        else {
            if (!isSymbol(withoutParams)) {
                return new None<>();
            }

            String generated = generateStruct(withoutParams, Lists.emptyList(), Lists.emptyList(), params, s, permits);
            structs.put(withoutParams, generated);
        }

        return new Some<>("");
    }

    private static String stringify(String name, List_<String> args) {
        String joined = args.iter()
                .map(arg -> arg.replace("*", "_ref"))
                .collect(new Joiner("_"))
                .orElse("");

        return name + "_" + joined;
    }

    private static String generateStruct(String name, List_<String> typeParams, List_<String> typeArgs, List_<String> params, String body, List_<String> permits) {
        List_<String> compiled = compileAll(params, param -> {
            return compileDefinition(param, typeParams, typeArgs, Main::generateDefinition).orElse("");
        });

        String outputContent = compileStatements(body, classMember -> compileClassMember(classMember, typeParams, typeArgs));

        structDependencies.put(name, dependencies);
        dependencies = Lists.emptyList();

        String joined = compiled.iter()
                .collect(new Joiner())
                .orElse("");

        String content = joined + outputContent;
        if (permits.isEmpty()) {
            return generateStruct0(name, content);
        }

        String permitsString = permits.iter()
                .map(value -> "\n\t" + value)
                .collect(new Joiner(""))
                .orElse("");

        String enumType = name + "_type";
        String enums = "enum " + enumType + " {" +
                permitsString +
                "\n};\n";

        return enums + generateStruct0(name, "\n\t" + enumType + " __value__;" + content);
    }

    private static String generateStruct0(String name, String content) {
        return "typedef struct {" + content + "\n} " + name + ";\n";
    }

    private static Option<String> compileSuffix(String input, String suffix, Function<String, Option<String>> compiler) {
        if (!input.endsWith(suffix)) {
            return new None<>();
        }
        String slice = input.substring(0, input.length() - suffix.length());
        return compiler.apply(slice);
    }

    private static Option<String> compileInfix(String input, String infix, BiFunction<String, String, Option<String>> compiler) {
        return compileInfix(input, infix, Main::locateFirst, compiler);
    }

    private static Option<String> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Option<String>> compiler) {
        int index = locator.apply(input, infix);
        if (index < 0) {
            return new None<>();
        }
        String left = input.substring(0, index).strip();
        String right = input.substring(index + infix.length()).strip();
        return compiler.apply(left, right);
    }

    private static int locateFirst(String input, String infix) {
        return input.indexOf(infix);
    }

    private static String compileClassMember(String classMember, List_<String> typeParams, List_<String> typeArgs) {
        return compileWhitespace(classMember)
                .or(() -> compileToStruct(classMember, "interface"))
                .or(() -> compileToStruct(classMember, "class "))
                .or(() -> compileToStruct(classMember, "record "))
                .or(() -> compileMethod(classMember, typeParams, typeArgs))
                .or(() -> compileConstructor(classMember))
                .or(() -> compileDefinitionStatement(classMember, typeParams, typeArgs))
                .orElseGet(() -> generatePlaceholder(classMember));
    }

    private static Option<String> compileConstructor(String input) {
        return compileInfix(input, "(", (beforeParams, _) -> {
            return compileInfix(beforeParams.strip(), " ", String::lastIndexOf, (beforeName, name) -> {
                return new Some<>("");
            });
        });
    }

    private static Option<String> compileDefinitionStatement(String classMember, List_<String> typeParams, List_<String> typeArgs) {
        return compileSuffix(classMember, ";", inner -> {
            return compileDefinition(inner, typeParams, typeArgs, Main::generateDefinition);
        });
    }

    private static Option<String> generateDefinition(Node node) {
        return generateStatement(Strings.toSlice(node.beforeType) + Strings.toSlice(node.type) + " " + Strings.toSlice(node.name));
    }

    private static Option<String> compileMethod(String input, List_<String> typeParams, List_<String> typeArgs) {
        return compileInfix(input, "(", (definition, _) -> compileDefinition(definition, typeParams, typeArgs, Main::generateFunctionalDefinition));
    }

    private static Option<String> compileDefinition(String definition, List_<String> typeParams, List_<String> typeArgs, Function<Node, Option<String>> generator) {
        return compileInfix(definition.strip(), " ", String::lastIndexOf, (beforeName, name) -> {
            return compileInfix(beforeName.strip(), " ", (slice, _) -> locateTypeSeparator(slice), (beforeType, type) -> {
                return compileType(type, typeParams, typeArgs).flatMap(compiledType -> {
                    List_<String> modifiers = Lists.of(beforeType.strip().split(" "))
                            .iter()
                            .map(String::strip)
                            .filter(value -> !value.isEmpty())
                            .collect(new ListCollector<>());

                    if (Lists.contains(modifiers, "static", String::equals)) {
                        return new Some<>("");
                    }

                    return generator.apply(new Node(Strings.fromSlice(""), Strings.fromSlice(compiledType), Strings.fromSlice(name)));
                });
            }).or(() -> {
                return compileType(beforeName.strip(), typeParams, typeArgs).flatMap(compiledType -> {
                    return generator.apply(new Node(Strings.fromSlice(""), Strings.fromSlice(compiledType), Strings.fromSlice(name)));
                });
            });
        });
    }

    private static int locateTypeSeparator(String slice) {
        int depth = 0;
        for (int i = slice.length() - 1; i >= 0; i--) {
            char c = slice.charAt(i);
            if (c == ' ' && depth == 0) {
                return i;
            }
            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        }

        return -1;
    }

    private static Option<String> generateFunctionalDefinition(Node node) {
        return generateStatement(Strings.toSlice(node.beforeType) + Strings.toSlice(node.type) + " (*" + Strings.toSlice(node.name) + ")()");
    }

    private static Option<String> generateStatement(String content) {
        return new Some<>("\n\t" + content + ";");
    }

    private static Option<String> compileType(String input, List_<String> typeParams, List_<String> typeArgs) {
        String stripped = input.strip();
        if (stripped.equals("private") || stripped.equals("public")) {
            return new None<>();
        }

        if (stripped.equals("boolean") || stripped.equals("int") || stripped.equals("Boolean") || stripped.equals("Integer")) {
            return new Some<>("int");
        }

        if (stripped.equals("char") || stripped.equals("Character")) {
            return new Some<>("char");
        }

        if (stripped.equals("String")) {
            return new Some<>("char*");
        }

        if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length()), typeParams, typeArgs).map(result -> result + "*");
        }

        return compileDependentType(stripped, typeParams, typeArgs).map(inner -> {
            dependencies.add(inner);
            return inner;
        });
    }

    private static Option<String> compileDependentType(String stripped, List_<String> typeParams, List_<String> typeArgs) {
        Option<Integer> typeParamIndex = Lists.indexOf(typeParams, stripped, String::equals);
        if (typeParamIndex.isPresent()) {
            return new Some<>(typeArgs.get(typeParamIndex.orElse(null)));
        }

        if (isSymbol(stripped)) {
            return new Some<>(stripped);
        }

        return compileGenericType(typeParams, typeArgs, stripped)
                .or(() -> new Some<>(generatePlaceholder(stripped)));
    }

    private static Option<String> compileGenericType(List_<String> typeParams, List_<String> typeArgs, String stripped) {
        return compileSuffix(stripped, ">", withoutEnd -> {
            return compileInfix(withoutEnd, "<", (base, args) -> {
                String strippedBase = base.strip();
                if (!isSymbol(strippedBase)) {
                    return new None<>();
                }

                List_<String> list = divide(args, Main::divideValueChar)
                        .iter()
                        .map(input1 -> compileType(input1, typeParams, typeArgs))
                        .flatMap(Iterators::fromOption)
                        .collect(new ListCollector<>());

                if (!isDefined(strippedBase, list, toExpand) && !isDefined(strippedBase, list, hasExpand)) {
                    toExpand = toExpand.add(new Tuple<>(strippedBase, list));
                }
                return new Some<>(stringify(strippedBase, list));
            });
        });
    }

    private static DivideState divideValueChar(DivideState state, Character c) {
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

    private static boolean isDefined(String base, List_<String> args, List_<Tuple<String, List_<String>>> expansions1) {
        return Lists.contains(expansions1, new Tuple<>(base, args),
                (tuple0, tuple1) -> Tuple.equalsTo(tuple0, tuple1, String::equals,
                        (list1, list2) -> Lists.equalsTo(list1, list2, String::equals)));
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '_' || Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static List_<String> divideStatements(String input) {
        return divide(input, Main::divideStatementChar);
    }

    private static List_<String> divide(String input, BiFunction<DivideState, Character, DivideState> divideStatementChar) {
        List_<Character> queue = Lists.fromNativeList(IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .toList());

        DivideState current = new MutableDivideState(queue);
        while (current.hasNext()) {
            Tuple<Character, DivideState> tuple = current.pop();
            current = divideDecorated(tuple.right, tuple.left, divideStatementChar);
        }

        return current.advance().segments();
    }

    private static DivideState divideDecorated(DivideState current, char c, BiFunction<DivideState, Character, DivideState> divider) {
        return divideSingleQuotes(current, c)
                .or(() -> divideDoubleQuotes(current, c))
                .orElseGet(() -> divider.apply(current, c));
    }

    private static @NotNull Option<DivideState> divideDoubleQuotes(DivideState state, char c) {
        if (c != '\"') {
            return new None<>();
        }

        DivideState current = state.append(c);
        while (current.hasNext()) {
            Tuple<Character, DivideState> poppedTuple = current.pop();
            char popped = poppedTuple.left;
            current = poppedTuple.right.append(popped);

            if (popped == '\"') {
                break;
            }
            if (popped == '\\') {
                current = current.popAndAppend();
            }
        }

        return new Some<>(current);
    }

    private static Option<DivideState> divideSingleQuotes(DivideState current, char c) {
        if (c != '\'') {
            return new None<>();
        }

        DivideState appended = current.append('\'');

        Tuple<Character, DivideState> maybeSlash = appended.pop();
        DivideState withSlash = maybeSlash.right.append(maybeSlash.left);

        DivideState withEscape;
        if (maybeSlash.left == '\\') {
            withEscape = withSlash.popAndAppend();
        }
        else {
            withEscape = withSlash;
        }

        return new Some<>(withEscape.popAndAppend());
    }

    private static DivideState divideStatementChar(DivideState current, char c) {
        DivideState appended = current.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        else if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        else if (c == '{') {
            return appended.enter();
        }
        else if (c == '}') {
            return appended.exit();
        }
        else {
            return appended;
        }
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + "*/";
    }
}