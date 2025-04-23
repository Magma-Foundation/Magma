package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static magma.StandardLibrary.emptyString;

public class Main {
    sealed interface Optional<T> permits Some, None {
        void ifPresent(Consumer<T> consumer);

        T orElse(T other);

        <R> Optional<R> map(Function<T, R> mapper);

        <R> Optional<R> flatMap(Function<T, Optional<R>> mapper);

        Optional<T> or(Supplier<Optional<T>> other);

        boolean isPresent();

        T orElseGet(Supplier<T> other);

        boolean isEmpty();
    }

    sealed interface Node permits Definition, ConstructorHeader {
    }

    public interface String_ {
        String_ concatChar(char c);

        String toSlice();
    }

    sealed interface Result<T, X> permits Ok, Err {
    }

    private static class State {
        private final List<String_> segments;
        private String_ buffer;
        private int depth;

        private State(List<String_> segments, String_ buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public static State createInitial() {
            return new State(new ArrayList<>(), emptyString(), 0);
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private void advance() {
            this.segments.add(this.buffer);
            this.buffer = emptyString();
        }

        private void exit() {
            this.depth = this.depth - 1;
        }

        private void enter() {
            this.depth = this.depth + 1;
        }

        private void append(char c) {
            this.buffer = this.buffer.concatChar(c);
        }
    }

    record Definition(List<String> modifiers, String value) implements Node {
    }

    record ConstructorHeader(String value) implements Node {
    }

    record Ok<T, X>(T value) implements Result<T, X> {
    }

    record Err<T, X>(X error) implements Result<T, X> {
    }

    private record Some<T>(T value) implements Optional<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public <R> Optional<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public <R> Optional<R> flatMap(Function<T, Optional<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public Optional<T> or(Supplier<Optional<T>> other) {
            return this;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    private static final class None<T> implements Optional<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public <R> Optional<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public <R> Optional<R> flatMap(Function<T, Optional<R>> mapper) {
            return new None<>();
        }

        @Override
        public Optional<T> or(Supplier<Optional<T>> other) {
            return other.get();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other.get();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }

    public static final List<String> structs = new ArrayList<>();
    public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java");
    public static final Path TARGET = Paths.get(".", "main.c");
    private static final List<String> methods = new ArrayList<>();

    public static void main(String[] args) {
        Optional<IOException> result = switch (readString(SOURCE)) {
            case Err<String, IOException>(IOException error) -> new Some<>(error);
            case Ok<String, IOException>(String value) -> {
                String output = compile(value);
                yield switch (writeString(TARGET, output)) {
                    case None<IOException> _ -> build();
                    case Some<IOException>(IOException error) -> new Some<>(error);
                };
            }
        };

        result.ifPresent(Throwable::printStackTrace);
    }

    private static Optional<IOException> build() {
        try {
            new ProcessBuilder("clang", "-o", "main.exe", "main.c")
                    .inheritIO()
                    .start()
                    .waitFor();
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        } catch (InterruptedException e) {
            return new Some<>(new IOException(e));
        }
    }

    private static Optional<IOException> writeString(Path target, String output) {
        try {
            Files.writeString(target, output);
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

    private static String compile(String input) {
        String output = compileAllStatements(input, Main::compileRootSegment).orElse("");
        String joinedStructs = String.join("", structs);
        String joinedMethods = String.join("", methods);
        return output + joinedStructs + joinedMethods;
    }

    private static Optional<String> compileAllStatements(String input, Function<String, Optional<String>> compiler) {
        return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements);
    }

    private static Optional<String> compileAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, Optional<String>> compiler,
            BiFunction<String, String, String> merger
    ) {
        return parseAll(input, folder, compiler)
                .map(output -> generateAll(merger, output));
    }

    private static Optional<List<String>> parseAll(String input, BiFunction<State, Character, State> folder, Function<String, Optional<String>> compiler) {
        State state = State.createInitial();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\'') {
                state.append(c);

                i++;
                char c1 = input.charAt(i);
                state.append(c1);

                if (c1 == '\\') {
                    i++;
                    state.append(input.charAt(i));
                }

                i++;
                state.append(input.charAt(i));

                continue;
            }

            state = folder.apply(state, c);
        }
        state.advance();

        List<String> segments = state.segments
                .stream()
                .map(string -> string.toSlice())
                .toList();

        Optional<List<String>> maybeOutput = new Some<>(new ArrayList<String>());
        for (String segment : segments) {
            Optional<String> maybeCompiled = compiler.apply(segment);
            maybeOutput = maybeOutput.flatMap(output -> {
                return maybeCompiled.map(compiled -> {
                    output.add(compiled);
                    return output;
                });
            });
        }
        return maybeOutput;
    }

    private static String generateAll(BiFunction<String, String, String> merger, List<String> output) {
        String cache = "";
        for (String element : output) {
            cache = merger.apply(cache, element);
        }
        return cache;
    }

    private static String mergeStatements(String output, String compiled) {
        return output + compiled;
    }

    private static State foldStatementChar(State state, char c) {
        state.append(c);
        if (c == ';' && state.isLevel()) {
            state.advance();
        }
        else if (c == '}' && state.isShallow()) {
            state.advance();
            state.exit();
        }
        else {
            if (c == '{') {
                state.enter();
            }
            if (c == '}') {
                state.exit();
            }
        }
        return state;
    }

    private static Optional<String> compileRootSegment(String input) {
        if (input.isBlank()) {
            return new Some<>("");
        }

        return compileClass(input)
                .or(() -> new Some<>(generatePlaceholder(input.strip()) + "\n"));
    }

    private static Optional<String> compileClass(String input) {
        return compileStructured(input, "class ");
    }

    private static Optional<String> compileStructured(String input, String infix) {
        int classIndex = input.indexOf(infix);
        if (classIndex < 0) {
            return new None<>();
        }
        String afterKeyword = input.substring(classIndex + infix.length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }
        String beforeContent = afterKeyword.substring(0, contentStart).strip();

        int permitsIndex = beforeContent.indexOf(" permits ");
        String beforeContent1 = permitsIndex >= 0
                ? beforeContent.substring(0, permitsIndex).strip()
                : beforeContent;

        int paramStart = beforeContent1.indexOf("(");
        String withoutParams;
        List<String> recordParameters;
        if (paramStart >= 0) {
            withoutParams = beforeContent1.substring(0, paramStart).strip();
            String withEnd = beforeContent1.substring(paramStart + "(".length());
            int paramEnd = withEnd.indexOf(")");
            if (paramEnd >= 0) {
                String paramString = withEnd.substring(0, paramEnd).strip();
                recordParameters = parseParameters(paramString).orElse(Collections.emptyList());
            }
            else {
                withoutParams = beforeContent1;
                recordParameters = Collections.emptyList();
            }
        }
        else {
            withoutParams = beforeContent1;
            recordParameters = Collections.emptyList();
        }

        int typeParamStart = withoutParams.indexOf("<");
        String name = typeParamStart >= 0
                ? withoutParams.substring(0, typeParamStart).strip()
                : withoutParams;

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        return compileAllStatements(inputContent, input1 -> compileClassSegment(input1, name).or(() -> new Some<>(generatePlaceholder(input1)))).flatMap(outputContent -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            String joined = recordParameters.stream()
                    .map(Main::formatStatement)
                    .collect(Collectors.joining());

            String generated = "struct " + name + " {" + joined + outputContent + "\n};\n";
            structs.add(generated);
            return new Some<>("");
        });
    }

