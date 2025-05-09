/* private */struct List_/* T */ {/* List<T> addLast(T element); *//* Iterator<T> iterate(); *//* boolean contains(T element, BiFunction<T, T, Boolean> equator); *//* boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator); *//*  */
};
/* private */struct Type {/* String stringify(); *//* String generate(); *//* boolean equalsTo(Type other); *//*  */
};
/* private @ */struct Actual {/*  */
};
/* private static */struct StandardLibrary {
	/*         private static <T> */ /* T */* allocate(/* int length */)/*  {
            return (T[]) new Object[length];
        } *//*  */
};
/* private static */struct Lists {
	/* public static <T> */ struct List_/* T */ of(/* T... elements */)/*  {
            return new ArrayList<>(elements, elements.length);
        } *//*  */
};
/* private */struct List_char_ref {/* List<T> addLast(T element); *//* Iterator<T> iterate(); *//* boolean contains(T element, BiFunction<T, T, Boolean> equator); *//* boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator); *//*  */
};
/* private static */struct DivideState {
	/* private */ struct List_char_ref segments;
	/* private */ char* buffer;
	/* private */ int depth;/* private DivideState(List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } *//* public DivideState() {
            this(new ArrayList<String>(), "", 0);
        } */
	/* private */ /* DivideState */ advance(/*  */)/*  {
            this.segments = this.segments.addLast(this.buffer);
            this.buffer = "";
            return this;
        } */
	/* private */ /* DivideState */ append(/* char c */)/*  {
            this.buffer = this.buffer + c;
            return this;
        } */
	/* public */ /* boolean */ isLevel(/*  */)/*  {
            return this.depth == 0;
        } */
	/* public */ /* DivideState */ enter(/*  */)/*  {
            this.depth++;
            return this;
        } */
	/* public */ /* DivideState */ exit(/*  */)/*  {
            this.depth--;
            return this;
        } */
	/* public */ /* boolean */ isShallow(/*  */)/*  {
            return this.depth == 1;
        } *//*  */
};
/* private */struct Iterator_/* R */ {
	/* <R> */ /* R */ fold(/* R initial, BiFunction<R, T, R> folder */)/* ; */
	/* <C> */ /* C */ collect(/* Collector<T, C> collector */)/* ; */
	/* <R> */ struct Iterator_/* R */ map(/* Function<T, R> mapper */)/* ; */
	/* <R> */ struct Iterator_/* R */ flatMap(/* Function<T, Iterator<R>> mapper */)/* ; *//* Iterator<T> concat(Iterator<T> other); *//* Optional<T> next(); *//* boolean anyMatch(Predicate<T> predicate); */
	/* <R> Iterator<Tuple<T, */ /* R>> */ zip(/* Iterator<R> other */)/* ; *//* boolean allMatch(Predicate<T> predicate); *//*  */
};
/* private */struct Iterator_/* T */ {
	/* <R> */ /* R */ fold(/* R initial, BiFunction<R, T, R> folder */)/* ; */
	/* <C> */ /* C */ collect(/* Collector<T, C> collector */)/* ; */
	/* <R> */ struct Iterator_/* R */ map(/* Function<T, R> mapper */)/* ; */
	/* <R> */ struct Iterator_/* R */ flatMap(/* Function<T, Iterator<R>> mapper */)/* ; *//* Iterator<T> concat(Iterator<T> other); *//* Optional<T> next(); *//* boolean anyMatch(Predicate<T> predicate); */
	/* <R> Iterator<Tuple<T, */ /* R>> */ zip(/* Iterator<R> other */)/* ; *//* boolean allMatch(Predicate<T> predicate); *//*  */
};
/* private static */struct Iterators {
	/* public static <T> */ struct Iterator_/* T */ fromOptional(/* Optional<T> optional */)/*  {
            return new HeadedIterator<>(optional.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
        } *//*  */
};
/* private */struct List_/* T> */ {/* List<T> addLast(T element); *//* Iterator<T> iterate(); *//* boolean contains(T element, BiFunction<T, T, Boolean> equator); *//* boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator); *//*  */
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
        public boolean anyMatch(Predicate<T> predicate) {
            return this.fold(false, (aBoolean, t) -> aBoolean || predicate.test(t));
        }

        @Override
        public <R> Iterator<Tuple<T, R>> zip(Iterator<R> other) {
            return new HeadedIterator<>(() -> {
                return this.head.next().flatMap(nextValue -> {
                    return other.next().map(otherValue -> {
                        return new Tuple<>(nextValue, otherValue);
                    });
                });
            });
        }

        @Override
        public boolean allMatch(Predicate<T> predicate) {
            return this.fold(true, (aBoolean, t) -> aBoolean && predicate.test(t));
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
    } */
	/* private */ /* record */ CompileState(/* 
            List<String> structs,
            Map<String, Function<List<Type>, Optional<CompileState>>> expandables,
            List<ObjectType> expansions */)/*  {
        public CompileState() {
            this(new ArrayList<String>(), new HashMap<>(), new ArrayList<ObjectType>());
        }

        private Optional<CompileState> expand(ObjectType expansion) {
            if (this.expansions.contains(expansion, ObjectType::equalsTo)) {
                return Optional.empty();
            }

            return this.addExpansion(expansion)
                    .findExpandable(expansion.name)
                    .flatMap(expandable -> expandable.apply(expansion.arguments));
        }

        private CompileState addExpansion(ObjectType type) {
            return new CompileState(this.structs, this.expandables, this.expansions.addLast(type));
        }

        public CompileState addStruct(String struct) {
            return new CompileState(this.structs.addLast(struct), this.expandables, this.expansions);
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
    } */
	/* private */ /* record */ Joiner(/* String delimiter */)/*  implements Collector<String, Optional<String>> {
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
    } */
	/* private */ /* record */ Ref(/* Type type */)/*  implements Type {
        @Override
        public String stringify() {
            return this.type.stringify() + "_ref";
        }

        @Override
        public String generate() {
            return this.type.generate() + "*";
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Ref ref && this.type.equalsTo(ref.type);
        }
    } */
	/* private */ /* record */ ObjectType(/* String name, List<Type> arguments */)/*  implements Type {
        @Override
        public String stringify() {
            return this.generate();
        }

        @Override
        public String generate() {
            return "struct " + this.name + this.joinArguments();
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof ObjectType objectType
                    && this.name.equals(objectType.name)
                    && this.arguments.equalsTo(objectType.arguments, Type::equalsTo);
        }

        private String joinArguments() {
            return this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");
        }
    } */
	/* private */ /* record */ Placeholder(/* String value */)/*  implements Type {
        @Override
        public String stringify() {
            return generatePlaceholder(this.value);
        }

        @Override
        public String generate() {
            return generatePlaceholder(this.value);
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Placeholder placeholder && this.value.equals(placeholder.value);
        }
    } */
	/* private */ /* record */ Definition(/* List<String> annotations, String afterAnnotations, Type type, String name */)/*  {
        private String generate() {
            return generatePlaceholder(this.afterAnnotations()) + " " + this.type().generate() + " " + this.name();
        }
    } */
	/* public static */ /* void */ main(/*  */)/*  {
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
    } */
	/* private static */ char* compile(/* String input */)/*  {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var joinedStructs = compiled.left.structs
                .iterate()
                .collect(new Joiner())
                .orElse("");

        return joinedStructs + compiled.right + "\nint main(){\n\treturn 0;\n}\n";
    } */
	/* private static Tuple<CompileState, */ /* String> */ compileStatements(/* CompileState initial, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper */)/*  {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::merge);
    } */
	/* private static Tuple<CompileState, */ /* String> */ compileAll(/* CompileState initial, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<String, String, String> merger */)/*  {
        var tuple = parseAll(initial, input, folder, mapper);
        return new Tuple<>(tuple.left, generateAll(tuple.right, merger));
    } */
	/* private static <T> Tuple<CompileState, */ struct List_/* T> */ parseAll(/* 
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
     */)/*  {
        var segments = divide(input, folder);

        var tuple = new Tuple<>(initial, (List<T>) new ArrayList<T>());
        var folded = segments.iterate().fold(tuple, (tuple0, element) -> {
            var mapped = mapper.apply(tuple0.left, element);
            return new Tuple<>(mapped.left, tuple0.right.addLast(mapped.right));
        });

        return new Tuple<CompileState, List<T>>(folded.left, tuple.right);
    } */
	/* private static */ char* generateAll(/* List<String> elements, BiFunction<String, String, String> merger */)/*  {
        return elements.iterate().fold("", merger);
    } */
	/* private static */ char* merge(/* String buffer, String element */)/*  {
        return buffer + element;
    } */
	/* private static */ struct List_char_ref divide(/* String input, BiFunction<DivideState, Character, DivideState> folder */)/*  {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } */
	/* private static */ /* DivideState */ foldStatementChar(/* DivideState state, char c */)/*  {
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
            return assembleStructure(state, beforeKeyword, name, new ArrayList<String>(), new ArrayList<Type>(), content).map(newState -> {
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
                Main::compileDefinitionStatement,
                Main::compileMethod
        ));
    } */
	/* private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (definitionString, withParams) -> {
            return compileFirst(withParams, ")", (params, content) -> {
                return parseDefinition(state, definitionString).flatMap(definitionTuple -> {
                    var definitionState = definitionTuple.left;
                    var definition = definitionTuple.right;
                    if (definition.annotations.contains("Actual", String::equals)) {
                        return Optional.of(new Tuple<>(definitionState, ""));
                    }

                    var generated = "\n\t" + definition.generate() + "(" + generatePlaceholder(params) + ")" + generatePlaceholder(content);
                    return Optional.of(new Tuple<>(definitionState, generated));
                });
            });
        });
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
	/* private static Optional<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd)
                    .map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))
                    .map(tuple -> new Tuple<>(tuple.left, "\n\t" + tuple.right + ";"));
        });
    } */
	/* private static Optional<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return compileInfix(input.strip(), " ", Main::findLast, (beforeName, rawName) -> {
            return compileInfix(beforeName.strip(), " ", Main::findLast, (beforeType, type) -> {
                var strippedBeforeType = beforeType.strip();
                return compileInfix(strippedBeforeType, "\n", Main::findLast, (annotationsString, afterAnnotations) -> {
                    var annotations = divide(annotationsString, foldWithDelimiter('\n'))
                            .iterate()
                            .map(slice -> slice.substring(1))
                            .collect(new ListCollector<>());

                    return assembleDefinition(state, annotations, afterAnnotations, rawName, type);
                }).or(() -> {
                    return assembleDefinition(state, new ArrayList<>(), strippedBeforeType, rawName, type);
                });
            });
        });
    } */
	/* private static Optional<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, List<String> annotations, String afterAnnotations, String rawName, String type) {
        return compileSymbol(rawName.strip(), name -> {
            var typeTuple = parseType(state, type);
            return Optional.of(new Tuple<>(typeTuple.left, new Definition(annotations, afterAnnotations, typeTuple.right, name)));
        });
    } */
	/* private static BiFunction<DivideState, Character, DivideState> foldWithDelimiter(char delimiter) {
        return (state, c) -> {
            if (c == delimiter) {
                return state.advance();
            }
            return state.append(c);
        };
    } */
	/* private static Tuple<CompileState, Type> parseType(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                typed(Main::parsePrimitive),
                typed(Main::parseTemplate),
                typed(Main::parseArray)
        )).orElseGet(() -> new Tuple<>(state, new Placeholder(input.strip())));
    } */
	/* private static Optional<Tuple<CompileState, Type>> parseArray(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("[]")) {
            var slice = input.substring(0, stripped.length() - "[]".length());
            var childTuple = parseType(state, slice);
            return Optional.of(new Tuple<>(childTuple.left, new Ref(childTuple.right)));
        }

        return Optional.empty();
    } */
	/* private static <T extends R, R> BiFunction<CompileState, String, Optional<Tuple<CompileState, R>>> typed(BiFunction<CompileState, String, Optional<Tuple<CompileState, T>>> rule) {
        return (state, input) -> rule.apply(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right));
    } */
	/* private static Optional<Tuple<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("String")) {
            return Optional.of(new Tuple<>(state, new Ref(Primitive.Char)));
        }
        if (stripped.equals("int")) {
            return Optional.of(new Tuple<>(state, Primitive.Int));
        }
        return Optional.empty();
    } */
	/* private static Optional<Tuple<CompileState, ObjectType>> parseTemplate(CompileState oldState, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = parseAll(oldState, argumentsString, Main::foldValueChar, Main::parseType);

                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right;

                var expansion = new ObjectType(base, arguments);
                var withExpansion = argumentsState.expand(expansion).orElse(argumentsState);
                return Optional.of(new Tuple<>(withExpansion, expansion));
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
        Char("char"),
        Int("int");

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

        @Override
        public boolean equalsTo(Type other) {
            return this == other;
        }
    } */
	/* } */
int main(){
	return 0;
}
