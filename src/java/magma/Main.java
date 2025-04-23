sealed private interface Result<T, X> permits Ok, Err {
}

sealed private interface Option<T> permits Some, None {
}

record Ok<T, X>(T value) implements Result<T, X> {
}

record Err<T, X>(X error) implements Result<T, X> {
}

record Some<T>(T value) implements Option<T> {
}

static final class None<T> implements Option<T> {
}

private static class State {
    private final List<String> segments;
    private StringBuilder buffer;
    private int depth;

    private State(List<String> segments, StringBuilder buffer, int depth) {
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
    }

    public State() {
        this(new ArrayList<>(), new StringBuilder(), 0);
    }

    private State append(char c) {
        this.buffer.append(c);
        return this;
    }

    private boolean isLevel() {
        return this.depth == 0;
    }

    private boolean isShallow() {
        return this.depth == 1;
    }

    private State enter() {
        this.depth = this.depth + 1;
        return this;
    }

    private State exit() {
        this.depth = this.depth - 1;
        return this;
    }

    private State advance() {
        this.segments.add(this.buffer.toString());
        this.buffer = new StringBuilder();
        return this;
    }
}

void main() {
    var source = Paths.get(".", "src", "java", "magma", "Main.java");
    var target = source.resolveSibling("main.c");

    if (this.run(source, target) instanceof Some(var error)) {
        //noinspection CallToPrintStackTrace
        error.printStackTrace();
    }
}

private Option<IOException> run(Path source, Path target) {
    return switch (this.readString(source)) {
        case Err(var error) -> new Some<>(error);
        case Ok(var input) -> this.writeString(target, this.compile(input));
    };
}

private String compile(String input) {
    return this.compileAll(input, this::foldStatementChar, this::compileRootSegment, this::mergeStatements);
}

private String compileAll(
        String input,
        BiFunction<State, Character, State> folder,
        Function<String, String> compiler,
        BiFunction<StringBuilder, String, StringBuilder> merger
) {
    var segments = this.divide(input, new State(), folder);

    var output = new StringBuilder();
    for (var segment : segments) {
        var compiled = compiler.apply(segment);
        output = merger.apply(output, compiled);
    }

    return output.toString();
}

private StringBuilder mergeStatements(StringBuilder output, String compiled) {
    return output.append(compiled);
}

private List<String> divide(String input, State state, BiFunction<State, Character, State> folder) {
    var current = state;
    for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        current = folder.apply(current, c);
    }
    current.advance();
    return current.segments;
}

private State foldStatementChar(State state, char c) {
    state.append(c);
    if (c == ';' && state.isLevel()) {
        state.advance();
    }
    else if (c == '}' && state.isShallow()) {
        state.advance();
        state.exit();
    }
    else if (c == '{') {
        state.enter();
    }
    else if (c == '}') {
        state.exit();
    }
    return state;
}

private String compileRootSegment(String input0) {
    var rules = List.<Function<String, Option<String>>>of(
            input -> this.compileStructured(input, "interface "),
            input -> this.compileStructured(input, "record "),
            input -> this.compileStructured(input, "class "),
            this::compileMethod
    );

    for (var rule : rules) {
        if (rule.apply(input0) instanceof Some(var compiled)) {
            return compiled;
        }
    }

    return this.generatePlaceholder(input0);
}

private Option<String> compileDefinitionStatement(String input) {
    var stripped = input.strip();
    if (stripped.endsWith(";")) {
        var content = stripped.substring(0, stripped.length() - ";".length());
        return new Some<>("\n\t" + this.compileDefinition(content) + ";");
    }
    else {
        return new None<>();
    }
}

private Option<String> compileMethod(String input) {
    var paramStart = input.indexOf("(");
    if (paramStart >= 0) {
        var definition = input.substring(0, paramStart).strip();
        var withParams = input.substring(paramStart + "(".length());

        var paramEnd = withParams.indexOf(")");
        if (paramEnd >= 0) {
            var params = withParams.substring(0, paramEnd).strip();
            var withBraces = withParams.substring(paramEnd + ")".length()).strip();
            if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                var inputContent = withBraces.substring(1, withBraces.length() - 1);
                var outputContent = this.compileAll(inputContent, this::foldStatementChar, this::compileStatementOrBlock, this::mergeStatements);
                return new Some<>(this.compileDefinition(definition) + "(" + this.generatePlaceholder(params) + "){" + outputContent + "\n}\n");
            }
        }
    }

    return new None<>();
}