    private static Optional<String> compileClassSegment(String input, String structName) {
        return compileWhitespace(input)
                .or(() -> compileClass(input))
                .or(() -> compileStructured(input, "interface "))
                .or(() -> compileStructured(input, "record "))
                .or(() -> compileMethod(input, structName))
                .or(() -> compileStatement(input, (value) -> compileDefinition(value).flatMap(Main::generateDefinition).or(() -> compileAssigner(value))))
                .or(() -> new Some<>(generatePlaceholder(input)));
    }

    private static Optional<String> generateDefinition(Node node) {
        if (node instanceof Definition definition) {
            return new Some<>(definition.value);
        }
        else {
            return new None<>();
        }
    }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return new Some<>("");
        }
        return new None<>();
    }

    private static Optional<String> compileDefinitionStatement(String input) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return new None<>();
        }

        String slice = stripped.substring(0, stripped.length() - ";".length());
        return compileDefinition(slice)
                .flatMap(Main::generateDefinition)
                .map(Main::formatStatement);
    }

    private static String formatStatement(String inner) {
        return "\n\t" + inner + ";";
    }

    private static Optional<String> compileMethod(String input, String structName) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return new None<>();
        }

        String beforeParamsString = input.substring(0, paramStart).strip();
        return compileDefinition(beforeParamsString)
                .or(() -> compileConstructorHeader(structName, beforeParamsString))
                .flatMap(beforeName -> {
                    String withParams = input.substring(paramStart + "(".length());
                    int paramEnd = withParams.indexOf(")");
                    if (paramEnd < 0) {
                        return new None<>();
                    }

                    String inputParams = withParams.substring(0, paramEnd).strip();
                    return parseParameters(inputParams)
                            .map(params -> {
                                if (beforeName instanceof Definition definition) {
                                    if (!definition.modifiers.contains("static")) {
                                        ArrayList<String> copy = new ArrayList<>();
                                        copy.add("struct " + structName + " this");

                                        params.forEach(param -> {
                                            if (!param.isEmpty()) {
                                                copy.add(param);
                                            }
                                        });
                                        return copy;
                                    }
                                }
                                return params;

                            })
                            .map(Main::generateParams).flatMap(outputParams -> {
                                String withBraces = withParams.substring(paramEnd + ")".length()).strip();

                                return compileMethodBeforeName(beforeName).flatMap(outputBeforeName -> {
                                    String header = outputBeforeName + "(" + outputParams + ")";
                                    if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
                                        return new Some<>(formatStatement(header));
                                    }

                                    String content = withBraces.substring(1, withBraces.length() - 1);
                                    return parseStatements(content)
                                            .map(statements -> modifyMethodBody(structName, beforeName, statements))
                                            .map(Main::generateStatements).flatMap(outputContent -> {
                                                String outputBody = "{" + outputContent + "\n}\n";
                                                String generated = header + outputBody;
                                                methods.add(generated);
                                                return new Some<>("");
                                            });
                                });
                            });
                });
    }

    private static Optional<List<String>> parseParameters(String inputParams) {
        return parseAllValues(inputParams, Main::compileParam);
    }

    private static Optional<String> compileMethodBeforeName(Node node) {
        if (node instanceof Definition definition) {
            return new Some<>(definition.value);
        }

        if (node instanceof ConstructorHeader(String value)) {
            return new Some<>(value);
        }

        return new None<>();
    }

    private static String generateParams(List<String> output) {
        return generateAll(Main::mergeValues, output);
    }

    private static Optional<String> compileParam(String param) {
        return compileWhitespace(param)
                .or(() -> compileDefinition(param).flatMap(Main::generateDefinition))
                .or(() -> new Some<>(generatePlaceholder(param)));
    }

    private static Optional<List<String>> parseAllValues(String inputParams, Function<String, Optional<String>> compiler) {
        return parseAll(inputParams, Main::foldValueChar, compiler);
    }

    private static List<String> modifyMethodBody(String structName, Node beforeName, List<String> statements) {
        if (beforeName instanceof ConstructorHeader) {
            List<String> copy = new ArrayList<>();
            copy.add(formatStatement("struct " + structName + " this"));
            copy.addAll(statements);
            copy.add(formatStatement("return this"));
            return copy;
        }
        else {
            return statements;
        }
    }

    private static String generateStatements(List<String> output) {
        return generateAll(Main::mergeStatements, output);
    }

    private static Optional<List<String>> parseStatements(String content) {
        return parseAll(content, Main::foldStatementChar, Main::compileStatementOrBlock);
    }

    private static Optional<Node> compileConstructorHeader(String structName, String definition) {
        String stripped0 = definition.strip();
        int index = stripped0.lastIndexOf(" ");
        if (index >= 0) {
            String constructorName = stripped0.substring(index + " ".length());
            if (constructorName.equals(structName)) {
                String generated = "struct " + structName + " new_" + structName;
                return new Some<>(new ConstructorHeader(generated));
            }
        }
        return new None<>();
    }

    private static Optional<String> compileAllValues(String inputParams, Function<String, Optional<String>> compiler) {
        return compileAll(inputParams, Main::foldValueChar, compiler, Main::mergeValues);
    }

    private static State foldValueChar(State state, char c) {
        if (c == ',' && state.isLevel()) {
            state.advance();
            return state;
        }

        state.append(c);
        if (c == '(' || c == '<') {
            state.enter();
        }
        else if (c == ')' || c == '>') {
            state.exit();
        }
        return state;
    }

    private static String mergeValues(String cache, String element) {
        if (cache.isEmpty()) {
            return element;
        }
        return cache + ", " + element;
    }

    private static Optional<Node> compileDefinition(String input) {
        String stripped = input.strip();
        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }
        String beforeName = stripped.substring(0, nameSeparator).strip();
        String oldName = stripped.substring(nameSeparator + " ".length()).strip();

        if (!isSymbol(oldName)) {
            return new None<>();
        }

        String newName;
        if (oldName.equals("main")) {
            newName = "__main__";
        }
        else {
            newName = oldName;
        }

        List<String> modifiers;
        String type;
        switch (findTypeSeparator(beforeName)) {
            case None<Integer> _ -> {
                modifiers = Collections.emptyList();
                type = beforeName;
            }
            case Some<Integer>(int typeSeparator) -> {
                String modifiersString = beforeName.substring(0, typeSeparator).strip();
                modifiers = Arrays.asList(modifiersString.split(" "));
                type = beforeName.substring(typeSeparator + " ".length());
            }
        }

        return compileType(type).map(outputType -> {
            String outputDefinition = outputType + " " + newName;
            return new Definition(modifiers, outputDefinition);
        });
    }

    private static Optional<Integer> findTypeSeparator(String input) {
        int depth = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            if (c == ' ' && depth == 0) {
                return new Some<>(i);
            }
            else if (c == '>') {
                depth++;
            }
            else if (c == '<') {
                depth--;
            }
        }
        return new None<>();
    }

    private static Optional<String> compileType(String type) {
        String stripped = type.strip();
        if (stripped.equals("private") || stripped.equals("public")) {
            return new None<>();
        }

        if (stripped.equals("void")) {
            return new Some<>("void");
        }

        if (stripped.equals("String")) {
            return new Some<>("char*");
        }

        if (stripped.equals("char")) {
            return new Some<>("char");
        }

        if (stripped.equals("int") || stripped.equals("boolean")) {
            return new Some<>("int");
        }

        if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length())).map(result -> result + "*");
        }

        if (stripped.endsWith(">")) {
            String slice = stripped.substring(0, stripped.length() - ">".length());
            int typeArgsStart = slice.indexOf("<");
            if (typeArgsStart >= 0) {
                String base = slice.substring(0, typeArgsStart).strip();
                String arguments = slice.substring(typeArgsStart + "<".length()).strip();
                return compileAllValues(arguments, Main::compileType).map(newArguments -> base + "<" + newArguments + ">");
            }
        }

        if (isSymbol(stripped)) {
            return new Some<>("struct " + stripped);
        }

        return new Some<>(generatePlaceholder(stripped));
    }

    private static Optional<String> compileStatementOrBlock(String input) {
        return compileWhitespace(input)
                .or(() -> compileStatement(input, Main::compileStatementValue))
                .or(() -> new Some<>("\n\t" + generatePlaceholder(input.strip())));
    }

    private static Optional<String> compileStatement(String input, Function<String, Optional<String>> compileStatementValue) {
        String stripped = input.strip();
        if (stripped.endsWith(";")) {
            String slice = stripped.substring(0, stripped.length() - ";".length());
            return compileStatementValue.apply(slice).map(Main::formatStatement);
        }

        return new None<>();
    }

    private static Optional<String> compileStatementValue(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("return ")) {
            String slice = stripped.substring("return ".length());
            return new Some<>("return " + compileValue(slice));
        }

        Optional<String> destination = compileAssigner(stripped);
        if (destination.isPresent()) {
            return destination;
        }

        Optional<String> maybeInvokable = compileInvokable(stripped);
        if (maybeInvokable.isPresent()) {
            return maybeInvokable;
        }

        return new Some<>(generatePlaceholder(stripped));
    }

    private static Optional<String> compileAssigner(String stripped) {
        int valueSeparator = stripped.indexOf("=");
        if (valueSeparator < 0) {
            return new None<>();
        }
        String destination = stripped.substring(0, valueSeparator).strip();
        String source = stripped.substring(valueSeparator + "=".length()).strip();
        String newDestination = compileDefinition(destination)
                .flatMap(Main::generateDefinition)
                .orElseGet(() -> compileValue(destination));

        return new Some<>(newDestination + " = " + compileValue(source));
    }

    private static String compileValue(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return stripped;
        }

        if (isNumber(stripped)) {
            return stripped;
        }

        if (isSymbol(stripped)) {
            return stripped;
        }

        Optional<String> maybeInvokable = compileInvokable(stripped);
        if (maybeInvokable.isPresent()) {
            return maybeInvokable.orElse(null);
        }

        int lastSeparator = stripped.lastIndexOf(".");
        if (lastSeparator >= 0) {
            String parent = stripped.substring(0, lastSeparator);
            String property = stripped.substring(lastSeparator + ".".length());
            if (isSymbol(property)) {
                return compileValue(parent) + "." + property;
            }
        }

        return compileOperator(stripped, "==")
                .or(() -> compileOperator(stripped, "+"))
                .or(() -> compileOperator(stripped, "-"))
                .orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileOperator(String input, String operator) {
        int equalsIndex = input.indexOf(operator);
        if (equalsIndex < 0) {
            return new None<>();
        }
        String left = input.substring(0, equalsIndex);
        String right = input.substring(equalsIndex + operator.length());
        return new Some<>(compileValue(left) + " " + operator + " " + compileValue(right));
    }

    private static Optional<String> compileInvokable(String input) {
        String stripped = input.strip();
        if (!stripped.endsWith(")")) {
            return new None<>();
        }

        String withoutEnd = stripped.substring(0, stripped.length() - ")".length());
        int argumentStart = withoutEnd.indexOf("(");
        if (argumentStart < 0) {
            return new None<>();
        }

        String beforeArguments = withoutEnd.substring(0, argumentStart).strip();
        String arguments = withoutEnd.substring(argumentStart + "(".length()).strip();
        Optional<String> maybeNewArguments = compileAllValues(arguments, argument -> new Some<>(compileValue(argument)));
        if (maybeNewArguments.isEmpty()) {
            return new None<>();
        }

        String newArguments = maybeNewArguments.orElse(null);
        if (!beforeArguments.startsWith("new ")) {
            String caller = compileValue(beforeArguments);
            return generateInvocation(caller, newArguments);
        }

        String type = beforeArguments.substring("new ".length()).strip();
        String caller = "new_" + type;
        return generateInvocation(caller, newArguments);
    }

    private static Optional<String> generateInvocation(String caller, String arguments) {
        return new Some<>(caller + "(" + arguments + ")");
    }

    private static boolean isNumber(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c) || c == '_') {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
