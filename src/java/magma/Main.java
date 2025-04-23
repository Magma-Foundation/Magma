package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    sealed interface Node permits Definition, ConstructorHeader {
    }

    private static class State {
        private final List<String> segments;
        private String buffer;
        private int depth;

        private State(List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public static State createInitial() {
            return new State(new ArrayList<>(), "", 0);
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private void advance() {
            this.segments.add(this.buffer);
            this.buffer = "";
        }

        private void exit() {
            this.depth = this.depth - 1;
        }

        private void enter() {
            this.depth = this.depth + 1;
        }

        private void append(char c) {
            this.buffer = this.buffer + c;
        }
    }

    record Definition(List<String> modifiers, String value) implements Node {
    }

    record ConstructorHeader(String value) implements Node {
    }

    public static final List<String> structs = new ArrayList<>();
    private static final List<String> methods = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = Paths.get(".", "main.c");
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang", "-o", "main.exe", "main.c")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
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

        List<String> segments = state.segments;
        Optional<List<String>> maybeOutput = Optional.of(new ArrayList<String>());
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
            return Optional.of("");
        }

        return compileClass(input)
                .or(() -> Optional.of(generatePlaceholder(input.strip()) + "\n"));
    }

    private static Optional<String> compileClass(String input) {
        return compileStructured(input, "class ");
    }

    private static Optional<String> compileStructured(String input, String infix) {
        int classIndex = input.indexOf(infix);
        if (classIndex < 0) {
            return Optional.empty();
        }
        String afterKeyword = input.substring(classIndex + infix.length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }
        String beforeContent = afterKeyword.substring(0, contentStart).strip();

        int permitsIndex = beforeContent.indexOf(" permits ");
        String beforeContent1 = permitsIndex >= 0
                ? beforeContent.substring(0, permitsIndex).strip()
                : beforeContent;

        int paramStart = beforeContent1.indexOf("(");
        String name;
        List<String> recordParameters;
        if (paramStart >= 0) {
            name = beforeContent1.substring(0, paramStart).strip();
            String withEnd = beforeContent1.substring(paramStart + "(".length());
            int paramEnd = withEnd.indexOf(")");
            if (paramEnd >= 0) {
                String paramString = withEnd.substring(0, paramEnd).strip();
                recordParameters = parseParameters(paramString).orElse(Collections.emptyList());
            }
            else {
                name = beforeContent1;
                recordParameters = Collections.emptyList();
            }
        }
        else {
            name = beforeContent1;
            recordParameters = Collections.emptyList();
        }

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        String finalName = name;
        return compileAllStatements(inputContent, input1 -> compileClassSegment(input1, finalName).or(() -> Optional.of(generatePlaceholder(input1)))).flatMap(outputContent -> {
            if (!isSymbol(finalName)) {
                return Optional.empty();
            }

            String joined = recordParameters.stream()
                    .map(Main::formatStatement)
                    .collect(Collectors.joining());

            String generated = "struct " + finalName + " {" + joined + outputContent + "\n}\n";
            structs.add(generated);
            return Optional.of("");
        });
    }

    private static Optional<String> compileClassSegment(String input, String structName) {
        return compileWhitespace(input)
                .or(() -> compileClass(input))
                .or(() -> compileStructured(input, "interface "))
                .or(() -> compileStructured(input, "record "))
                .or(() -> compileMethod(input, structName))
                .or(() -> compileInitialization(input))
                .or(() -> compileDefinitionStatement(input))
                .or(() -> Optional.of(generatePlaceholder(input)));
    }

    private static Optional<String> compileInitialization(String input) {
        int valueSeparator = input.indexOf("=");
        if (valueSeparator < 0) {
            return Optional.empty();
        }

        return compileDefinition(input.substring(0, valueSeparator))
                .flatMap(Main::generateDefinition)
                .map(Main::formatStatement);
    }

    private static Optional<String> generateDefinition(Node node) {
        if (node instanceof Definition definition) {
            return Optional.of(definition.value);
        }
        else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return Optional.of("");
        }
        return Optional.empty();
    }

    private static Optional<String> compileDefinitionStatement(String input) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return Optional.empty();
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
            return Optional.empty();
        }

        String beforeParamsString = input.substring(0, paramStart).strip();
        return compileDefinition(beforeParamsString)
                .or(() -> compileConstructorHeader(structName, beforeParamsString))
                .flatMap(beforeName -> {
                    String withParams = input.substring(paramStart + "(".length());
                    int paramEnd = withParams.indexOf(")");
                    if (paramEnd < 0) {
                        return Optional.empty();
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
                                if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
                                    return Optional.empty();
                                }

                                String content = withBraces.substring(1, withBraces.length() - 1);
                                return parseStatements(content)
                                        .map(statements -> modifyMethodBody(structName, beforeName, statements))
                                        .map(Main::generateStatements).flatMap(outputContent -> {
                                            return compileMethodBeforeName(beforeName).flatMap(outputBeforeName -> {
                                                String generated = outputBeforeName + "(" + outputParams + "){" + outputContent + "\n}\n";
                                                methods.add(generated);
                                                return Optional.of("");
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
            return Optional.of(definition.value);
        }

        if (node instanceof ConstructorHeader(String value)) {
            return Optional.of(value);
        }

        return Optional.empty();
    }

    private static String generateParams(List<String> output) {
        return generateAll(Main::mergeValues, output);
    }

    private static Optional<String> compileParam(String param) {
        return compileWhitespace(param)
                .or(() -> compileDefinition(param).flatMap(Main::generateDefinition))
                .or(() -> Optional.of(generatePlaceholder(param)));
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
                return Optional.of(new ConstructorHeader(generated));
            }
        }
        return Optional.empty();
    }

    private static Optional<String> compileAllValues(String inputParams, Function<String, Optional<String>> compiler) {
        return compileAll(inputParams, Main::foldValueChar, compiler, Main::mergeValues);
    }

    private static State foldValueChar(State state, char c) {
        if (c == ',') {
            state.advance();
            return state;
        }

        state.append(c);
        if (c == '(') {
            state.enter();
        }
        else if (c == ')') {
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
            return Optional.empty();
        }
        String beforeName = stripped.substring(0, nameSeparator).strip();
        String oldName = stripped.substring(nameSeparator + " ".length()).strip();

        if (!isSymbol(oldName)) {
            return Optional.empty();
        }

        String newName;
        if (oldName.equals("main")) {
            newName = "__main__";
        }
        else {
            newName = oldName;
        }

        int typeSeparator = beforeName.lastIndexOf(" ");

        List<String> modifiers;
        String type;
        if (typeSeparator >= 0) {
            String modifiersString = beforeName.substring(0, typeSeparator).strip();
            type = beforeName.substring(typeSeparator + " ".length());

            modifiers = Arrays.asList(modifiersString.split(" "));
        }
        else {
            modifiers = Collections.emptyList();
            type = beforeName;
        }

        return compileType(type).flatMap(outputType -> {
            String outputDefinition = outputType + " " + newName;
            return Optional.of(new Definition(modifiers, outputDefinition));
        });
    }

    private static Optional<String> compileType(String type) {
        String stripped = type.strip();
        if (stripped.equals("private") || stripped.equals("public")) {
            return Optional.empty();
        }

        if (stripped.equals("void")) {
            return Optional.of("void");
        }

        if (stripped.equals("String")) {
            return Optional.of("char*");
        }

        if (stripped.equals("int") || stripped.equals("boolean")) {
            return Optional.of("int");
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
            return Optional.of("struct " + stripped);
        }

        return Optional.of(generatePlaceholder(stripped));
    }

    private static Optional<String> compileStatementOrBlock(String input) {
        return compileWhitespace(input)
                .or(() -> compileStatement(input))
                .or(() -> Optional.of("\n\t" + generatePlaceholder(input.strip())));
    }

    private static Optional<String> compileStatement(String input) {
        String stripped = input.strip();
        if (stripped.endsWith(";")) {
            String slice = stripped.substring(0, stripped.length() - ";".length());
            return compileStatementValue(slice).map(Main::formatStatement);
        }

        return Optional.empty();
    }

    private static Optional<String> compileStatementValue(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("return ")) {
            String slice = stripped.substring("return ".length());
            return Optional.of("return " + compileValue(slice));
        }

        Optional<String> maybeInvokable = compileInvokable(stripped);
        if (maybeInvokable.isPresent()) {
            return maybeInvokable;
        }

        int valueSeparator = stripped.indexOf("=");
        if (valueSeparator >= 0) {
            String destination = stripped.substring(0, valueSeparator).strip();
            String source = stripped.substring(valueSeparator + "=".length()).strip();

            return Optional.of(compileValue(destination) + " = " + compileValue(source));
        }

        return Optional.of(generatePlaceholder(stripped));
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
            return maybeInvokable.get();
        }

        int equalsIndex = stripped.indexOf("==");
        if (equalsIndex >= 0) {
            String left = stripped.substring(0, equalsIndex);
            String right = stripped.substring(equalsIndex + "==".length());
            return compileValue(left) + " == " + compileValue(right);
        }

        int lastSeparator = stripped.lastIndexOf(".");
        if (lastSeparator >= 0) {
            String parent = stripped.substring(0, lastSeparator);
            String property = stripped.substring(lastSeparator + ".".length());
            if (isSymbol(property)) {
                return compileValue(parent) + "." + property;
            }
        }

        return generatePlaceholder(stripped);
    }

    private static Optional<String> compileInvokable(String input) {
        String stripped = input.strip();
        if (stripped.endsWith(")")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ")".length());
            int argumentStart = withoutEnd.indexOf("(");
            if (argumentStart >= 0) {
                String beforeArguments = withoutEnd.substring(0, argumentStart).strip();
                String arguments = withoutEnd.substring(argumentStart + "(".length()).strip();
                Optional<String> maybeNewArguments = compileAllValues(arguments, argument -> Optional.of(compileValue(argument)));
                if (maybeNewArguments.isPresent()) {
                    String newArguments = maybeNewArguments.get();
                    if (beforeArguments.startsWith("new ")) {
                        String type = beforeArguments.substring("new ".length()).strip();
                        String caller = "new_" + type;
                        return generateInvocation(caller, newArguments);
                    }
                    else {
                        String caller = compileValue(beforeArguments);
                        return generateInvocation(caller, newArguments);
                    }
                }
            }
        }
        return Optional.empty();
    }

    private static Optional<String> generateInvocation(String caller, String arguments) {
        return Optional.of(caller + "(" + arguments + ")");
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
            if (Character.isLetter(c)) {
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
