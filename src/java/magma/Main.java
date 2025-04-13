package magma;

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

public class Main {
    private interface DivideState {
        DivideState popAndAppend();

        DivideState advance();

        DivideState append(char c);

        List<String> segments();

        boolean isLevel();

        DivideState enter();

        DivideState exit();

        boolean isShallow();

        boolean hasNext();

        char pop();
    }

    private static class MutableDivideState implements DivideState {
        private final Deque<Character> queue;
        private final List<String> segments;
        private int depth;
        private StringBuilder buffer;

        private MutableDivideState(Deque<Character> queue, List<String> segments, StringBuilder buffer, int depth) {
            this.queue = queue;
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public MutableDivideState(Deque<Character> queue) {
            this(queue, new ArrayList<>(), new StringBuilder(), 0);
        }

        @Override
        public DivideState advance() {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        @Override
        public DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        @Override
        public List<String> segments() {
            return this.segments;
        }

        @Override
        public boolean isLevel() {
            return this.depth == 0;
        }

        @Override
        public DivideState enter() {
            this.depth++;
            return this;
        }

        @Override
        public DivideState exit() {
            this.depth--;
            return this;
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
    }

    private record Node(String beforeType, String type, String name) {
    }

    private static final List<String> structs = new ArrayList<>();

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
            return compileInfix(right, "{", (name, withEnd) -> {
                return compileSuffix(withEnd, "}", s -> {
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
                .or(() -> compileMethod(classMember))
                .or(() -> compileDefinitionStatement(classMember))
                .orElseGet(() -> generatePlaceholder(classMember));
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
                return generator.apply(new Node(generatePlaceholder(beforeType) + " ", compileType(type), name));
            }).or(() -> {
                return generator.apply(new Node("", compileType(beforeName.strip()), name));
            });
        });
    }

    private static Optional<String> generateFunctionalDefinition(Node node) {
        return generateStatement(node.beforeType() + node.type() + " (*" + node.name() + ")()");
    }

    private static Optional<String> generateStatement(String content) {
        return Optional.of("\n\t" + content + ";");
    }

    private static String compileType(String input) {
        String stripped = input.strip();
        if (stripped.equals("boolean") || stripped.equals("int")) {
            return "int";
        }

        if (stripped.equals("char")) {
            return "char";
        }

        if (isSymbol(stripped)) {
            return "struct " + stripped;
        }

        return generatePlaceholder(stripped);
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
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

        return current.advance().segments();
    }

    private static DivideState divideDecorated(DivideState current, char c) {
        return divideSingleQuotes(current, c)
                .orElseGet(() -> divideStatementChar(current, c));
    }

    private static Optional<DivideState> divideSingleQuotes(DivideState current, char c) {
        if (c != '\'') {
            return Optional.empty();
        }

        current.append('\'');
        char maybeSlash = current.pop();
        current.append(maybeSlash);
        if (maybeSlash == '\\') {
            current.popAndAppend();
        }

        current.popAndAppend();
        return Optional.of(current);
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