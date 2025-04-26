/* public  */struct Main {/* private static  */struct State {/* private final List<String> segments; *//* 
        private int depth; *//* 
        private StringBuilder buffer; *//* 

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } *//* 

        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        } *//* 

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } *//* 

        private State append(char c) {
            this.buffer.append(c);
            return this;
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

        public boolean isShallow() {
            return this.depth == 1;
        } *//* 
     */
};
/* 

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("main.c");
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } *//* 

    private static String compileRoot(String input) {
        return compileAll(input, Main::compileRootSegment);
    } *//* 

    private static String compileAll(String input, Function<String, String> compiler) {
        var segments = divide(input);

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compiler.apply(segment));
        }

        return output.toString();
    } *//* 

    private static List<String> divide(String input) {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = foldStatementChar(current, c);
        }

        return current.advance().segments;
    } *//* 

    private static State foldStatementChar(State state, char c) {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} *//* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* 
        if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* 
        return appended; *//* 
     */
};
/* private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped));
    } *//* private static Optional<String> compileClass(String input) {
        return compileInfix(input, " */struct ", (beforeKeyword, afterKeyword) -> {/* return compileInfix(afterKeyword, "{", (left, right) -> {
                var withEnd = right.strip();
                if (withEnd.endsWith("}")) {
                    var inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    var outputContent = compileAll(inputContent, Main::compileStructSegment);
                    return Optional.of(generatePlaceholder(beforeKeyword) + "struct " + left.strip() + " {" + outputContent + "\n};\n");
                } *//* 
                else {
                    return Optional.empty();
                } *//* 
            });
         */
};
/* ); *//* }

    private static String compileStructSegment(String input) {
        return compileClass(input)
                .orElseGet(() -> generatePlaceholder(input)); *//* }

    private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<String>> rule) {
        var index = input.indexOf(infix); *//* if (index < 0) {
            return Optional.empty();
        } *//* var left = input.substring(0, index); *//* var right = input.substring(index + infix.length()); *//* return rule.apply(left, right); *//* }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */"; *//* }
} */