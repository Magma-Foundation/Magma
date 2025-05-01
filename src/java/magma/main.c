/* private */struct CompileState(JavaList<String> structs, JavaList<String> functions) {
};
/* private */struct DivideState(String input, JavaList<String> segments, StringBuilder buffer, int index, int depth) {
};
/* private */struct Tuple<A, B>(A left, B right) {
};
/* private */struct JavaList<T>(List<T> list) {
};
/* public */struct Main {
};
/* public CompileState */(/* ) {
            this(new JavaList<>(), new JavaList<>());
        } *//* 

        private String generate */(/* ) {
            return String.join("", this.structs.list)
                    + String.join("", this.functions.list);
        } *//* 

        public CompileState addStruct */(/* String struct) {
            return new CompileState(this.structs.addLast(struct), this.functions);
        } *//* 

        public CompileState addFunction */(/* String function) {
            return new CompileState(this.structs, this.functions.addLast(function));
        } *//* public DivideState */(/* String input) {
            this(input, new JavaList<>(), new StringBuilder(), 0, 0);
        } *//* 

        private Optional<DivideState> popAndAppend */(/* ) {
            return this.pop().map(tuple -> tuple.right.append(tuple.left));
        } *//* 

        private DivideState append */(/* char c) {
            return new DivideState(this.input, this.segments, this.buffer.append(c), this.index, this.depth);
        } *//* 

        public Optional<Tuple<Character, DivideState>> pop */(/* ) {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return Optional.of(new Tuple<>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            }
            else {
                return Optional.empty();
            }
        } *//* 

        private DivideState advance */(/* ) {
            return new DivideState(this.input, this.segments.addLast(this.buffer.toString()), new StringBuilder(), this.index, this.depth);
        } *//* 

        public DivideState exit */(/* ) {
            return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth - 1);
        } *//* 

        public boolean isLevel */(/* ) {
            return this.depth == 0;
        } *//* 

        public DivideState enter */(/* ) {
            return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth + 1);
        } *//* 

        public boolean isShallow */(/* ) {
            return this.depth == 1;
        } *//* public JavaList */(/* ) {
            this(new ArrayList<>());
        } *//* 

        public JavaList<T> addLast */(/* T element) {
            var copy = new ArrayList<T>(this.list);
            copy.add(element);
            return new JavaList<>(copy);
        } *//* 

    public static void main */(/* ) {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } *//* 

    private static String compileRoot */(/* String input) {
        var state = new CompileState();
        var tuple = compileAll(state, input, Main::compileRootSegment);
        return tuple.right + tuple.left.generate();
    } *//* 

    private static Tuple<CompileState, String> compileAll */(/* 
            CompileState initial,
            String input,
            BiFunction<CompileState, String, Tuple<CompileState, String>> mapper
    ) {
        var segments = divide(input);

        CompileState state = initial;
        var output = new StringBuilder();
        for (var segment : segments.list) {
            var tuple = mapper.apply(state, segment);
            state = tuple.left;
            output.append(tuple.right);
        }

        return new Tuple<>(state, output.toString());
    } *//* 

    private static JavaList<String> divide */(/* String input) {
        DivideState current = new DivideState(input);
        while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var popped = maybePopped.get();
            var c = popped.left;
            var state = popped.right;
            current = foldSingleQuotes(state, c)
                    .orElseGet(() -> foldStatementChar(state, c));
        }
        return current.advance().segments;
    } *//* 

    private static Optional<DivideState> foldSingleQuotes */(/* DivideState state, char c) {
        if (c != '\'') {
            return Optional.empty();
        }

        return state.append(c).pop().map(maybeNextTuple -> {
            var nextChar = maybeNextTuple.left;
            var nextState = maybeNextTuple.right.append(nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState.popAndAppend().orElse(nextState)
                    : nextState;

            return withEscaped.popAndAppend().orElse(withEscaped);
        });
    } *//* 

    private static DivideState foldStatementChar */(/* DivideState state, char c) {
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
    } *//* 

    private static Tuple<CompileState, String> compileRootSegment */(/* CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return compileWhitespace(state, stripped)
                .or(() -> compileStructure(state, stripped, "class "))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped)));
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileStructure */(/* CompileState state, String input, String infix) {
        return compileInfix(input, infix, (beforeKeyword, afterKeyword) -> {
            return compileInfix(afterKeyword, "{", (name, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content -> {
                    var tuple = compileAll(state, content, Main::compileStructSegment);
                    var generated = generatePlaceholder(beforeKeyword.strip()) + "struct " + name.strip() + " {" + tuple.right + "\n};\n";
                    return Optional.of(new Tuple<>(tuple.left.addStruct(generated), ""));
                });
            });
        });
    } *//* 

    private static Tuple<CompileState, String> compileStructSegment */(/* CompileState state, String input) {
        return compileWhitespace(state, input)
                .or(() -> compileStructure(state, input, "record "))
                .or(() -> compileMethod(state, input))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileWhitespace */(/* CompileState state, String input) {
        if (input.isBlank()) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    } *//* 


    private static Optional<Tuple<CompileState, String>> compileMethod */(/* CompileState state, String input) {
        return compileInfix(input, "(", (s, s2) -> {
            var generated = generatePlaceholder(s) + "(" + generatePlaceholder(s2);
            return Optional.of(new Tuple<>(state.addFunction(generated), ""));
        });
    } *//* 

    private static <T> Optional<T> compileSuffix */(/* String input, String suffix, Function<String, Optional<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }
        var content = input.substring(0, input.length() - suffix.length());
        return mapper.apply(content);
    } *//* 

    private static String generatePlaceholder */(/* String input) {
        return "/* " + input + " */";
    } *//* 

    private static <T> Optional<T> compileInfix */(/* String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        var classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            var beforeKeyword = input.substring(0, classIndex);
            var afterKeyword = input.substring(classIndex + infix.length());
            return mapper.apply(beforeKeyword, afterKeyword);
        }
        return Optional.empty();
    } */