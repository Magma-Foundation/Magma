/* private */ /* static */ struct DivideState {
	/* private final List<String> segments */;
	/* private int depth */;
	/* private StringBuilder buffer */;/* 

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
/* public */ struct Main {/* 

    private record Tuple<A, B>(A left, B right) {
    } *//* 

    private record CompileState(List<String> structs) {
        public CompileState() {
            this(new ArrayList<>());
        }

        public CompileState addStruct(String struct) {
            this.structs.add(struct);
            return this;
        }
    } *//* 

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
        var tuple = compileAll(new CompileState(), input, Main::compileRootSegment);
        var output = tuple.right;
        var joinedStructs = String.join("", tuple.left().structs);
        return joinedStructs + output;
    } *//* 

    private static Tuple<CompileState, String> compileAll(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler) {
        var segments = divide(input);

        var current = state;
        var output = new StringBuilder();
        for (var segment : segments) {
            var result = compiler.apply(current, segment);
            current = result.left;
            output.append(result.right);
        }

        return new Tuple<>(current, output.toString());
    } *//* 

    private static List<String> divide(String input) {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = foldStatementChar(current, c);
        }

        return current.advance().segments;
    } *//* 

    private static DivideState foldStatementChar(DivideState state, char c) {
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
        } */
	/* return appended */;/* 
     */
};
/* private */ /* static */ /* Optional<Tuple<CompileState, */ /* String>> */ /* compileClass(CompileState */ /* state, */ /* String */ /* input) */ /* { */ /* return */ /* compileInfix(state, */ /* input, */ /* " */ struct ", (state0, tuple0) -> {
	/* var modifiers = Arrays.stream(tuple0.left.strip().split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList() */;
	/* var afterKeyword = tuple0.right */;/* 
            return compileInfix(state0, afterKeyword, "{", (state1, tuple1) -> {
                var name = tuple1.left.strip();
                var withEnd = tuple1.right.strip();
                return compileSuffix(state1, withEnd, "}", (state2, inputContent1) -> {
                    return getCompileStateStringTuple(state2, inputContent1, modifiers, name);
                });
            } */
	/* ) */;
	/* }) */;
};
/* 

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        return compileOr(state, input, List.of(
                Main::compileNamespaced,
                Main::compileClass
        ));
    } *//* 

    private static Tuple<CompileState, String> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        for (var rule : rules) {
            var result = rule.apply(state, input);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return new Tuple<>(state, generatePlaceholder(input));
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileSuffix(
            CompileState state,
            String input,
            String suffix, BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> rule
    ) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }

        var slice = input.substring(0, input.length() - suffix.length());
        return rule.apply(state, slice);
    } *//* 

    private static Optional<Tuple<CompileState, String>> getCompileStateStringTuple(CompileState state1, String inputContent, List<String> modifiers, String name) {
        var outputContent = compileAll(state1, inputContent, Main::compileStructSegment);

        var joined = modifiers.isEmpty() ? "" : modifiers.stream()
                .map(Main::generatePlaceholder)
                .collect(Collectors.joining(" ")) + " ";

        var generated = joined + "struct " + name + " {" + outputContent.right + "\n};\n";
        return Optional.of(new Tuple<>(outputContent.left.addStruct(generated), ""));
    } *//* 

    private static Tuple<CompileState, String> compileStructSegment(CompileState state, String input) {
        return compileOr(state, input, List.of(
                Main::compileClass,
                Main::compileStructStatement
        ));
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileStructStatement(CompileState state, String input) {
        return compileSuffix(state, input.strip(), ";", (state0, input0) -> {
            return Optional.of(new Tuple<>(state0, "\n\t" + generatePlaceholder(input0) + ";"));
        });
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileInfix(CompileState state, String input, String infix, BiFunction<CompileState, Tuple<String, String>, Optional<Tuple<CompileState, String>>> rule) {
        var index = input.indexOf(infix);
        if (index < 0) {
            return Optional.empty();
        }

        var left = input.substring(0, index);
        var right = input.substring(index + infix.length());

        return rule.apply(state, new Tuple<>(left, right));
    } *//* 

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    } *//* 
}
 */