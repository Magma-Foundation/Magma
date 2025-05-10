/* public  */class Main {
	/* private  */interface Collector<T, C> {
		createInitial() : C;
		fold(current : C, element : T) : C;/* 
     */}
	/* 

    private  */interface Iterator<T> {
		fold<R>(initial : R, folder : /* BiFunction<R, T, R> */) : R;
		map<R>(mapper : /* Function<T, R> */) : /* Iterator<R> */;
		collect<R>(collector : /* Collector<T, R> */) : R;/* 
     */}
	/* 

    private  */interface List<T> {
		add(element : T) : /* List<T> */;
		iterate() : /* Iterator<T> */;
		removeLast() : /* Optional<Tuple<List<T>, T>> */;/* 
     */}
	/* 

    private  */interface Head<T> {
		next() : /* Optional<T> */;/* 
     */}
	/* private */ HeadedIterator<T>(head : /* Head<T> */) : record/* implements Iterator<T> {
        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {
                    current = optional.get();
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public <R> R collect(Collector<T, R> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }
    } */
	/* 

    private static  */class RangeHead implements Head<Integer> {
		/* private final */ length : int;
		/* private */ counter : int;
		RangeHead(length : int) : public/* {
            this.length = length;
        } */
		/* @Override
        public */ next() : /* Optional<Integer> *//* {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return Optional.of(value);
            }

            return Optional.empty();
        } *//* 
     */}
	/* 


    private static  */class Lists {
		/* private */ JVMList<T>(elements : /* java.util.List<T> */) : record/* implements List<T> {


            public JVMList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> add(T element) {
                this.elements.add(element);
                return this;
            }

            @Override
            public Iterator<T> iterate() {
                return new HeadedIterator<>(new RangeHead(this.elements.size())).map(this.elements::get);
            }

            @Override
            public Optional<Tuple<List<T>, T>> removeLast() {
                if (this.elements.isEmpty()) {
                    return Optional.empty();
                }

                var slice = this.elements.subList(0, this.elements.size() - 1);
                var last = this.elements.getLast();
                return Optional.of(new Tuple<>(new JVMList<>(slice), last));
            }
        } */
		/* public static  */ empty<T>() : /* List<T> *//* {
            return new JVMList<>();
        } *//* 
     */}
	/* 

    private static  */class State {
		/* private */ segments : /* List<String> */;
		/* private */ buffer : StringBuilder;
		/* private */ depth : int;
		State(segments : /* List<String> */, buffer : StringBuilder, depth : int) : public/* {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } */
		State() : public/* {
            this(Lists.empty(), new StringBuilder(), 0);
        } */
		/* private */ advance() : State/* {
            this.segments = this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } */
		/* private */ append(c : char) : State/* {
            this.buffer.append(c);
            return this;
        } */
		/* public */ enter() : State/* {
            this.depth++;
            return this;
        } */
		/* public */ isLevel() : boolean/* {
            return this.depth == 0;
        } */
		/* public */ exit() : State/* {
            this.depth--;
            return this;
        } */
		/* public */ isShallow() : boolean/* {
            return this.depth == 1;
        } *//* 
     */}
	/* private */ Joiner(delimiter : String) : record/* implements Collector<String, Optional<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Optional<String> createInitial() {
            return Optional.empty();
        }

        @Override
        public Optional<String> fold(Optional<String> current, String element) {
            return Optional.of(current.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    } */
	/* private */ Definition(maybeBefore : /* Optional<String> */, type : String, name : String, typeParams : /* List<String> */) : record/* {
        private String generate() {
            return this.generateWithParams("");
        }

        public String generateWithParams(String params) {
            var joined = this.typeParams.iterate()
                    .collect(new Joiner())
                    .map(inner -> "<" + inner + ">")
                    .orElse("");

            var before = this.maybeBefore
                    .filter(value -> !value.isEmpty())
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse("");

            return before + this.name + joined + params + " : " + this.type;
        }
    } */
	/* 

    private static  */class ListCollector<T> implements Collector<T, List<T>> {
		/* @Override
        public */ createInitial() : /* List<T> *//* {
            return Lists.empty();
        } */
		/* @Override
        public */ fold(current : /* List<T> */, element : T) : /* List<T> *//* {
            return current.add(element);
        } *//* 
     */}
	/* private record */ B>(left : A, right : B) : /* Tuple<A, *//* {
    } */
	/* public static */ main() : void/* {
        try {
            var parent = Paths.get(".", "src", "java", "magma");
            var source = parent.resolve("Main.java");
            var target = parent.resolve("main.ts");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    } */
	/* private static */ compile(input : String) : String/* {
        return compileStatements(input, Main::compileRootSegment);
    } */
	/* private static */ compileStatements(input : String, mapper : /* Function<String, String> */) : String/* {
        return compileAll(input, Main::foldStatementChar, mapper, Main::mergeStatements);
    } */
	/* private static */ compileAll(input : String, folder : /* BiFunction<State, Character, State> */, mapper : /* Function<String, String> */, merger : /* BiFunction<StringBuilder, String, StringBuilder> */) : String/* {
        return divideAll(input, folder)
                .iterate()
                .map(mapper)
                .fold(new StringBuilder(), merger)
                .toString();
    } */
	/* private static */ mergeStatements(stringBuilder : StringBuilder, str : String) : StringBuilder/* {
        return stringBuilder.append(str);
    } */
	/* private static */ divideStatements(input : String) : /* List<String> *//* {
        return divideAll(input, Main::foldStatementChar);
    } */
	/* private static */ divideAll(input : String, folder : /* BiFunction<State, Character, State> */) : /* List<String> *//* {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } */
	/* private static */ foldStatementChar(state : State, c : char) : State/* {
        var append = state.append(c);
        if (c == ';' && append.isLevel()) {
            return append.advance();
        }
        if (c == '} */
	/* ' */ append.isShallow() : /* && *//* ) {
            return append.advance().exit();
        } */
	/* 
        if  *//* {
            return append.enter();
        }
        if (c == '} */
	/* ') {
            */ append.exit() : return/* ;
        } */
	append : return;/* 
     */
}
/* private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(stripped));
    } *//* private static Optional<String> compileClass(String stripped, int depth) {
        return compileStructure(stripped, depth, "class ");
    } *//* private static Optional<String> compileStructure(String stripped, int depth, String infix) {
        return first(stripped, infix, (left, right) -> {
            return first(right, "{", (name, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                return suffix(strippedWithEnd, "}", content1 -> {
                    var strippedName = name.strip();

                    var beforeIndent = depth == 0 ? "" : "\n\t";
                    var afterIndent = depth == 0 ? "\n" : "";

                    var statements = compileStatements(content1, input -> compileClassSegment(input, depth + 1));
                    return Optional.of(beforeIndent + generatePlaceholder(left) + infix + strippedName + " {" + statements + afterIndent + "}" + afterIndent);
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
    } *//* private static <T> Optional<T> suffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }

        var slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    } *//* private static String compileClassSegment(String input, int depth) {
        return compileClass(input, depth)
                .or(() -> compileStructure(input, depth, "interface "))
                .or(() -> compileMethod(input, depth))
                .or(() -> compileDefinitionStatement(input, depth))
                .orElseGet(() -> generatePlaceholder(input));
    } *//* private static @NotNull Optional<? extends String> compileMethod(String input, int depth) {
        return first(input, "(", (definition, withParams) -> {
            return first(withParams, ")", (params, rawContent) -> {
                var content = rawContent.strip();
                var newContent = content.equals(";") ? ";" : generatePlaceholder(content);

                return Optional.of(createIndent(depth) + parseDefinition(definition)
                        .map(definition1 -> definition1.generateWithParams("(" + compileValues(params, Main::compileParameter) + ")"))
                        .orElseGet(() -> generatePlaceholder(definition)) + newContent);
            });
        });
    } *//* private static String compileValues(String params, Function<String, String> mapper) {
        return compileAll(params, Main::foldValueChar, mapper, Main::mergeValues);
    } *//* private static String compileParameter(String input) {
        if (input.isBlank()) {
            return "";
        }
        return parseDefinition(input).map(Definition::generate).orElseGet(() -> generatePlaceholder(input));
    } *//* private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    } *//* private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    } *//* private static Optional<String> compileDefinitionStatement(String input, int depth) {
        return suffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(withoutEnd).map(result -> createIndent(depth) + result.generate() + ";");
        });
    } *//* private static Optional<Definition> parseDefinition(String input) {
        return last(input.strip(), " ", (beforeName, name) -> {
            return split(() -> getStringStringTuple(beforeName), (beforeType, type) -> {
                return suffix(beforeType.strip(), ">", withoutTypeParamStart -> {
                    return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -> {
                        var typeParams = divideAll(typeParamsString, Main::foldValueChar)
                                .iterate()
                                .map(String::strip)
                                .collect(new ListCollector<>());

                        return assembleDefinition(Optional.of(beforeTypeParams), name, typeParams, type);
                    });
                }).or(() -> {
                    return assembleDefinition(Optional.of(beforeType), name, Lists.empty(), type);
                });
            }).or(() -> {
                return assembleDefinition(Optional.empty(), name, Lists.empty(), beforeName);
            });
        });
    } *//* private static Optional<Tuple<String, String>> getStringStringTuple(String beforeName) {
        var divisions = divideAll(beforeName, Main::foldTypeSeparator);
        return divisions.removeLast().map(removed -> {
            var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
            var right = removed.right;

            return new Tuple<>(left, right);
        });
    } *//* private static State foldTypeSeparator(State state, Character c) {
        if (c == ' ' && state.isLevel()) {
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
    } *//* private static Optional<Definition> assembleDefinition(Optional<String> beforeTypeParams, String name, List<String> typeParams, String type) {
        return Optional.of(new Definition(beforeTypeParams, compileType(type), name.strip(), typeParams));
    } *//* private static State foldValueChar(State state, char c) {
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
    } *//* private static String compileType(String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return stripped;
        }

        return generatePlaceholder(stripped);
    } *//* private static <T> Optional<T> last(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return infix(input, infix, Main::findLast, mapper);
    } *//* private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    } *//* private static <T> Optional<T> first(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return infix(input, infix, Main::findFirst, mapper);
    } *//* private static <T> Optional<T> infix(
            String input,
            String infix,
            BiFunction<String, String, Optional<Integer>> locator,
            BiFunction<String, String, Optional<T>> mapper
    ) {
        return split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple<>(left, right);
        }), mapper);
    } *//* private static <T> Optional<T> split(Supplier<Optional<Tuple<String, String>>> splitter, BiFunction<String, String, Optional<T>> mapper) {
        return splitter.get().flatMap(tuple -> mapper.apply(tuple.left, tuple.right));
    } *//* private static Optional<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    } *//* private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end");

        return "content-start " + replaced + " content-end";
    } *//* } */