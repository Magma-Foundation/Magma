package magma;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public interface List_<T> {
        T get(int index);

        int size();

        List_<T> add(T element);

        boolean isEmpty();

        Tuple<T, List_<T>> pop();
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

    private record Node(String beforeType, String type, String name) {
    }

    public record Tuple<A, B>(A left, B right) {
        public static <A, B> boolean equalsTo(Tuple<A, B> first, Tuple<A, B> second, BiFunction<A, A, Boolean> firstComparator, BiFunction<B, B, Boolean> secondComparator) {
            return firstComparator.apply(first.left, second.left) && secondComparator.apply(first.right, second.right);
        }
    }

    private static final Map<String, Function<List_<String>, Optional<String>>> expanding = new HashMap<>();
    private static final Map<String, String> structs = new HashMap<>();
    private static final List<String> methods = new ArrayList<>();
    private static final List<String> stack = new ArrayList<>();
    private static final List<String> dependencies = new ArrayList<>();
    private static Map<String, List<String>> structDependencies = new HashMap<>();
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
        List<String> compiled = compileStatementsToList(input, Main::compileRootSegment);

        List<String> orderedStructs = new ArrayList<>();
        while (!structDependencies.isEmpty()) {
            List<String> toPrune = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : structDependencies.entrySet()) {
                List<String> dependencies = entry.getValue();
                if (dependencies.isEmpty()) {
                    toPrune.add(entry.getKey());
                }
            }

            if (toPrune.isEmpty()) {
                break;
            }

            orderedStructs.addAll(toPrune);
            structDependencies = structDependencies.entrySet().stream()
                    .map(entry -> new Tuple<>(entry.getKey(), entry.getValue()))
                    .map(entry -> removeDependencies(entry, toPrune))
                    .collect(Collectors.toMap(Tuple::left, Tuple::right));
        }

        List<String> collected = orderedStructs.stream()
                .map(structs::get)
                .toList();

        compiled.addAll(collected);

        while (!toExpand.isEmpty()) {
            Tuple<Tuple<String, List_<String>>, List_<Tuple<String, List_<String>>>> popped = toExpand.pop();
            Tuple<String, List_<String>> entry = popped.left;
            toExpand = popped.right;
            hasExpand = hasExpand.add(entry);

            if (expanding.containsKey(entry.left)) {
                Optional<String> expanded = expanding.get(entry.left).apply(entry.right);
                compiled.add(expanded.orElse(""));
            }
            else {
                compiled.add(generatePlaceholder(stringify(entry.left, entry.right)));
            }
        }

        return mergeStatements(compiled);
    }

    private static Tuple<String, List<String>> removeDependencies(Tuple<String, List<String>> entry, List<String> toPrune) {
        List<String> newDependencies = entry.right.stream()
                .filter(dependency -> !toPrune.contains(dependency))
                .toList();

        return new Tuple<>(entry.left, newDependencies);
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        List<String> compiled = compileStatementsToList(input, compiler);
        return mergeStatements(compiled);
    }

    private static String mergeStatements(List<String> compiled) {
        StringBuilder output = new StringBuilder();
        for (String s : compiled) {
            output.append(s);
        }

        return output.toString();
    }

    private static List<String> compileStatementsToList(String input, Function<String, String> compiler) {
        List<String> segments = divideStatements(input);
        return compileAll(segments, compiler);
    }

    private static ArrayList<String> compileAll(List<String> segments, Function<String, String> compiler) {
        ArrayList<String> compiled = new ArrayList<String>();
        for (String segment : segments) {
            compiled.add(compiler.apply(segment));
        }
        return compiled;
    }

    private static String compileRootSegment(String input) {
        Optional<String> maybeWhitespace = compileWhitespace(input);
        if (maybeWhitespace.isPresent()) {
            return maybeWhitespace.get();
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

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return Optional.of("");
        }
        return Optional.empty();
    }

    private static Optional<String> compileClass(String stripped) {
        return compileToStruct(stripped, "class ");
    }

    private static Optional<String> compileToStruct(String stripped, String infix) {
        return compileInfix(stripped, infix, (_, right) -> {
            return compileInfix(right, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd, "}", s -> {
                    String withoutImplements = compileInfix(beforeContent, " implements ", (left, _) -> Optional.of(left))
                            .orElse(beforeContent).strip();

                    return compileInfix(withoutImplements, "(", (nameWithoutParams, withParamEnd) -> {
                        return compileSuffix(withParamEnd.strip(), ")", paramString -> {
                            List<String> params = divide(paramString, Main::divideValueChar);
                            String stripped1 = nameWithoutParams.strip();
                            return getString(s, stripped1, params);
                        });
                    }).or(() -> {
                        String stripped1 = withoutImplements.strip();
                        return getString(s, stripped1, Collections.emptyList());
                    });
                });
            });
        });
    }

    private static Optional<String> getString(String s, String withoutParams, List<String> params) {
        int typeParamStart = withoutParams.indexOf("<");
        if (typeParamStart >= 0) {
            String name = withoutParams.substring(0, typeParamStart).strip();
            List_<String> typeParams = Lists.fromNativeList(Arrays.stream(withoutParams.substring(typeParamStart + 1, withoutParams.length() - 1).strip().split(","))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList());

            if (!isSymbol(name)) {
                return Optional.empty();
            }

            expanding.put(name, argsInternal -> {
                String newName = stringify(name, argsInternal);
                String generated = generateStruct(newName, typeParams, argsInternal, params, s);
                structs.put(withoutParams, generated);
                return Optional.of("");
            });
        }
        else {
            if (!isSymbol(withoutParams)) {
                return Optional.empty();
            }

            String generated = generateStruct(withoutParams, Lists.emptyList(), Lists.emptyList(), params, s);
            structs.put(withoutParams, generated);
        }

        return Optional.of("");
    }

    private static String stringify(String name, List_<String> args) {
        String joined = String.join("_", Lists.toNativeList(args));
        return name + "_" + joined;
    }

    private static String generateStruct(String name, List_<String> typeParams, List_<String> typeArgs, List<String> params, String body) {
        ArrayList<String> compiled = compileAll(params, param -> {
            return compileDefinition(param, typeParams, typeArgs, Main::generateDefinition).orElse("");
        });

        String outputContent = compileStatements(body, classMember -> compileClassMember(classMember, typeParams, typeArgs));

        structDependencies.put(name, new ArrayList<>(dependencies));
        dependencies.clear();

        return "typedef struct {" +
                String.join("", compiled) +
                outputContent +
                "\n} " +
                name +
                ";\n";
    }

    private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> compiler) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }
        String slice = input.substring(0, input.length() - suffix.length());
        return compiler.apply(slice);
    }

    private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<String>> compiler) {
        return compileInfix(input, infix, Main::locateFirst, compiler);
    }

    private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Optional<String>> compiler) {
        int index = locator.apply(input, infix);
        if (index < 0) {
            return Optional.empty();
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

    private static Optional<? extends String> compileConstructor(String input) {
        return compileInfix(input, "(", (beforeParams, _) -> {
            return compileInfix(beforeParams.strip(), " ", String::lastIndexOf, (beforeName, name) -> {
                return Optional.of("");
            });
        });
    }

    private static Optional<String> compileDefinitionStatement(String classMember, List_<String> typeParams, List_<String> typeArgs) {
        return compileSuffix(classMember, ";", inner -> {
            return compileDefinition(inner, typeParams, typeArgs, Main::generateDefinition);
        });
    }

    private static Optional<String> generateDefinition(Node node) {
        return generateStatement(node.beforeType() + node.type() + " " + node.name());
    }

    private static Optional<String> compileMethod(String input, List_<String> typeParams, List_<String> typeArgs) {
        return compileInfix(input, "(", (definition, _) -> compileDefinition(definition, typeParams, typeArgs, Main::generateFunctionalDefinition));
    }

    private static Optional<String> compileDefinition(String definition, List_<String> typeParams, List_<String> typeArgs, Function<Node, Optional<String>> generator) {
        return compileInfix(definition.strip(), " ", String::lastIndexOf, (beforeName, name) -> {
            return compileInfix(beforeName.strip(), " ", (slice, _) -> locateTypeSeparator(slice), (beforeType, type) -> {
                return compileType(type, typeParams, typeArgs).flatMap(compiledType -> {
                    List<String> modifiers = Stream.of(beforeType.strip().split(" "))
                            .map(String::strip)
                            .filter(value -> !value.isEmpty())
                            .toList();

                    if (modifiers.contains("static")) {
                        return Optional.of("");
                    }

                    return generator.apply(new Node("", compiledType, name));
                });
            }).or(() -> {
                return compileType(beforeName.strip(), typeParams, typeArgs).flatMap(compiledType -> {
                    return generator.apply(new Node("", compiledType, name));
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

    private static Optional<String> generateFunctionalDefinition(Node node) {
        return generateStatement(node.beforeType() + node.type() + " (*" + node.name() + ")()");
    }

    private static Optional<String> generateStatement(String content) {
        return Optional.of("\n\t" + content + ";");
    }

    private static Optional<String> compileType(String input, List_<String> typeParams, List_<String> typeArgs) {
        String stripped = input.strip();
        if (stripped.equals("private") || stripped.equals("public")) {
            return Optional.empty();
        }

        if (stripped.equals("boolean") || stripped.equals("int")) {
            return Optional.of("int");
        }

        if (stripped.equals("char") || stripped.equals("Character")) {
            return Optional.of("char");
        }

        dependencies.add(stripped);
        Optional<Integer> typeParamIndex = Lists.indexOf(typeParams, stripped, String::equals);
        if (typeParamIndex.isPresent()) {
            return Optional.of(typeArgs.get(typeParamIndex.get()));
        }

        if (isSymbol(stripped)) {
            return Optional.of(stripped);
        }

        return compileGenericType(typeParams, typeArgs, stripped)
                .or(() -> Optional.of(generatePlaceholder(stripped)));
    }

    private static Optional<String> compileGenericType(List_<String> typeParams, List_<String> typeArgs, String stripped) {
        return compileSuffix(stripped, ">", withoutEnd -> {
            return compileInfix(withoutEnd, "<", (base, args) -> {
                String strippedBase = base.strip();
                if (!isSymbol(strippedBase)) {
                    return Optional.empty();
                }

                List_<String> list = Lists.fromNativeList(divide(args, Main::divideValueChar)
                        .stream()
                        .map(input1 -> compileType(input1, typeParams, typeArgs))
                        .flatMap(Optional::stream)
                        .toList());

                if (!isDefined(strippedBase, list, toExpand) && !isDefined(strippedBase, list, hasExpand)) {
                    toExpand = toExpand.add(new Tuple<>(strippedBase, list));
                }
                return Optional.of(stringify(strippedBase, list));
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

    private static List<String> divideStatements(String input) {
        return divide(input, Main::divideStatementChar);
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> divideStatementChar) {
        List_<Character> queue = Lists.fromNativeList(IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .toList());

        DivideState current = new MutableDivideState(queue);
        while (current.hasNext()) {
            Tuple<Character, DivideState> tuple = current.pop();
            current = divideDecorated(tuple.right, tuple.left, divideStatementChar);
        }

        return Lists.toNativeList(current.advance().segments());
    }

    private static DivideState divideDecorated(DivideState current, char c, BiFunction<DivideState, Character, DivideState> divider) {
        return divideSingleQuotes(current, c)
                .or(() -> divideDoubleQuotes(current, c))
                .orElseGet(() -> divider.apply(current, c));
    }

    private static @NotNull Optional<? extends DivideState> divideDoubleQuotes(DivideState state, char c) {
        if (c != '\"') {
            return Optional.empty();
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

        return Optional.of(current);
    }

    private static Optional<DivideState> divideSingleQuotes(DivideState current, char c) {
        if (c != '\'') {
            return Optional.empty();
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

        return Optional.of(withEscape.popAndAppend());
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