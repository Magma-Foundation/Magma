/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.List; */
/* import java.util.Optional; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/*  */
struct State {
	/* private final */ List<char*> segments;
	/* private */ int depth;
	/* private */ struct StringBuilder buffer;
	/* private State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        } */
	/* private State(List<String> segments, StringBuilder buffer, int depth) {
            this.buffer = buffer;
            this.segments = segments;
            this.depth = depth;
        } */
	/* private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } */
	/* private State append(char c) {
            this.buffer.append(c);
            return this;
        } */
	/* public boolean isLevel() {
            return this.depth == 0;
        } */
	/* public State enter() {
            this.depth++;
            return this;
        } */
	/* public State exit() {
            this.depth--;
            return this;
        } */
	/* public boolean isShallow() {
            return this.depth == 1;
        } */
	/*  */
};
struct ");
        if (classIndex >= 0) {
	/* var afterKeyword = stripped.substring(classIndex + */ struct "class ".length());
	/* var contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                var name = afterKeyword.substring(0, contentStart).strip();
                var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    var content = withEnd.substring(0, withEnd.length() - "}".length());
                    var generated = "struct " + name + " {" +
                            compileStatements(content, Main::compileClassSegment) +
                            "\n};\n";
                    structs.add(generated);
                    return Optional.of("");
                }
            } */
	/* } */ struct return Optional.empty();
};
struct Main {
	/* public static final List<String> structs = */ struct new ArrayList<>();
	/* public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("Main.c");
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */
	/* private static String compileRoot(String input) {
        var output = compileStatements(input, Main::compileRootSegment);
        var joinedStructs = String.join("", structs);
        return output + joinedStructs;
    } */
	/* private static String compileStatements(String input, Function<String, String> compiler) {
        return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements);
    } */
	/* private static String compileAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, String> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        var segments = divide(input, folder);

        var output = new StringBuilder();
        for (var segment : segments) {
            var compiled = compiler.apply(segment);
            output = merger.apply(output, compiled);
        }

        return output.toString();
    } */
	/* private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    } */
	/* private static List<String> divide(String input, BiFunction<State, Character, State> folder) {
        return divideAll(input, folder);
    } */
	/* private static List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
        var current = new State();
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
    } */
	/* private static State foldStatementChar(State current, char c) {
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
    } */
	/* private static String compileRootSegment(String input) {
        var stripped = input.strip();

        if (stripped.startsWith("package ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped) + "\n");
    } */
	/* private static String compileClassSegment(String input) {
        return compileClass(input)
                .or(() -> compileDefinitionStatement(input))
                .orElseGet(() -> "\n\t" + generatePlaceholder(input.strip()));
    } */
	/* private static Optional<String> compileDefinitionStatement(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return Optional.of("\n\t" + compileDefinition(withoutEnd) + ";");
        }
        else {
            return Optional.empty();
        }
    } */
	/* private static String compileDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var beforeName = stripped.substring(0, nameSeparator).strip();
            var name = stripped.substring(nameSeparator + " ".length()).strip();

            var typeSeparator = beforeName.lastIndexOf(" ");
            if (typeSeparator >= 0) {
                var beforeType = beforeName.substring(0, typeSeparator).strip();
                var type = beforeName.substring(typeSeparator + " ".length()).strip();
                return generatePlaceholder(beforeType) + " " + compileType(type) + " " + name;
            }
        }

        return generatePlaceholder(stripped);
    } */
	/* private static String compileType(String input) {
        var stripped = input.strip();
        if (stripped.equals("int")) {
            return "int";
        }

        if (stripped.equals("String")) {
            return "char*";
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                var base = withoutEnd.substring(0, argsStart).strip();
                var args = withoutEnd.substring(argsStart + "<".length()).strip();
                return base + "<" + compileAll(args, Main::foldValueChar, Main::compileType, Main::mergeValues) + ">";
            }
        }

        return "struct " + stripped;
    } */
	/* private static State foldValueChar(State state, char c) {
        if (c == ',') {
            return state.advance();
        }
        else {
            return state.append(c);
        }
    } */
	/* private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) {
            return buffer.append(element);
        }
        return buffer.append(", ").append(element);
    } */
	/* private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    } */
	/*  */
};
