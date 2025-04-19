package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class Main {
    private static class DivideState {
        private final JavaList<String> segments;
        private final String buffer;
        private final int depth;

        private DivideState(JavaList<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(new JavaList<>(), "", 0);
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private DivideState append(char c) {
            return new DivideState(this.segments, this.buffer + c, this.depth);
        }

        private DivideState advance() {
            return new DivideState(this.segments.addLast(this.buffer), "", this.depth);
        }

        private DivideState enter() {
            return new DivideState(this.segments, this.buffer, this.depth + 1);
        }

        private DivideState exit() {
            return new DivideState(this.segments, this.buffer, this.depth - 1);
        }
    }

    private record JavaList<T>(List<T> list) {
        public JavaList() {
            this(new ArrayList<>());
        }

        public JavaList<T> addLast(T element) {
            ArrayList<T> copy = new ArrayList<>(this.list);
            copy.add(element);
            return new JavaList<>(copy);
        }

        public JavaList<T> removeLast() {
            return new JavaList<>(this.list.subList(0, this.list.size() - 1));
        }

        public T last() {
            return this.list.getLast();
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    record CompileState(JavaList<String> structs, JavaList<String> methods, JavaList<String> structNames) {
        public CompileState() {
            this(new JavaList<>(), new JavaList<>(), new JavaList<>());
        }

        public CompileState addMethod(String method) {
            return new CompileState(this.structs, this.methods.addLast(method), this.structNames);
        }

        public CompileState addStruct(String struct) {
            return new CompileState(this.structs.addLast(struct), this.methods, this.structNames);
        }

        public CompileState pushStructName(String structName) {
            return new CompileState(this.structs, this.methods, this.structNames.addLast(structName));
        }

        public CompileState popStructName() {
            return new CompileState(this.structs, this.methods, this.structNames.removeLast());
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path output = source.resolveSibling("main.c");
            Files.writeString(output, compile(input));

            new ProcessBuilder("cmd.exe", "/c", "build.bat")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        Tuple<CompileState, String> compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        CompileState newState = compiled.left;
        String output = compiled.right;
        String joinedStructs = String.join("", newState.structs.list);
        String joinedMethods = String.join("", newState.methods.list);
        String joined = output + joinedStructs + joinedMethods;

        return joined + "int main(){\n\treturn 0;\n}\n";
    }

    private static Tuple<CompileState, String> compileStatements(
            CompileState methods,
            String input,
            BiFunction<CompileState, String, Tuple<CompileState, String>> compiler
    ) {
        return compileAll(methods, input, Main::foldStatementChar, compiler, Main::mergeStatements);
    }

    private static Tuple<CompileState, String> compileAll(CompileState methods, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler, BiFunction<String, String, String> merger) {
        List<String> segments = divide(input, folder);
        CompileState current = methods;
        String output = "";
        for (String segment : segments) {
            Tuple<CompileState, String> compiled = compiler.apply(current, segment);
            current = compiled.left;
            output = merger.apply(output, compiled.right);
        }

        return new Tuple<>(current, output);
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        DivideState current = new DivideState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments.list;
    }

    private static String mergeStatements(String buffer, String element) {
        return buffer + element;
    }

    private static DivideState foldStatementChar(DivideState current, char c) {
        DivideState appended = current.append(c);
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

    private static Tuple<CompileState, String> compileRootSegment(CompileState methods, String input) {
        String stripped = input.strip();
        if (stripped.startsWith("package ")) {
            return new Tuple<>(methods, "");
        }

        return compileClass(methods, stripped)
                .orElseGet(() -> new Tuple<>(methods, generatePlaceholder(stripped) + "\n"));
    }


    private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        int classIndex = input.indexOf("class ");
        if (classIndex < 0) {
            return Optional.empty();
        }
        String modifiers = input.substring(0, classIndex);
        String afterKeyword = input.substring(classIndex + "class ".length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }
        String name = afterKeyword.substring(0, contentStart).strip();
        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        if (!isSymbol(name)) {
            return Optional.empty();
        }
        Tuple<CompileState, String> outputContent = compileStatements(state.pushStructName(name), inputContent, Main::compileClassSegment);
        String generated = generatePlaceholder(modifiers) + "struct " + name + " {" + outputContent.right + "};\n";
        return Optional.of(new Tuple<>(outputContent.left
                .popStructName().addStruct(generated), ""));
    }

    private static boolean isSymbol(String input) {
        if (input.equals("private") || input.equals("record") || input.equals("public")) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isLetter(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileClass(state, input)
                .or(() -> compileMethod(state, input))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        String stripped = input.strip();
        int paramStart = stripped.indexOf("(");
        if (paramStart < 0) {
            return Optional.empty();
        }
        String definition = stripped.substring(0, paramStart).strip();
        return compileDefinition(state, definition).flatMap(outputDefinition -> {
            String withParams = stripped.substring(paramStart + "(".length());
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) {
                return Optional.empty();
            }
            String oldParameters = withParams.substring(0, paramEnd);
            Tuple<CompileState, String> newParameters = compileAll(outputDefinition.left, oldParameters, Main::foldValueChar, Main::compileParameter, Main::mergeValues);
            String withBraces = withParams.substring(paramEnd + ")".length()).strip();
            if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
                return Optional.empty();
            }
            String content = withBraces.substring(1, withBraces.length() - 1);
            String generated = outputDefinition.right + "(" + newParameters.right + "){" + generatePlaceholder(content) + "}\n";
            return Optional.of(new Tuple<>(newParameters.left.addMethod(generated), ""));
        });
    }

    private static String mergeValues(String cache, String element) {
        if (cache.isEmpty()) {
            return element;
        }
        return cache + ", " + element;
    }

    private static DivideState foldValueChar(DivideState state, Character c) {
        if (c == ',') {
            return state.advance();
        }
        return state.append(c);
    }

    private static Tuple<CompileState, String> compileParameter(CompileState state, String s) {
        return compileDefinition(state, s).orElseGet(() -> new Tuple<>(state, generatePlaceholder(s)));
    }

    private static Optional<Tuple<CompileState, String>> compileDefinition(CompileState state, String input) {
        String stripped = input.strip();
        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Optional.empty();
        }
        String beforeName = stripped.substring(0, nameSeparator).strip();
        String name = stripped.substring(nameSeparator + " ".length()).strip();
        String newName;
        if (name.equals("main")) {
            newName = "__main__";
        }
        else {
            newName = name + "_" + state.structNames.last();
        }

        int typeSeparator = beforeName.lastIndexOf(" ");
        if (typeSeparator >= 0) {
            String beforeType = beforeName.substring(0, typeSeparator);
            String type = beforeName.substring(typeSeparator + " ".length());

            return compileType(type).map(outputType -> {
                String compiled = generatePlaceholder(beforeType) + " " + outputType;
                return new Tuple<>(state, compiled + " " + newName);
            });
        }
        else {
            return compileType(beforeName).map(compiled -> {
                return new Tuple<>(state, compiled + " " + newName);
            });
        }
    }

    private static Optional<String> compileType(String type) {
        String stripped = type.strip();
        if (type.equals("boolean")) {
            return Optional.of("int");
        }

        if (type.equals("char")) {
            return Optional.of("char");
        }

        if (type.equals("void")) {
            return Optional.of("void");
        }

        if (type.equals("String")) {
            return Optional.of("char*");
        }

        if (isSymbol(type)) {
            return Optional.of("struct " + stripped);
        }
        return Optional.empty();
    }

    private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("/*", "<comment-start>")
                .replace("*/", "<comment-end>");

        return "/* " + replaced + " */";
    }
}
