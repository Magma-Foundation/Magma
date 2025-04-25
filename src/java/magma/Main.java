package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    private interface Type {
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

    private record Definition(Optional<String> maybeBeforeType, Type type, String name) {
        public Definition(Type type, String name) {
            this(Optional.empty(), type, name);
        }

        private String generate() {
            var beforeType = this.maybeBeforeType().map(inner -> inner + " ").orElse("");
            return beforeType + this.type().generate() + " " + this.name();
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

    private record Content(String input) implements Type {
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

    public static List<String> structs;
    private static List<String> functions;
    private static Optional<String> currentStructName;

    public static void main() {
        structs = new ArrayList<>();
        functions = new ArrayList<>();
        currentStructName = Optional.empty();

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
            return Optional.empty();
        }
        var afterKeyword = stripped.substring(classIndex + infix.length());
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }
        var beforeContent = afterKeyword.substring(0, contentStart).strip();

        var implementsIndex = beforeContent.indexOf(" implements ");
        var withoutImplements = implementsIndex >= 0
                ? beforeContent.substring(0, implementsIndex).strip()
                : beforeContent;

        var paramStart = withoutImplements.indexOf("(");
        var name = paramStart >= 0
                ? withoutImplements.substring(0, paramStart).strip()
                : withoutImplements;

        if (!isSymbol(name)) {
            return Optional.empty();
        }
        var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }
        var content = withEnd.substring(0, withEnd.length() - "}".length());

        currentStructName = Optional.of(name);
        var generated = "struct " + name + " {" +
                compileStatements(content, Main::compileClassSegment) +
                "\n};\n";
        structs.add(generated);
        return Optional.of("");
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
                .or(() -> compileClassStatement(input))
                .or(() -> compileMethod(input))
                .orElseGet(() -> "\n\t" + generatePlaceholder(input.strip()));
    }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return Optional.of("");
        }
        else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileMethod(String input) {
        var paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            var beforeParams = input.substring(0, paramStart).strip();
            var withParams = input.substring(paramStart + "(".length());

            var header = compileDefinition(beforeParams)
                    .or(() -> compileConstructorDefinition(beforeParams))
                    .orElseGet(() -> compileDefinitionOrPlaceholder(beforeParams));

            var paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                var params = withParams.substring(0, paramEnd).strip();
                var withBraces = withParams.substring(paramEnd + ")".length()).strip();
                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    var content = withBraces.substring(1, withBraces.length() - 1);
                    var constructor = header + "(" + compileValues(params, Main::compileDefinitionOrPlaceholder) + "){" + compileStatements(content, Main::compileStatementOrBlock) + "\n}\n";
                    functions.add(constructor);
                    return Optional.of("");
                }
            }
        }

        return Optional.empty();
    }

    private static Optional<String> compileConstructorDefinition(String beforeParams) {
        var nameSeparator = beforeParams.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var name = beforeParams.substring(nameSeparator + " ".length());
            if (currentStructName.isPresent() && currentStructName.get().equals(name)) {
                return Optional.of(new Definition(new Struct(name), "new_" + name).generate());
            }
        }
        return Optional.empty();
    }

    private static String compileStatementOrBlock(String input) {
        return compileWhitespace(input)
                .or(() -> compileStatement(input, Main::compileStatementValue))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static String compileStatementValue(String input) {
        return compileAssignable(input)
                .or(() -> compileDefinition(input))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileClassStatement(String input) {
        return compileStatement(input, Main::compileClassStatementValue);
    }

    private static Optional<String> compileStatement(String input, Function<String, String> compiler) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return Optional.of("\n\t" + compiler.apply(withoutEnd) + ";");
        }
        else {
            return Optional.empty();
        }
    }

    private static String compileClassStatementValue(String input) {
        return compileAssignable(input)
                .or(() -> compileDefinition(input))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileAssignable(String input) {
        var valueSeparator = input.indexOf("=");
        if (valueSeparator >= 0) {
            var inputDefinition = input.substring(0, valueSeparator);
            var value = input.substring(valueSeparator + "=".length());

            var destination = compileDefinition(inputDefinition).orElseGet(() -> compileValue(inputDefinition));
            return Optional.of(destination + " = " + compileValue(value));
        }
        return Optional.empty();
    }

    private static String compileValue(String input) {
        var stripped = input.strip();
        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            return compileValue(parent) + "." + property;
        }

        if (isSymbol(stripped)) {
            return stripped;
        }

        return generatePlaceholder(stripped);
    }

    private static String compileDefinitionOrPlaceholder(String input) {
        return compileDefinition(input).orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Optional.empty();
        }

        var beforeName = stripped.substring(0, nameSeparator).strip();
        var name = stripped.substring(nameSeparator + " ".length()).strip();

        var typeSeparator = beforeName.lastIndexOf(" ");
        if (typeSeparator < 0) {
            return parseType(beforeName).map(type -> new Definition(Optional.empty(), type, name).generate());
        }

        var beforeType = beforeName.substring(0, typeSeparator).strip();
        var inputType = beforeName.substring(typeSeparator + " ".length()).strip();
        return parseType(inputType).map(outputType -> new Definition(Optional.of(generatePlaceholder(beforeType)), outputType, name).generate());
    }

    private static Optional<String> compileType(String input) {
        return parseType(input).map(Type::generate);
    }

    private static Optional<Type> parseType(String input) {
        var stripped = input.strip();
        if (stripped.equals("private")) {
            return Optional.empty();
        }

        if (stripped.equals("int")) {
            return Optional.of(Primitive.I32);
        }

        if (stripped.equals("String")) {
            return Optional.of(new Ref(Primitive.I8));
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                var base = withoutEnd.substring(0, argsStart).strip();
                var argsString = withoutEnd.substring(argsStart + "<".length()).strip();
                var args = parseValues(argsString, input1 -> parseType(input1).orElseGet(() -> new Content(input1)));

                return Optional.of(new Generic(base, args));
            }
        }

        if (isSymbol(stripped)) {
            return Optional.of(new Struct(stripped));
        }

        return Optional.empty();
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
