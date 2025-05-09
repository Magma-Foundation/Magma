/* private static  */struct DivideState {/* private final List<String> segments; *//* 
        private StringBuilder buffer; *//* 
        private int depth; *//* 

        private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } *//* 

        public DivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        } *//* 

        private DivideState advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } *//* 

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        } *//* 

        public boolean isLevel() {
            return this.depth == 0;
        } *//* 

        public DivideState enter() {
            this.depth++;
            return this;
        } *//* 

        public DivideState exit() {
            this.depth--;
            return this;
        } *//* 

        public boolean isShallow() {
            return this.depth == 1;
        } *//* 
     */
};
/* public  */struct Main {/* 

    private record CompileState(List<String> structs) {
        public CompileState() {
            this(new ArrayList<>());
        }

        public CompileState addStruct(String struct) {
            this.structs.add(struct);
            return this;
        }
    } *//* 

    private record Tuple<A, B>(A left, B right) {
    } *//* 

    public static void main() {
        try {
            var root = Paths.get(".", "src", "java", "magma");
            var source = root.resolve("main.java");
            var target = root.resolve("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang.exe", target.toAbsolutePath().toString(), "-o", "main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    } *//* 

    private static String compile(String input) {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var joinedStructs = String.join("", compiled.left.structs);
        return joinedStructs + compiled.right + "\nint main(){\n\treturn 0;\n}\n";
    } *//* 

    private static Tuple<CompileState, String> compileStatements(CompileState initial, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        var segments = divide(input);
        var output = new StringBuilder();
        var current = initial;
        for (var segment : segments) {
            var mapped = mapper.apply(current, segment);
            current = mapped.left;
            output.append(mapped.right);
        }

        return new Tuple<>(current, output.toString());
    } *//* 

    private static List<String> divide(String input) {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = fold(current, c);
        }

        return current.advance().segments;
    } *//* 

    private static DivideState fold(DivideState state, char c) {
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
/* private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return compileClassSegment(state, stripped);
    } *//* private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        return compileInfix(input, "class ", (beforeKeyword, afterKeyword) -> {
            return compileInfix(afterKeyword, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content1 -> {
                    var statementsTuple = compileStatements(state, content1, Main::compileClassSegment);

                    var name = beforeContent.strip();
                    if (!isSymbol(name)) {
                        return Optional.empty();
                    }

                    var generated = generatePlaceholder(beforeKeyword) + "struct " + name + " {" + statementsTuple.right + "\n};\n";
                    var added = statementsTuple.left.addStruct(generated);
                    return Optional.of(new Tuple<>(added, ""));
                });
            });
        });
    } *//* private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    } *//* private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileClass(state, input)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    } *//* private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }

        return Optional.empty();
    } *//* private static <T> Optional<T> compileInfix(String stripped, String infix, BiFunction<String, String, Optional<T>> mapper) {
        var classIndex = stripped.indexOf(infix);
        if (classIndex >= 0) {
            var left = stripped.substring(0, classIndex);
            var right = stripped.substring(classIndex + infix.length());
            return mapper.apply(left, right);
        }
        return Optional.empty();
    } *//* private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end");

        return "content-start" + replaced + " content-end";
    } *//* } */
int main(){
	return 0;
}
