/* private */ struct CompileState {
	char* join();
	struct CompileState addStruct(char* structString);
};
/* private */ struct Rule {
	Optional<Tuple<struct CompileState, char*>> parse(struct CompileState state, char* input);
};
/* private */ struct Splitter {
	Optional<Tuple<char*, char*>> split(char* input);
};
/* private */ struct Locator {
	Optional<struct Integer> locate(char* input, char* infix);
};
/* private */ struct Divider {
	List<char*> divideAll(char* input);
};
/* private */ struct Folder extends BiFunction<DivideState, Character, DivideState> {
};
/* private */ struct Merger {
	struct StringBuilder merge(struct StringBuilder currentCache, char* right);
};
/* public */ /* static */ struct StatementMerger implements Merger {/* @Override
        public StringBuilder merge(StringBuilder currentCache, String right) {
            return currentCache.append(right);
        } */
};
/* private */ /* static */ struct FirstLocator implements Locator {/* @Override
        public Optional<Integer> locate(String input, String infix) {
            var index = input.indexOf(infix);
            return index < 0 ? Optional.empty() : Optional.of(index);
        } */
};
/* private */ /* static */ struct LastLocator implements Locator {/* @Override
        public Optional<Integer> locate(String input, String infix) {
            var index = input.lastIndexOf(infix);
            return index < 0 ? Optional.empty() : Optional.of(index);
        } */
};
/* private */ /* static */ struct StatementFolder implements Folder {/* @Override
        public DivideState apply(DivideState state, Character c) {
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
	/* return appended */;
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

    private record MutableCompileState(List<String> structs) implements CompileState {
        public MutableCompileState() {
            this(new ArrayList<>());
        }

        @Override
        public CompileState addStruct(String structString) {
            this.structs.add(structString);
            return this;
        }

        @Override
        public String join() {
            return String.join("", this.structs);
        }
    } *//* 

    private record SuffixRule(String suffix, Rule rule) implements Rule {
        @Override
        public Optional<Tuple<CompileState, String>> parse(
                CompileState state,
                String input
        ) {
            if (!input.endsWith(this.suffix())) {
                return Optional.empty();
            }

            var slice = input.substring(0, input.length() - this.suffix().length());
            return this.rule().parse(state, slice);
        }
    } *//* 

    private record DivideRule(Rule compiler, Divider divider, Merger merger) implements Rule {
        private DivideRule(Rule compiler, Folder folder) {
            this(compiler, new FoldingDivider(folder), new StatementMerger());
        }

        @Override
        public Optional<Tuple<CompileState, String>> parse(CompileState state, String input) {
            var segments = this.divider.divideAll(input);

            var maybeOutput = Optional.of(new Tuple<>(state, new StringBuilder()));
            for (var segment : segments) {
                maybeOutput = maybeOutput.flatMap(output -> {
                    var currentState = output.left;
                    var currentCache = output.right;

                    return this.compiler.parse(currentState, segment).map(result -> {
                        var left = result.left;
                        var right = result.right;
                        return new Tuple<>(left, this.merger.merge(currentCache, right));
                    });
                });
            }

            return maybeOutput.map(output -> new Tuple<>(output.left, output.right.toString()));
        }
    } *//* 

    private record OrRule(List<Rule> rules) implements Rule {
        @Override
        public Optional<Tuple<CompileState, String>> parse(CompileState state, String input) {
            for (var rule : this.rules()) {
                var result = rule.parse(state, input);
                if (result.isPresent()) {
                    return result;
                }
            }

            return Optional.empty();
        }
    } *//* 

    private record InfixSplitter(String infix, Locator locator) implements Splitter {
        public InfixSplitter(String infix) {
            this(infix, new FirstLocator());
        }

        @Override
        public Optional<Tuple<String, String>> split(String input) {
            return this.locator.locate(input, this.infix).map(index -> {
                var left = input.substring(0, index);
                var right = input.substring(index + this.infix().length());
                return new Tuple<String, String>(left, right);
            });
        }
    } *//* 

    private record StripRule(Rule rule) implements Rule {
        @Override
        public Optional<Tuple<CompileState, String>> parse(CompileState state, String input) {
            return this.rule.parse(state, input.strip());
        }
    } */
};
/* private */ /* static */ struct ValueFolder implements Folder {/* @Override
        public DivideState apply(DivideState state, Character c) {
            if (c == ',' && state.isLevel()) {
                return state.advance();
            }
            var appended = state.append(c);
            if (c == '<') {
                return appended.enter();
            }
            if (c == '>') {
                return appended.exit();
            }
            return appended;
        } */
};
/* private */ /* static */ struct LazyRule implements Rule {
	struct private Optional<Rule> maybeChildRule = Optional.empty();/* 

        @Override
        public Optional<Tuple<CompileState, String>> parse(CompileState state, String input) {
            return this.maybeChildRule.flatMap(childRule -> childRule.parse(state, input));
        } *//* 

        public void set(Rule rule) {
            this.maybeChildRule = Optional.of(rule);
        } */
};
/* public */ /* static */ struct ValueMerger implements Merger {/* @Override
        public StringBuilder merge(StringBuilder currentCache, String right) {
            if (currentCache.isEmpty()) {
                return currentCache.append(right);
            }
            return currentCache.append(", ").append(right);
        } */
};
/* private */ /* static */ struct PrimitiveRule implements Rule {
	/* private final Map<String, String> mappings = Map.of(
                "String", "char*"
        ) */;/* 

        @Override
        public Optional<Tuple<CompileState, String>> parse(CompileState state, String input) {
            return this.findMapping(input.strip()).map(result -> new Tuple<>(state, result));
        } *//* 

        private Optional<String> findMapping(String input) {
            if (this.mappings.containsKey(input)) {
                return Optional.of(this.mappings.get(input));
            }
            else {
                return Optional.empty();
            }
        } */
};
/* 

    private record FoldingDivider(Folder folder) implements Divider {
        @Override
        public List<String> divideAll(String input) {
            var current = new DivideState();
            for (var i = 0; i < input.length(); i++) {
                var c = input.charAt(i);
                current = this.folder().apply(current, c);
            }

            return current.advance().segments;
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
        var state = new MutableCompileState();
        var tuple = new DivideRule((state1, input1) -> rootSegment().parse(state1, input1), new StatementFolder())
                .parse(state, input)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));

        var output = tuple.right;
        var joinedStructs = tuple.left().join();
        return joinedStructs + output;
    } *//* 

    private static OrRule rootSegment() {
        return new OrRule(List.of(
                Main::compileNamespaced,
                Main::parseClass,
                Main::parsePlaceholder
        ));
    } *//* 

    private static Optional<Tuple<CompileState, String>> parsePlaceholder(CompileState state, String input) {
        return Optional.of(new Tuple<>(state, generatePlaceholder(input)));
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    } *//* 

    private static Optional<Tuple<CompileState, String>> parseClass(CompileState state, String input) {
        return parseStructured(state, input, "class ");
    } *//* 

    private static Optional<Tuple<CompileState, String>> parseStructured(CompileState state, String input, String infix) {
        return parseInfix(state, input, new InfixSplitter(infix), (state0, tuple0) -> {
            var modifiers = Arrays.stream(tuple0.left.strip().split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            var afterKeyword = tuple0.right;
            return parseInfix(state0, afterKeyword, new InfixSplitter("{"), (state1, tuple1) -> {
                var name = tuple1.left.strip();
                var withEnd = tuple1.right.strip();
                return new SuffixRule("}", (state2, inputContent1) -> {
                    return new DivideRule((state3, input1) -> structSegment().parse(state3, input1), new StatementFolder()).parse(state2, inputContent1).map(outputContent -> {
                        var joined = modifiers.isEmpty() ? "" : modifiers.stream()
                                .map(Main::generatePlaceholder)
                                .collect(Collectors.joining(" ")) + " ";

                        var generated = joined + "struct " + name + " {" + outputContent.right + "\n};\n";
                        return new Tuple<>(outputContent.left.addStruct(generated), "");
                    });
                }).parse(state1, withEnd);
            });
        });
    } *//* 

    private static OrRule structSegment() {
        return new OrRule(List.of(
                Main::parseWhitespace,
                Main::parseClass,
                (state, input) -> parseStructured(state, input, "interface "),
                Main::parseMethod,
                structStatement(),
                Main::parsePlaceholder
        ));
    } *//* 

    private static Optional<Tuple<CompileState, String>> parseMethod(CompileState state, String input) {
        return parseInfix(state, input, new InfixSplitter("("), (state0, tuple0) -> {
            var right = tuple0.right;
            return parseInfix(state0, right, new InfixSplitter(")"), (state1, tuple1) -> {
                return parseDefinition(state1, tuple0.left).flatMap(definition -> {
                    var inputParams = tuple1.left;
                    return values(parameter()).parse(definition.left, inputParams).flatMap(outputParams -> {
                        if (tuple1.right.strip().equals(";")) {
                            return Optional.of(new Tuple<>(outputParams.left, "\n\t" + definition.right + "(" + outputParams.right + ");"));
                        }
                        else {
                            return Optional.empty();
                        }
                    });
                });
            });
        });
    } *//* 

    private static OrRule parameter() {
        return new OrRule(List.of(
                Main::parseWhitespace,
                Main::parseDefinition
        ));
    } *//* 

    private static Optional<Tuple<CompileState, String>> parseWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    } *//* 

    private static Optional<Tuple<CompileState, String>> parseDefinition(CompileState state, String input) {
        return parseInfix(state, input.strip(), new InfixSplitter(" ", new LastLocator()), (state1, tuple) -> {
            return type().parse(state1, tuple.left).map(parse -> {
                return new Tuple<>(parse.left, parse.right + " " + tuple.right);
            });
        });
    } *//* 

    private static Rule type() {
        var type = new LazyRule();
        type.set(new OrRule(List.of(
                new PrimitiveRule(),
                generic(type),
                Main::parseStruct
        )));
        return type;
    } *//* 

    private static Optional<Tuple<CompileState, String>> parseStruct(CompileState state, String input) {
        return Optional.of(new Tuple<>(state, "struct " + input));
    } *//* 

    private static StripRule generic(Rule type) {
        return new StripRule(new SuffixRule(">", (state, input) -> parseInfix(state, input, "<", (state1, tuple) -> {
            var base = tuple.left.strip();
            return values(type)
                    .parse(state1, tuple.right)
                    .map(result -> new Tuple<>(result.left, base + "<" + result.right + ">"));
        })));
    } *//* 

    private static DivideRule values(Rule childRule) {
        return new DivideRule(childRule, new FoldingDivider(new ValueFolder()), new ValueMerger());
    } *//* 

    private static Optional<Tuple<CompileState, String>> parseInfix(CompileState state, String input, String infix, BiFunction<CompileState, Tuple<String, String>, Optional<Tuple<CompileState, String>>> rule) {
        return parseInfix(state, input, new InfixSplitter(infix), rule);
    } *//* 

    private static Optional<Tuple<String, String>> findTypeSeparator(String input) {
        var slices = new FoldingDivider(Main::foldTypeSeparator).divideAll(input);
        if (slices.size() >= 2) {
            var before = slices.subList(0, slices.size() - 1);
            var last = slices.getLast();

            return Optional.of(new Tuple<>(String.join(" ", before), last));
        }
        else {
            return Optional.empty();
        }
    } *//* 

    private static DivideState foldTypeSeparator(DivideState state, Character c) {
        var appended = state.append(c);
        if (c == ' ' && appended.isLevel()) {
            return appended.advance();
        }

        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    } *//* 

    private static Rule structStatement() {
        return new SuffixRule(";", (state0, input0) -> {
            return Optional.of(new Tuple<>(state0, "\n\t" + generatePlaceholder(input0.strip()) + ";"));
        });
    } *//* 

    private static Optional<Tuple<CompileState, String>> parseInfix(
            CompileState state,
            String input,
            Splitter splitter,
            BiFunction<CompileState, Tuple<String, String>, Optional<Tuple<CompileState, String>>> rule
    ) {
        return splitter.split(input).flatMap(tuple -> rule.apply(state, tuple));
    } *//* 

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    } *//* 

}
 */