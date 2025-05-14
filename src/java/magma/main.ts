/*private static */class DivideState {
	/*private final*/segments : /*List*/<string>;
	/*private*/buffer : /*StringBuilder*/;
	/*private*/depth : number;
	/*private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private DivideState advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            this.depth++;
            return this;
        }

        public DivideState exit() {
            this.depth--;
            return this;
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record CompileState(String output) {
        public CompileState() {
            this("");
        }

        public CompileState append(String element) {
            return new CompileState(this.output + element);
        }
    }

    public static void main() {
        var source = Paths.get(".", "src", "java",*/"Main.java") : /*"magma",*/;
	/*var target*/source.resolveSibling("main.ts") : /*=*/;
	/*try {
            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compileRoot(String input) {
        var compiled = compileSegments(new CompileState(),*/Main::compileRootSegment) : /*input,*/;
	/*return compiled.left.output*/compiled.right : /*+*/;
	/*}

    private static Tuple<CompileState, String> compileSegments(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(state, input, Main::foldStatements,*/Main::mergeStatements) : /*mapper,*/;
	/*}

    private static Tuple<CompileState, String> compileAll(CompileState state, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<StringBuilder, String, StringBuilder> merger) {
        var divisions =*/folder) : /*divide(input,*/;
	/*var current = new Tuple<>(state,*/StringBuilder()) : /*new*/;
	/*for (var segment : divisions) {
            var currentState = current.left;
            var currentElement = current.right;

            var mappedTuple = mapper.apply(currentState, segment);
            var mappedState = mappedTuple.left;
            var mappedElement = mappedTuple.right;

            current = new Tuple<>(mappedState, merger.apply(currentElement, mappedElement));
        }

        return new*/current.right.toString()) : /*Tuple<>(current.left,*/;
	/*}

    private static StringBuilder mergeStatements(StringBuilder cache, String element) {
       */cache.append(element) : /*return*/;
	/*}

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current =*/DivideState() : /*new*/;
	/*for (var i*/0 : /*=*/;
	/*i*/input.length() : /*<*/;
	/*i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

       */current.advance().segments : /*return*/;
	/*}

    private static DivideState foldStatements(DivideState state, char c) {
        var appended*/state.append(c) : /*=*/;
	/*if (c*/' : /*==*/;
	/*' && appended.isLevel()) {
            return appended.advance();
        }

        if (c == '{') {
            return appended.enter();
        }

        if (c == '}') {
            return appended.exit();
        }

       */appended : /*return*/;
	/*}

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        return compileOr(state, input, List.of(
                Main::compileNamespaced,
               */)) : /*Main::compileClass*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        var stripped*/input.strip() : /*=*/;
	/*if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return Optional.of(new Tuple<>(state, ""));
        }
        else {
            return Optional.empty();
        }
    }

    private static Tuple<CompileState, String> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        for (var rule : rules) {
            var maybeTuple = rule.apply(state, input);
            if (maybeTuple.isPresent()) {
                return maybeTuple.get();
            }
        }

        return new*/generatePlaceholder(input)) : /*Tuple<>(state,*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        return compileFirst(input, "class ", (beforeKeyword, right1) -> {
            return compileFirst(right1, "{", (name, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", inputContent -> {
                    var outputContentTuple = compileSegments(state, inputContent, Main::compileClassSegment);
                    var outputContentState = outputContentTuple.left;
                    var outputContent = outputContentTuple.right;

                    var generated = generatePlaceholder(beforeKeyword) + "class " + name.strip() + " {" + outputContent + "}";
                    return Optional.of(new Tuple<>(outputContentState.append(generated), ""));
                });
           */}) : /*});*/;
	/*}

    private static Tuple<CompileState, String> compileClassSegment(CompileState state1, String input1) {
        return compileOr(state1, input1, List.of(
                Main::compileClass,
               */)) : /*Main::compileFieldDefinition*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileFieldDefinition(CompileState state, String input) {
        return*/" : /*compileSuffix(input.strip(),*/;
	/*", withoutEnd -> {
            var definitionTuple = compileDefinition(withoutEnd, state);
            return Optional.of(new Tuple<>(definitionTuple.left, "\n\t" + definitionTuple.right +*/}) : /*";"));*/;
	/*}

    private static Tuple<CompileState, String> compileDefinition(String input, CompileState state) {
        var stripped*/input.strip() : /*=*/;
	/*return compileLast(stripped, " ", (beforeName, name) -> {
            return compileLast(beforeName.strip(), " ", (beforeType, type) -> {
                var typeTuple = compileType(state, type);
                var generated = generatePlaceholder(beforeType) + name + " : " + typeTuple.right;
                return Optional.of(new Tuple<>(typeTuple.left, generated));
            });
        }).orElseGet(() -> new*/generatePlaceholder(input))) : /*Tuple<>(state,*/;
	/*}

    private static Tuple<CompileState, String> compileType(CompileState state, String type) {
        return compileOr(state, type, List.of(
                Main::compileGeneric,
               */)) : /*Main::compilePrimitive*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compilePrimitive(CompileState state, String input) {
        return findPrimitiveValue(input.strip()).map(result -> new*/result)) : /*Tuple<>(state,*/;
	/*}

    private static Optional<String> findPrimitiveValue(String input) {
        if (input.equals("String")) {
            return Optional.of("string");
        }

        if (input.equals("int")) {
            return Optional.of("number");
        }

       */Optional.empty() : /*return*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileGeneric(CompileState state, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (baseString, argumentsString) -> {
                var argumentsTuple = compileAll(state, argumentsString, Main::foldValues, Main::compileType, Main::mergeValues);
                return Optional.of(new Tuple<>(argumentsTuple.left, generatePlaceholder(baseString) + "<" + argumentsTuple.right + ">"));
           */}) : /*});*/;
	/*}

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return*/").append(element) : /*cache.append(",*/;
	/*}

    private static DivideState foldValues(DivideState state, char c) {
        if (c == ',') {
            return state.advance();
        }
       */state.append(c) : /*return*/;
	/*}

    private static <T> Optional<T> compileLast(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix,*/mapper) : /*Main::findLast,*/;
	/*}

    private static int findLast(String input, String infix) {
       */input.lastIndexOf(infix) : /*return*/;
	/*}

    private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }

        var content = input.substring(0, input.length()*/suffix.length()) : /*-*/;
	/*return mapper.apply(content)*/;
	/*}

    private static <T> Optional<T> compileFirst(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix,*/mapper) : /*Main::findFirst,*/;
	/*}

    private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Optional<T>> mapper) {
        var index =*/infix) : /*locator.apply(input,*/;
	/*if (index < 0) {
            return Optional.empty();
        }

        var left =*/index) : /*input.substring(0,*/;
	/*var right = input.substring(index*/infix.length()) : /*+*/;
	/*return*/right) : /*mapper.apply(left,*/;
	/*}

    private static int findFirst(String input, String infix) {
       */input.indexOf(infix) : /*return*/;
	/*}

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("start", "start")
               */"end") : /*.replace("end",*/;
	/*return "start" + replaced*/"*/" : /*+*/;/*
    */}/*

public */class Main {}