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

    private boolean isLevel() {
        return this.depth == 0;
    }

    private boolean isShallow() {
        return this.depth == 1;
    }
}

void main() {
    try {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var input = Files.readString(source);

        var target = source.resolveSibling("main.c");
        Files.writeString(target, this.compileRoot(input));
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private String compileRoot(String input) {
    return this.getString(input);
}

private String getString(String input) {
    return this.compileAll(input, this::foldStatementChar, this::compileRootSegment, this::mergeStatements);
}

private String compileAll(
        String input,
        BiFunction<State, Character, State> folder,
        Function<String, String> compiler,
        BiFunction<StringBuilder, String, StringBuilder> merger
) {
    var segments = this.divideAll(input, folder);

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

private List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
    var current = new State();
    for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        current = folder.apply(current, c);
    }

    return current.advance().segments;
}

private State foldStatementChar(State state, char c) {
    var appended = state.append(c);
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

private String compileRootSegment(String input) {
    var paramStart = input.indexOf("(");
    if (paramStart >= 0) {
        var definition = input.substring(0, paramStart);
        var withParams = input.substring(paramStart + "(".length());
        var paramEnd = withParams.indexOf(")");
        if (paramEnd >= 0) {
            var oldParams = withParams.substring(0, paramEnd);
            var withBraces = withParams.substring(paramEnd + ")".length()).strip();

            var newParams = this.compileAll(oldParams, this::foldValueChar, this::compileDefinition, this::mergeValues);
            if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                var content = withBraces.substring(1, withBraces.length() - 1);
                return this.compileDefinition(definition) + "(" + newParams + "){" + this.generatePlaceholder(content) + "\n}\n";
            }
        }
    }

    return this.generatePlaceholder(input);
}

private StringBuilder mergeValues(StringBuilder buffer, String element) {
    if (buffer.isEmpty()) {
        return buffer.append(element);
    }
    return buffer.append(", ").append(element);
}

private State foldValueChar(State state, char c) {
    if (c == ',') {
        return state.advance();
    }
    else {
        return state.append(c);
    }
}

private String compileDefinition(String input) {
    var stripped = input.strip();
    var nameSeparator = stripped.lastIndexOf(" ");
    if (nameSeparator >= 0) {
        var beforeName = stripped.substring(0, nameSeparator).strip();
        var name = stripped.substring(nameSeparator + " ".length());

        var typeSeparator = beforeName.lastIndexOf(" ");
        if (typeSeparator >= 0) {
            var beforeType = beforeName.substring(0, typeSeparator);
            String type = this.compileType(beforeName.substring(typeSeparator + " ".length()));
            return this.generatePlaceholder(beforeType) + " " + type + " " + name;
        }
        else {
            String type = this.compileType(beforeName);
            return type + " " + name;
        }
    }

    return this.generatePlaceholder(stripped);
}

private String compileType(String input) {
    var stripped = input.strip();
    if (stripped.equals("void")) {
        return "void";
    }

    if (stripped.equals("String")) {
        return "char*";
    }

    return this.generatePlaceholder(stripped);
}

private String generatePlaceholder(String input) {
    return "/* " + input + " */";
}