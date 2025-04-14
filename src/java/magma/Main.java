package magma;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
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

        char pop();
    }

    private static class MutableDivideState implements DivideState {
        private final Deque<Character> queue;
        private final List_<String> segments;
        private final StringBuilder buffer;
        private final int depth;

        private MutableDivideState(Deque<Character> queue, List_<String> segments, StringBuilder buffer, int depth) {
            this.queue = queue;
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public MutableDivideState(Deque<Character> queue) {
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
        public char pop() {
            return this.queue.pop();
        }

        @Override
        public DivideState popAndAppend() {
            return this.append(this.pop());
        }

        @Override
        public List_<String> segments() {
            return this.segments;
        }
    }

    private record Node(String beforeType, String type, String name) {
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private static final List<String> structs = new ArrayList<>();
    private static final List<String> methods = new ArrayList<>();
    private static List_<Tuple<String, List_<String>>> expansions = Lists.emptyList();

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
        compiled.addAll(structs);
        return mergeStatements(compiled);
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

    private static ArrayList<String> compileStatementsToList(String input, Function<String, String> compiler) {
        List<String> segments = divide(input);
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

                    String withoutParams = compileInfix(withoutImplements, "(", (nameWithoutParams, withParamEnd) -> {
                        return compileSuffix(withParamEnd.strip(), ")", params -> {
                            return Optional.of(nameWithoutParams);
                        });
                    }).orElse(withoutImplements).strip();

                    int typeParamStart = withoutParams.indexOf("<");
                    String name = typeParamStart >= 0
                            ? withoutParams.substring(0, typeParamStart).strip()
                            : withoutParams;

                    if (!isSymbol(name)) {
                        return Optional.empty();
                    }

                    String outputContent = compileStatements(s, Main::compileClassMember);
                    String value = "struct " + name + " {" +
                            outputContent +
                            "\n};\n";
                    structs.add(value);
                    return Optional.of("");
                });
            });
        });
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

    private static String compileClassMember(String classMember) {
        return compileWhitespace(classMember)
                .or(() -> compileToStruct(classMember, "interface"))
                .or(() -> compileToStruct(classMember, "class "))
                .or(() -> compileToStruct(classMember, "record "))
                .or(() -> compileMethod(classMember))
                .or(() -> compileConstructor(classMember))
                .or(() -> compileDefinitionStatement(classMember))
                .orElseGet(() -> generatePlaceholder(classMember));
    }

    private static Optional<? extends String> compileConstructor(String input) {
        return compileInfix(input, "(", (beforeParams, _) -> {
            return compileInfix(beforeParams.strip(), " ", String::lastIndexOf, (beforeName, name) -> {
                return Optional.of("");
            });
        });
    }

    private static Optional<String> compileDefinitionStatement(String classMember) {
        return compileSuffix(classMember, ";", inner -> {
            return compileDefinition(inner, Main::generateDefinition);
        });
    }

    private static Optional<String> generateDefinition(Node node) {
        return generateStatement(node.beforeType() + node.type() + " " + node.name());
    }

    private static Optional<String> compileMethod(String input) {
        return compileInfix(input, "(", (definition, _) -> compileDefinition(definition, Main::generateFunctionalDefinition));
    }

    private static Optional<String> compileDefinition(String definition, Function<Node, Optional<String>> generator) {
        return compileInfix(definition.strip(), " ", String::lastIndexOf, (beforeName, name) -> {
            return compileInfix(beforeName.strip(), " ", String::lastIndexOf, (beforeType, type) -> {
                return compileType(type).flatMap(compiledType -> {
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
                return compileType(beforeName.strip()).flatMap(compiledType -> {
                    return generator.apply(new Node("", compiledType, name));
                });
            });
        });
    }

    private static Optional<String> generateFunctionalDefinition(Node node) {
        return generateStatement(node.beforeType() + node.type() + " (*" + node.name() + ")()");
    }

    private static Optional<String> generateStatement(String content) {
        return Optional.of("\n\t" + content + ";");
    }

    private static Optional<String> compileType(String input) {
        String stripped = input.strip();
        if (stripped.equals("private") || stripped.equals("public")) {
            return Optional.empty();
        }

        if (stripped.equals("boolean") || stripped.equals("int")) {
            return Optional.of("int");
        }

        if (stripped.equals("char")) {
            return Optional.of("char");
        }

        if (isSymbol(stripped)) {
            return Optional.of("struct " + stripped);
        }

        return compileSuffix(stripped, ">", withoutEnd -> {
            return compileInfix(withoutEnd, "<", (base, args) -> {
                expansions = expansions.add(new Tuple<>(base, Lists.of(args)));
                return Optional.of(base);
            });
        }).or(() -> Optional.of(generatePlaceholder(stripped)));
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

    private static List<String> divide(String input) {
        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        DivideState current = new MutableDivideState(queue);
        while (current.hasNext()) {
            char c = current.pop();
            current = divideDecorated(current, c);
        }

        return Lists.toNativeList(current.advance().segments());
    }

    private static DivideState divideDecorated(DivideState current, char c) {
        return divideSingleQuotes(current, c)
                .or(() -> divideDoubleQuotes(current, c))
                .orElseGet(() -> divideStatementChar(current, c));
    }

    private static @NotNull Optional<? extends DivideState> divideDoubleQuotes(DivideState state, char c) {
        if (c != '\"') {
            return Optional.empty();
        }

        DivideState current = state.append(c);
        while (current.hasNext()) {
            char popped = current.pop();
            current = current.append(popped);

            if (popped == '\"') {
                break;
            }
            if (popped == '\\') {
                current = current.append(current.pop());
            }
        }

        return Optional.of(current);
    }

    private static Optional<DivideState> divideSingleQuotes(DivideState current, char c) {
        if (c != '\'') {
            return Optional.empty();
        }

        DivideState appended = current.append('\'');

        char maybeSlash = appended.pop();
        DivideState withSlash = appended.append(maybeSlash);

        DivideState withEscape;
        if (maybeSlash == '\\') {
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