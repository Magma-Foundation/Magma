/* private */ struct Rule extends BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> {/*  */
};
/* public */ struct Main {/* 

    private record DivideState(List<String> segments, StringBuilder buffer, int depth) {
        public DivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private DivideState advance() {
            var copy = new ArrayList<>(this.segments);
            copy.add(this.buffer.toString());
            return new DivideState(copy, new StringBuilder(), this.depth);
        }

        private DivideState append(char c) {
            return new DivideState(this.segments, this.buffer.append(c), this.depth);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            return new DivideState(this.segments, this.buffer, this.depth + 1);
        }

        public DivideState exit() {
            return new DivideState(this.segments, this.buffer, this.depth - 1);
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    } *//* 

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

    private record SuffixRule(String suffix, Rule rule) implements Rule {
        @Override
        public Optional<Tuple<CompileState, String>> apply(
                CompileState state,
                String input
        ) {
            if (!input.endsWith(this.suffix())) {
                return Optional.empty();
            }

            var slice = input.substring(0, input.length() - this.suffix().length());
            return this.rule().apply(state, slice);
        }
    } *//* 

    private record DivideRule(Rule compiler) implements Rule {
        @Override
        public Optional<Tuple<CompileState, String>> apply(CompileState state, String input) {
            var segments = divide(input);

            var maybeOutput = Optional.of(new Tuple<>(state, new StringBuilder()));
            for (var segment : segments) {
                maybeOutput = maybeOutput.flatMap(output -> {
                    var currentState = output.left;
                    var currentCache = output.right;

                    return this.compiler().apply(currentState, segment).map(result -> {
                        var left = result.left;
                        var right = result.right;
                        return new Tuple<>(left, currentCache.append(right));
                    });
                });
            }

            return maybeOutput.map(output -> new Tuple<>(output.left, output.right.toString()));
        }
    } *//* 

    private record OrRule(List<Rule> rules) implements Rule {
        @Override
        public Optional<Tuple<CompileState, String>> apply(CompileState state, String input) {
            for (var rule : this.rules()) {
                var result = rule.apply(state, input);
                if (result.isPresent()) {
                    return result;
                }
            }

            return Optional.empty();
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
        var state = new CompileState();
        var tuple = new DivideRule((state1, input1) -> rootSegment().apply(state1, input1))
                .apply(state, input)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));

        var output = tuple.right;
        var joinedStructs = String.join("", tuple.left().structs);
        return joinedStructs + output;
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
/* 

    private static OrRule rootSegment() {
        return new OrRule(List.of(
                Main::compileNamespaced,
                Main::compileClass,
                Main::compilePlaceholder
        ));
    } *//* 

    private static Optional<Tuple<CompileState, String>> compilePlaceholder(CompileState state, String input) {
        return Optional.of(new Tuple<>(state, generatePlaceholder(input)));
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        return compileStructured(state, input, "class ");
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileStructured(CompileState state, String input, String infix) {
        return compileInfix(state, input, infix, (state0, tuple0) -> {
            var modifiers = Arrays.stream(tuple0.left.strip().split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            var afterKeyword = tuple0.right;
            return compileInfix(state0, afterKeyword, "{", (state1, tuple1) -> {
                var name = tuple1.left.strip();
                var withEnd = tuple1.right.strip();
                return new SuffixRule("}", (state2, inputContent1) -> {
                    return new DivideRule((state3, input1) -> structSegment().apply(state3, input1)).apply(state2, inputContent1).map(outputContent -> {
                        var joined = modifiers.isEmpty() ? "" : modifiers.stream()
                                .map(Main::generatePlaceholder)
                                .collect(Collectors.joining(" ")) + " ";

                        var generated = joined + "struct " + name + " {" + outputContent.right + "\n};\n";
                        return new Tuple<>(outputContent.left.addStruct(generated), "");
                    });
                }).apply(state1, withEnd);
            });
        });
    } *//* 

    private static OrRule structSegment() {
        return new OrRule(List.of(
                Main::compileClass,
                (state, input) -> compileStructured(state, input, "interface "),
                structStatement(),
                Main::compilePlaceholder
        ));
    } *//* 

    private static SuffixRule structStatement() {
        return new SuffixRule(";", (state0, input0) -> Optional.of(new Tuple<>(state0, "\n\t" + generatePlaceholder(input0.strip()) + ";")));
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