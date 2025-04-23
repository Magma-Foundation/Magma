/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.List; */
/* import java.util.Optional; */
/* import java.util.function.Function; */
struct Main {struct State {/* private final List<String> segments; */
/* 
        private StringBuilder buffer; */
/* 
        private int depth; */
/* 

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

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State exit() {
            this.depth--;
            return this;
        }
    }

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return compileStatements(input, Main::compileRootSegment); */
/* 
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        var segments = divide(input, new State()); */
/* 

        var output = new StringBuilder(); */
/* 
        for (var segment : segments) {
            output.append(compiler.apply(segment));
        }
        return output.toString(); */
/* 
    }

    private static List<String> divide(String input, State state) {
        var current = state; */
/* 
        for (var i = 0; */
/*  i < input.length(); */
/*  i++) {
            var c = input.charAt(i);
            current = foldStatementChar(current, c);
        }

        return current.advance().segments; */
/* 
    }

    private static State foldStatementChar(State state, char c) {
        var appended = state.append(c); */
/* 
        if (c == '; */
/* ' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended; */
/* 
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip(); */
/* 
        if (stripped.startsWith("package ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped)); */
/* 
    }

    private static Optional<String> compileClass(String stripped) {
        var classIndex = stripped.indexOf("class "); */
/* 
        if (classIndex < 0) {
            return Optional.empty();
        }

        var afterKeyword = stripped.substring(classIndex + "class ".length()); */
/* 
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }

        var name = afterKeyword.substring(0, contentStart).strip();
        var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }

        var content = withEnd.substring(0, withEnd.length() - "}".length()); */
/* 
        return Optional.of("struct " + name + " {" + compileStatements(content, Main::compileClassSegment) + "}"); */
/* 
    }

    private static String compileClassSegment(String input) {
        return compileClass(input).orElseGet(() -> generatePlaceholder(input)); */
/* 
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */\n"; */
/* 
     */
}}