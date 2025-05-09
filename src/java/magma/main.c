/* private */struct Type {/* String stringify(); *//* String generate(); *//*  */
};
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
/* private */struct List_char_ref {/* List<T> add(T element); *//* Iterator<T> iterate(); *//*  */
};
/* private static */struct DivideState {
	/* private */ struct List_char_ref segments;
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
    } *//* private record CompileState(
            List<String> structs,
            Map<String, Function<List<Type>, Optional<CompileState>>> expandables
    ) {
        public CompileState() {
            this(Lists.empty(), new HashMap<>());
        }

        public CompileState addStruct(String struct) {
            return new CompileState(this.structs.add(struct), this.expandables);
        }

        public CompileState addExpandable(String name, Function<List<Type>, Optional<CompileState>> expandable) {
            this.expandables.put(name, expandable);
            return this;
        }

        public Optional<Function<List<Type>, Optional<CompileState>>> findExpandable(String name) {
            if (this.expandables.containsKey(name)) {
                return Optional.of(this.expandables.get(name));
            }
            return Optional.empty();
        }
    } *//* private record Tuple<A, B>(A left, B right) {
    } *//* private record Joiner(String delimiter) implements Collector<String, Optional<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Optional<String> createInitial() {
            return Optional.empty();
        }

        @Override
        public Optional<String> fold(Optional<String> maybeCurrent, String element) {
            return Optional.of(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        }
    } *//* private record Ref(Type type) implements Type {
        @Override
        public String stringify() {
            return this.type.stringify() + "_ref";
        }

        @Override
        public String generate() {
            return this.type.generate() + "*";
        }
    } *//* private record ObjectType(String name, List<Type> arguments) implements Type {
        @Override
        public String stringify() {
            return this.generate();
        }

        @Override
        public String generate() {
            return "struct " + this.name + this.joinArguments();
        }

        private String joinArguments() {
            return this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");
        }
    } *//* private record Placeholder(String input) implements Type {
        @Override
        public String stringify() {
            return generatePlaceholder(this.input);
        }

        @Override
        public String generate() {
            return generatePlaceholder(this.input);
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
        var tuple = parseAll(initial, input, folder, mapper);
        return new Tuple<>(tuple.left, generateAll(tuple.right, merger));
    } *//* private static <T> Tuple<CompileState, List<T>> parseAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
    ) {
        var segments = divide(input, folder);

        var tuple = new Tuple<>(initial, Lists.<T>empty());
        var folded = segments.iterate().fold(tuple, (tuple0, element) -> {
            var mapped = mapper.apply(tuple0.left, element);
            return new Tuple<>(mapped.left, tuple0.right.add(mapped.right));
        });

        return new Tuple<CompileState, List<T>>(folded.left, tuple.right);
    } *//* private static String generateAll(List<String> elements, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return elements.iterate().fold(new StringBuilder(), merger).toString();
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
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithTypeParamsRule(String beforeKeyword, String content) {
        return (state, input) -> {
            return compileSuffix(input.strip(), ">", withoutEnd -> {
                return compileFirst(withoutEnd, "<", (name, typeParameters) -> {
                    var typeParams = divide(typeParameters, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return Optional.of(new Tuple<>(state.addExpandable(name, (typeArguments) -> {
                        return assembleStructure(state, beforeKeyword, name, typeParams, typeArguments, content);
                    }), ""));
                });
            });
        };
    } */
	/* private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',') {
            return state.advance();
        }
        return state.append(c);
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithoutTypeParamsRule(String beforeKeyword, String content) {
        return (state, name) -> {
            return assembleStructure(state, beforeKeyword, name, Lists.empty(), Lists.empty(), content).map(newState -> {
                return new Tuple<>(newState, "");
            });
        };
    } */
	/* private static Optional<CompileState> assembleStructure(
            CompileState state,
            String beforeStruct,
            String name,
            List<String> typeParams,
            List<Type> typeArguments,
            String content
    ) {
        return compileSymbol(name.strip(), strippedName -> {
            var statementsTuple = compileStatements(state, content, Main::compileClassSegment);
            var generated = generatePlaceholder(beforeStruct.strip()) + new ObjectType(strippedName, typeArguments).generate() + " {" + statementsTuple.right + "\n};\n";
            var added = statementsTuple.left.addStruct(generated);
            return Optional.of(added);
        });
    } */
	/* private static <T> Optional<T> compileSymbol(String input, Function<String, Optional<T>> mapper) {
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
	/* private static <T> Optional<Tuple<CompileState, T>> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, T>>>> rules
    ) {
        return rules.iterate()
                .map(rule -> rule.apply(state, input))
                .flatMap(Iterators::fromOptional)
                .next();
    } */
	/* private static @NotNull Optional<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return compileInfix(withoutEnd, " ", Main::findLast, (beforeName, rawName) -> {
                return compileInfix(beforeName, " ", Main::findLast, (beforeType, type) -> {
                    return compileSymbol(rawName.strip(), name -> {
                        var typeTuple = parseType(state, type);
                        return Optional.of(new Tuple<>(typeTuple.left, "\n\t" + generatePlaceholder(beforeType) + " " + typeTuple.right.generate() + " " + name + ";"));
                    });
                });
            });
        });
    } */
	/* private static Tuple<CompileState, Type> parseType(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                typed(Main::parsePrimitive),
                typed(Main::parseTemplate)
        )).orElseGet(() -> new Tuple<>(state, new Placeholder(input.strip())));
    } */
	/* private static <T extends R, R> BiFunction<CompileState, String, Optional<Tuple<CompileState, R>>> typed(BiFunction<CompileState, String, Optional<Tuple<CompileState, T>>> rule) {
        return (state, input) -> rule.apply(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right));
    } */
	/* private static Optional<Tuple<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("String")) {
            return Optional.of(new Tuple<>(state, new Ref(Primitive.Char)));
        }
        return Optional.empty();
    } */
	/* private static Optional<Tuple<CompileState, ObjectType>> parseTemplate(CompileState oldState, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = parseAll(oldState, argumentsString, Main::foldValueChar, (state1, input1) -> {
                    return parseType(state1, input1);
                });

                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right;
                return argumentsState.findExpandable(base).flatMap(expandable -> {
                    return expandable.apply(arguments).map(newState -> {
                        return new Tuple<>(newState, new ObjectType(base, arguments));
                    });
                }).or(() -> {
                    return Optional.of(new Tuple<>(argumentsState, new ObjectType(base, arguments)));
                });
            });
        });
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
	/* private enum Primitive implements Type {
        Char("char");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String stringify() {
            return this.value;
        }

        @Override
        public String generate() {
            return this.value;
        }
    } */
	/* } */
int main(){
	return 0;
}
