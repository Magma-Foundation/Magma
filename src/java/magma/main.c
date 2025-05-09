/* private static */struct Lists {/* private record JVMList<T>(java.util.List<T> internal) implements List<T> {

            public JVMList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> add(T element) {
                this.internal.add(element);
                return this;
            }

            @Override
            public Iterator<T> iterate() {
                return new HeadedIterator<>(new RangeHead(this.internal.size())).map(this.internal::get);
            }
        } *//* public static <T> List<T> empty() {
            return new JVMList<>();
        } *//* public static <T> List<T> of(T... elements) {
            return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
        } *//*  */
};
/* private static */struct DivideState {
	/* private */ /* List */</* String */> segments;
	/* private */ /* StringBuilder */ buffer;
	/* private */ /* int */ depth;/* private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } *//* public DivideState() {
            this(Lists.empty(), new StringBuilder(), 0);
        } *//* private DivideState advance() {
            this.segments = this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } *//* private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        } *//* public boolean isLevel() {
            return this.depth == 0;
        } *//* public DivideState enter() {
            this.depth++;
            return this;
        } *//* public DivideState exit() {
            this.depth--;
            return this;
        } *//* public boolean isShallow() {
            return this.depth == 1;
        } *//*  */
};
/* private static */struct Iterators {/* public static <T> Iterator<T> fromOptional(Optional<T> optional) {
            return new HeadedIterator<>(optional.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
        } *//*  */
};
/* public */struct Main {/* private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).<Iterator<R>>fold(new HeadedIterator<R>(new EmptyHead<R>()), Iterator::concat);
        }

        @Override
        public Iterator<T> concat(Iterator<T> other) {
            return new HeadedIterator<>(() -> this.head.next().or(other::next));
        }

        @Override
        public Optional<T> next() {
            return this.head.next();
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (optional.isPresent()) {
                    current = optional.get();
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }
    } *//* private record CompileState(List<String> structs) {
        public CompileState() {
            this(Lists.empty());
        }

        public CompileState addStruct(String struct) {
            this.structs.add(struct);
            return this;
        }
    } *//* private record Tuple<A, B>(A left, B right) {
    } *//* private record Joiner() implements Collector<String, Optional<String>> {
        @Override
        public Optional<String> createInitial() {
            return Optional.empty();
        }

        @Override
        public Optional<String> fold(Optional<String> maybeCurrent, String element) {
            return Optional.of(maybeCurrent.map(current -> current + element).orElse(element));
        }
    } *//* public static void main() {
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
    } *//* private static String compile(String input) {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var joinedStructs = compiled.left.structs
                .iterate()
                .collect(new Joiner())
                .orElse("");

        return joinedStructs + compiled.right + "\nint main(){\n\treturn 0;\n}\n";
    } *//* private static Tuple<CompileState, String> compileStatements(CompileState initial, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::merge);
    } *//* private static Tuple<CompileState, String> compileAll(CompileState initial, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<StringBuilder, String, StringBuilder> merger) {
        var segments = divide(input, folder);

        var tuple = new Tuple<>(initial, new StringBuilder());
        var folded = segments.iterate().fold(tuple, (tuple0, element) -> {
            var mapped = mapper.apply(tuple0.left, element);
            return new Tuple<>(mapped.left, merger.apply(tuple0.right, mapped.right));
        });

        return new Tuple<>(folded.left, folded.right.toString());
    } *//* private static StringBuilder merge(StringBuilder buffer, String element) {
        return buffer.append(element);
    } *//* private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } *//* private static DivideState foldStatementChar(DivideState state, char c) {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} *//* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* return appended; *//*  */
};

	/* private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return createStructureRule("class ").apply(state, input)
                .orElseGet(() -> new Tuple<>(state, "\n\t" + generatePlaceholder(stripped.strip())));
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureRule(String infix) {
        return (state, input) -> compileFirst(input, infix, (beforeKeyword, afterKeyword) -> {
            return compileFirst(afterKeyword, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content1 -> {
                    return compileOr(state, beforeContent, Lists.of(
                            createStructureWithTypeParamsRule(beforeKeyword, content1),
                            createStructureWithoutTypeParamsRule(beforeKeyword, content1)
                    ));
                });
            });
        });
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithTypeParamsRule(String beforeKeyword, String content1) {
        return (state, input) -> {
            return compileSuffix(input.strip(), ">", withoutEnd -> {
                return compileFirst(withoutEnd, "<", (name, typeParameters) -> {
                    var typeParams = divide(typeParameters, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return Optional.of(new Tuple<>(state, ""));
                    // return assembleStructure(state, beforeKeyword, name, typeParams, content1);
                });
            });
        };
    } */
	/* private static DivideState foldValueChar(DivideState state1, Character c) {
        if (c == ',') {
            return state1.advance();
        }
        return state1.append(c);
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithoutTypeParamsRule(String beforeKeyword, String content) {
        return (state, name) -> assembleStructure(state, beforeKeyword, name, Lists.empty(), content);
    } */
	/* private static Optional<Tuple<CompileState, String>> assembleStructure(CompileState state, String beforeStruct, String name, List<String> typeParams, String content) {
        return compileSymbol(name.strip(), strippedName -> {
            var statementsTuple = compileStatements(state, content, Main::compileClassSegment);
            var generated = generatePlaceholder(beforeStruct.strip()) + "struct " + strippedName + " {" + statementsTuple.right + "\n};\n";
            var added = statementsTuple.left.addStruct(generated);
            return Optional.of(new Tuple<>(added, ""));
        });
    } */
	/* private static Optional<Tuple<CompileState, String>> compileSymbol(String input, Function<String, Optional<Tuple<CompileState, String>>> mapper) {
        if (!isSymbol(input)) {
            return Optional.empty();
        }

        return mapper.apply(input);
    } */
	/* private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    } */
	/* private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                createStructureRule("class "),
                createStructureRule("interface "),
                Main::compileDefinitionStatement
        ));
    } */
	/* private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input.strip())));
    } */
	/* private static Optional<Tuple<CompileState, String>> compileOr(CompileState state, String input, List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> biFunctionList) {
        return biFunctionList.iterate().map(rule -> rule.apply(state, input)).flatMap(Iterators::fromOptional).next();
    } */
	/* private static @NotNull Optional<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return compileInfix(withoutEnd, " ", Main::findLast, (beforeName, rawName) -> {
                return compileInfix(beforeName, " ", Main::findLast, (beforeType, type) -> {
                    return compileSymbol(rawName.strip(), name -> {
                        var typeTuple = compileType(state, type);
                        return Optional.of(new Tuple<>(typeTuple.left, "\n\t" + generatePlaceholder(beforeType) + " " + typeTuple.right + " " + name + ";"));
                    });
                });
            });
        });
    } */
	/* private static Tuple<CompileState, String> compileType(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                Main::compileTemplate
        ));
    } */
	/* private static Optional<Tuple<CompileState, String>> compileTemplate(CompileState state, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = compileAll(state, argumentsString, Main::foldValueChar, Main::compileType, Main::mergeValues);
                return Optional.of(new Tuple<>(argumentsTuple.left, generatePlaceholder(base) + "<" + argumentsTuple.right + ">"));
            });
        });
    } */
	/* private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) {
            return buffer.append(element);
        }
        return buffer.append(", ").append(element);
    } */
	/* private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    } */
	/* private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }

        return Optional.empty();
    } */
	/* private static <T> Optional<T> compileFirst(String stripped, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(stripped, infix, Main::findFirst, mapper);
    } */
	/* private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Optional<Integer>> locate, BiFunction<String, String, Optional<T>> mapper) {
        return locate.apply(input, infix).flatMap(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return mapper.apply(left, right);
        });
    } */
	/* private static Optional<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    } */
	/* private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end");

        return "content-start" + replaced + " content-end";
    } */
	/* } */
int main(){
	return 0;
}