private String compileDefinition(String input) {
    var stripped = input.strip();
    var space = stripped.lastIndexOf(" ");
    if (space >= 0) {
        var beforeName = stripped.substring(0, space);
        var typeSeparator = beforeName.lastIndexOf(" ");
        var type = typeSeparator >= 0
                ? beforeName.substring(typeSeparator + " ".length())
                : beforeName;

        var name = stripped.substring(space + " ".length());
        return this.compileType(type) + " " + name;
    }

    return this.generatePlaceholder(stripped);
}

private String compileType(String input) {
    var stripped = input.strip();
    if (stripped.equals("void")) {
        return "void";
    }

    if (stripped.equals("int")) {
        return "int";
    }

    return this.generatePlaceholder(input);
}

private String compileStatementOrBlock(String input) {
    var stripped = input.strip();
    if (stripped.endsWith(";")) {
        var statement = stripped.substring(0, stripped.length() - ";".length());
        var valueSeparator = statement.indexOf("=");
        if (valueSeparator >= 0) {
            var definition = statement.substring(0, valueSeparator).strip();
            var value = statement.substring(valueSeparator + "=".length());
            return "\n\t" + this.compileDefinition(definition) + " = " + this.compileValue(value) + ";";
        }
    }

    return this.generatePlaceholder(input);
}

private String compileValue(String input) {
    var stripped = input.strip();
    if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
        return stripped;
    }

    if (stripped.endsWith(")")) {
        var withoutEnd = stripped.substring(0, stripped.length() - ")".length());
        var paramStart = withoutEnd.indexOf("(");
        if (paramStart >= 0) {
            var caller = withoutEnd.substring(0, paramStart).strip();
            var arguments = withoutEnd.substring(paramStart + "(".length());
            return this.compileValue(caller) + "(" + this.compileAll(arguments, this::foldValueChar, this::compileValue, this::mergeValues) + ")";
        }
    }

    var separator = stripped.lastIndexOf(".");
    if (separator >= 0) {
        var parent = stripped.substring(0, separator);
        var child = stripped.substring(separator + ".".length());

        return this.compileValue(parent) + "." + child;
    }

    if (this.isSymbol(stripped)) {
        return stripped;
    }

    return this.generatePlaceholder(input);
}

private State foldValueChar(State state, char c) {
    if (c == ',' && state.isLevel()) {
        return state.advance();
    }

    return state.append(c);
}

private StringBuilder mergeValues(StringBuilder cache, String element) {
    if (cache.isEmpty()) {
        return cache.append(element);
    }
    return cache.append(", ").append(element);
}

private boolean isSymbol(String input) {
    for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        if (Character.isLetter(c)) {
            continue;
        }
        return false;
    }
    return true;
}

private Option<String> compileStructured(String input, String infix) {
    var interfaceIndex = input.indexOf(infix);
    if (interfaceIndex < 0) {
        return new None<>();
    }

    var beforeKeyword = input.substring(0, interfaceIndex).strip();
    var afterKeyword = input.substring(interfaceIndex + infix.length());
    var contentStart = afterKeyword.indexOf("{");
    if (contentStart < 0) {
        return new None<>();
    }

    var beforeContent = afterKeyword.substring(0, contentStart).strip();
    var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
    if (!withEnd.endsWith("}")) {
        return new None<>();
    }

    var content = withEnd.substring(0, withEnd.length() - "}".length());
    return new Some<>(this.generatePlaceholder(beforeKeyword) + "struct " +
            beforeContent + " {" + this.compileAll(content, this::foldStatementChar, this::compileClassMember, this::mergeStatements) + "\n}\n");
}

private String compileClassMember(String input) {
    var field = this.compileDefinitionStatement(input);
    if (field instanceof Some(var value)) {
        return value;
    }

    return this.generatePlaceholder(input);
}

private String generatePlaceholder(String input) {
    return "/* " + input + " */";
}

private Option<IOException> writeString(Path target, String output) {
    try {
        Files.writeString(target, output);
        return new None<>();
    } catch (IOException e) {
        return new Some<>(e);
    }
}

private Result<String, IOException> readString(Path source) {
    try {
        return new Ok<>(Files.readString(source));
    } catch (IOException e) {
        return new Err<>(e);
    }
}