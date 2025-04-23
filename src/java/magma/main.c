/* private static  */struct State {/* private final List<String> segments; *//* 
    private StringBuilder buffer; *//* 
    private int depth; *//* 

    private State(List<String> segments, StringBuilder buffer, int depth) {
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
    } *//* 

    public State() {
        this(new ArrayList<>(), new StringBuilder(), 0);
    } *//* 

    public boolean isLevel() {
        return this.depth == 0;
    } *//* 

    public State enter() {
        this.depth++;
        return this;
    } *//* 

    public State exit() {
        this.depth--;
        return this;
    } *//* 

    private State append(char c) {
        this.buffer.append(c);
        return this;
    } *//* 

    private State advance() {
        this.segments.add(this.buffer.toString());
        this.buffer = new StringBuilder();
        return this;
    } *//* 

    public boolean isShallow() {
        return this.depth == 1;
    } *//* 
 */};
/* 

void main() {
    try {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var input = Files.readString(source);

        var target = source.resolveSibling("main.c");
        Files.writeString(target, this.compileRoot(input));
    } catch (IOException e) {
        //noinspection CallToPrintStackTrace
        e.printStackTrace();
    }
} *//* 

private String compileRoot(String input) {
    return this.compileAll(input, this::compileRootSegment);
} *//* 

private String compileAll(String input, Function<String, String> compiler) {
    var segments = this.divide(input, new State()).segments;

    var output = new StringBuilder();
    for (var segment : segments) {
        output.append(compiler.apply(segment));
    }

    return output.toString();
} *//* 

private String compileRootSegment(String input) {
    var classIndex = input.indexOf(" */struct ");
    if (classIndex >= 0) {/* var left = input.substring(0, classIndex); *//* 
        var right = input.substring(classIndex + "class ".length()); *//* 
        var contentStart = right.indexOf("{");
        if (contentStart >= 0) {
            var name = right.substring(0, contentStart).strip();
            var withEnd = right.substring(contentStart + "{".length()).strip();
            if (withEnd.endsWith("}")) {
                var inputContent = withEnd.substring(0, withEnd.length() - 1);
                var outputContent = this.compileAll(inputContent, this::compileClassSegment);
                return this.generatePlaceholder(left) + "struct " + name + " {" + outputContent + "};\n";
            }
        }
    } *//* 

    return this.generatePlaceholder(input); *//* 
}

private String compileClassSegment(String input) {
    return this.generatePlaceholder(input); *//* 
}

private State divide(String input, State state) {
    var current = state; *//* 
    for (var i = 0; *//*  i < input.length(); *//*  i++) {
        var c = input.charAt(i);
        current = this.foldStatementChar(current, c);
    } *//* 
    return current.advance(); *//* 
}

private State foldStatementChar(State state, char c) {
    var appended = state.append(c); *//* 
    if (c == '; *//* ' && appended.isLevel()) {
        return appended.advance();
    } *//* 
    if (c == '}' && appended.isShallow()) {
        return appended.advance().exit(); *//* 
    }
    if (c == '{') {
        return appended.enter();
    } *//* 
    if (c == '}') {
        return appended.exit(); *//* 
    }
    else {
        return appended; *//* 
    }
 */};
/* 

private String generatePlaceholder(String input) {
    return "/* " + input + " */";
} *//*  */